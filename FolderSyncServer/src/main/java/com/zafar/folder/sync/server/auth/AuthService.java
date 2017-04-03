package com.zafar.folder.sync.server.auth;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zafar.folder.sync.common.CreateUserResponse;
import com.zafar.folder.sync.common.LoginRequest;
import com.zafar.folder.sync.server.Constants;

@Service
public class AuthService {
	private static Logger logger = LoggerFactory.getLogger(AuthService.class);

	private Properties users = new Properties();

	@PostConstruct
	public void init(){
		try {
			if(!Files.exists(Paths.get(Constants.DATA_FILE_PATH))){
				File file= new File(Constants.DATA_FILE_PATH);
				file.createNewFile();
			}
			InputStream input=null;
		
			input = new FileInputStream("classpath*:"+Constants.DATA_FILE_PATH);
			users.load(input);

		} catch (Exception e) {
			logger.error("Error",e);
		}	
	}
	public boolean authenticate(LoginRequest req){
		if(users.containsKey(req.getUserName())){
			String data=(String) users.get(req.getUserName());
			String strings[]=data.split(Constants.DELIMITER);
			if(strings[0].equals(req.getPassword())){
				addDeviceIfNotPresent(req);
				return true;
			}
		}
		return false;		
	}
	
	private void addDeviceIfNotPresent(LoginRequest req) {
		int i=0;
		boolean indicator=true;
		String d[]=((String) users.get(req.getUserName())).split(Constants.DELIMITER);
		for (String s: d){
			if(i!=0 && d[i].equals(req.getFingerPrint())){
				indicator=false;
				break;
			}
			i++;			
		}
		if(indicator)
			users.put(req.getUserName(), users.get(req.getUserName().concat(Constants.DELIMITER).concat(req.getFingerPrint())));		
	}
	public CreateUserResponse createUser(LoginRequest req){
		if(users.containsKey(req.getUserName())){
			return new CreateUserResponse(Constants.ALREADY_EXISTS,false, req);
		}else{
			users.put(req.getUserName(), req.getPassword());
			return new CreateUserResponse(Constants.CREATED, true, req);
		}
	}

}
