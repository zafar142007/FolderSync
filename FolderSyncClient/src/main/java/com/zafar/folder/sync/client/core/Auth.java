package com.zafar.folder.sync.client.core;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.zafar.folder.sync.client.util.Constants;
import com.zafar.folder.sync.client.util.ObjectMapperPool;
import com.zafar.folder.sync.common.CreateUserResponse;
import com.zafar.folder.sync.common.LoginRequest;
import com.zafar.folder.sync.common.LoginResponse;

@Component
public class Auth implements IAuth {

	@Value("${server.path}")
	private String serverPath;

	@Value("${default.username}")
	private String userName;

	@Value("${default.password}")
	private String password;

	@Value("${default.full.path.shared.folder}")
	private String sharedFolder;

	@Value("${max.connections}")
	private int maxConnections;

	@Value("${timeout.s}")
	private int timeOut;

	@Autowired
	private FingerPrint fingerPrint;
	
	private RestTemplate restTemplate;
	private IdleConnectionMonitorThread connectionMonitor;
	private ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
	    public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
		       return timeOut;
		    }
		};
	private static Logger logger = LoggerFactory.getLogger(Auth.class);

	@PostConstruct
	public void init() {
		restTemplate = new RestTemplate(
				new HttpComponentsClientHttpRequestFactory(getPool()));
		logger.info("Logging in ");
		signIn(userName, password);
	}

	public HttpClient getPool() {

		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		HttpClientBuilder builder = HttpClientBuilder.create();
		connectionManager.setMaxTotal(maxConnections);
		connectionManager.setDefaultMaxPerRoute(maxConnections);
		RequestConfig config = RequestConfig.custom()
				.setConnectTimeout(timeOut)
				.setConnectionRequestTimeout(timeOut).setSocketTimeout(timeOut)
				.build();
		connectionMonitor = new IdleConnectionMonitorThread(connectionManager);
		connectionMonitor.start();
		builder.setConnectionManager(connectionManager);
		builder.setKeepAliveStrategy(myStrategy);
		CloseableHttpClient defaultHttpClient = builder
				.setDefaultRequestConfig(config).build();
		logger.debug("prepared client");
		return defaultHttpClient;
	}

	@Override
	public boolean signIn(String u, String p) {
		HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
		ObjectMapper mapper=ObjectMapperPool.getMapper();

		String json="";
		try {
			json = mapper.writeValueAsString(new LoginRequest(u, p, fingerPrint.getFingerPrint()));
		
			HttpEntity<String> entity = new HttpEntity<String>(json, headers);
			ResponseEntity<String> response=restTemplate.postForEntity(serverPath.concat(Constants.SIGN_IN_SUFFIX), entity, String.class);
			LoginResponse resp=mapper.readValue(response.getBody(), LoginResponse.class);
			logger.debug("Got login response {}",resp.getResult());
			return resp.getResult();
		} catch (Exception e) {
			logger.error("Parse exception", e);
		}
		return false;
	}

	@Override
	public boolean createNewUser(String u, String p) {
		HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
		ObjectMapper mapper=ObjectMapperPool.getMapper();

		String json="";
		try {
			json = mapper.writeValueAsString(new LoginRequest(u, p, fingerPrint.getFingerPrint()));
		
			HttpEntity<String> entity = new HttpEntity<String>(json, headers);
			ResponseEntity<String> response=restTemplate.postForEntity(serverPath.concat(Constants.CREATE_USER_SUFFIX), entity, String.class);
			CreateUserResponse resp=mapper.readValue(response.getBody(), CreateUserResponse.class);
			logger.debug("Got create user response {}",resp.getStatus());
			return resp.isResult();
		} catch (Exception e) {
			logger.error("Parse exception", e);
		}
		return false;
	}

	@Override
	public boolean signOut(String u) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean forgotPassword(String u) {
		// TODO Auto-generated method stub
		return false;
	}

	public class IdleConnectionMonitorThread extends Thread {

		private final HttpClientConnectionManager connMgr;
		private volatile boolean shutdown;

		public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
			super();
			this.connMgr = connMgr;
		}

		@Override
		public void run() {
			try {
				while (!shutdown) {
					synchronized (this) {
						wait(7000);
						// Close expired connections
						connMgr.closeExpiredConnections();
						// Optionally, close connections
						// that have been idle longer than 30 sec
						connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
					}
				}
			} catch (InterruptedException ex) {
				logger.error("Error in idleconnection monitor", ex);
			}
		}

		public void shutdown() {
			shutdown = true;
			synchronized (this) {
				notifyAll();
			}
		}

	}
}
