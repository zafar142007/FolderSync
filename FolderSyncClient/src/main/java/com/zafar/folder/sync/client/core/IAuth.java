package com.zafar.folder.sync.client.core;

public interface IAuth {

	public boolean signIn();
	
	public boolean createNewUser();
	
	public boolean signOut();
	
	public boolean forgotPassword();

}
