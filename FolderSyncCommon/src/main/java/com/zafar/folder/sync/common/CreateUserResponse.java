package com.zafar.folder.sync.common;

public class CreateUserResponse extends AppResponse{
	private String status;
	private boolean result=false;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public CreateUserResponse() {
		super();
	}
	public CreateUserResponse(String st, boolean s, LoginRequest req) {
		super(req);
		status=st;
		result=s;
	}
		
}
