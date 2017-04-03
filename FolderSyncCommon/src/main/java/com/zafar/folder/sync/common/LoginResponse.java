package com.zafar.folder.sync.common;

public class LoginResponse extends AppResponse{
	private boolean result=true;

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public LoginResponse() {
		super();
	}
	
}
