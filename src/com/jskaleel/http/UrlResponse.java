package com.jskaleel.http;

public class UrlResponse {

	private int resposeCode;
	private String content, errorMsg;
	private int errorCode;

	public UrlResponse(){}

	public UrlResponse(int resposeCode, String content, String errorMsg) {
		super();
		this.resposeCode = resposeCode;
		this.content = content;
		this.errorMsg = errorMsg;
	}

	public int getResposeCode() {
		return resposeCode;
	}

	public void setResposeCode(int resposeCode) {
		this.resposeCode = resposeCode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public void setErrorCode(int errorCode){
		this.errorCode = errorCode;
	}
	
	public int getErrorCode(){
		return errorCode;
	}

	@Override
	public String toString() {
		return "BResponse [resposeCode=" + resposeCode + ", content=" + content
				+ ", errorMsg=" + errorMsg + "]";
	}

}
