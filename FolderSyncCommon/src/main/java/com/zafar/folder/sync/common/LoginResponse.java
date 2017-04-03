package com.zafar.folder.sync.common;

public class LoginResponse extends AppResponse{
	private boolean result=false;

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public LoginResponse() {
		super();
	}

	public LoginResponse(boolean re, LoginRequest req) {
		super(req);
		result=re;
	}
}
