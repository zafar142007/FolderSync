package com.zafar.folder.sync.common;

public class AppRequest {
	protected String fingerPrint;
	protected Long timeStamp=System.currentTimeMillis();
	public AppRequest() {
		super();
	}

	public String getFingerPrint() {
		return fingerPrint;
	}

	public void setFingerPrint(String fingerPrint) {
		this.fingerPrint = fingerPrint;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	} 
	
}
