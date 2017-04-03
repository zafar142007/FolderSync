package com.zafar.folder.sync.client.core;

public interface IAuth {

	public boolean signIn(String u, String p);
	
	public boolean createNewUser(String s, String p);
	
	public boolean signOut(String u);
	
	public boolean forgotPassword(String u);

}
