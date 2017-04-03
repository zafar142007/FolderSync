package com.zafar.folder.sync.common;

public class LoginRequest extends AppRequest{	
	private String userName;
	private String password;
	public LoginRequest(){super();}
	public LoginRequest(String u, String p, String f){
		super();
		userName=u;
		password=p;
		fingerPrint=f;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}	
