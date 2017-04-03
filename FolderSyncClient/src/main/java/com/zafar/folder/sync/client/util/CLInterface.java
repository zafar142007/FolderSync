package com.zafar.folder.sync.client.util;

import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zafar.folder.sync.client.core.Auth;

@Service
public class CLInterface {
	private String choices[]={"Signin","Register","Change shared folder","Signout","Forgot password"};

	@Autowired
	private Auth auth;
	
	public void printChoices(){
		for (int i=0; i< choices.length;i++)
			System.out.println("["+i+"] "+choices[i]);
	}
	@PostConstruct
	public void prompt(){
		int choice=-1;
		String username, password;
		Scanner scanner=new Scanner(System.in);
		while(choice!=2){
			printChoices();
			System.out.println("Enter a number from the above choices");
			choice=scanner.nextInt();
			switch(choice){
			case 0:
				System.out.println("Username:");
				username=scanner.next();
				System.out.println("Password:");
				password=scanner.next();
			
				System.out.println((auth.signIn(username, password))?"Logged in":"Could not log in");
				break;
			case 1:
				System.out.println("Username:");
				username=scanner.next();
				System.out.println("Password:");
				password=scanner.next();
			
				System.out.println((auth.createNewUser(username, password))?"Created User":"Could not create");
				break;
			case 2:
				//TODO
				break;
			case 3:
				System.out.println("Exiting");
				System.exit(0);
				break;
			case 4:
				//TODO
				break;
			}
		}
	}
}
