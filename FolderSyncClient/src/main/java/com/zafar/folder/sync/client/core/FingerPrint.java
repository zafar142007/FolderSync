package com.zafar.folder.sync.client.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import util.Constants;


/**
 * Create a unique machine fingerprint and store it locally on the machine
 * @author zafar
 *
 */
@Component
public class FingerPrint {	
	
	private String fingerPrint="";
	private static Logger logger=LoggerFactory.getLogger(FingerPrint.class);

	@PostConstruct
	public void init(){
		if(Files.exists(Paths.get(Constants.FINGERPRINT_PATH))){
			try (Stream<String> stream = Files.lines(Paths.get(Constants.FINGERPRINT_PATH))) {
				stream.forEach((s)->{
					logger.debug("Found fingerprint {}",s);
					fingerPrint=s;
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(fingerPrint.equals("") || fingerPrint==null){
			createFingerPrint();
		}
	}

	private void createFingerPrint() {
		fingerPrint= ThreadLocalRandom.current().nextInt(0, 10000000)+"";	
		try {
			Files.write(Paths.get(Constants.FINGERPRINT_PATH), fingerPrint.getBytes());
			logger.debug("Formed fingerprint {}", fingerPrint);
		} catch (IOException e) {
			logger.error("Could not write to file",e);
		}
	}

	public String getFingerPrint() {
		return fingerPrint;
	}

}
