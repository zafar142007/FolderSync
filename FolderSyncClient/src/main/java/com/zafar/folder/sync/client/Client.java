package com.zafar.folder.sync.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zafar.folder.sync.client.util.CLInterface;

/**
 * 
 *
 */
public class Client {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"context.xml");
	}	
}
