package com.zafar.folder.sync.common;

public class AppResponse {
	protected AppRequest request;
	protected Long timeStamp=System.currentTimeMillis();
	
	public AppResponse() {
		super();
	}
	public AppResponse(LoginRequest req) {
		request=req;
	}
	public AppRequest getRequest() {
		return request;
	}
	public void setRequest(AppRequest request) {
		this.request = request;
	}
	public Long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
}
