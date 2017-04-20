package com.zafar.folder.sync.client.util;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

public class ObjectMapperPool {

		private static ObjectMapper instance = new ObjectMapper();
		public static ObjectMapper getMapper() {
			instance.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return instance;
		}
	
}
	