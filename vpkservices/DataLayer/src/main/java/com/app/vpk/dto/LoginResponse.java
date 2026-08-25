package com.app.vpk.dto;

public class LoginResponse {
	private String accessToken;

	private String refreshToken;

	private String tokenType;

	private long expiresIn;
	
	private String message;
	
	private int statusCode;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public LoginResponse(String accessToken, String refreshToken, String tokenType, long expiresIn) {
		super();
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.tokenType = tokenType;
		this.expiresIn = expiresIn;
	}
	
	

	public LoginResponse(String accessToken, String refreshToken, String tokenType, long expiresIn, String message,
			int statusCode) {
		super();
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.tokenType = tokenType;
		this.expiresIn = expiresIn;
		this.message = message;
		this.statusCode = statusCode;
	}

	@Override
	public String toString() {
		return "LoginResponse [accessToken=" + accessToken + ", refreshToken=" + refreshToken + ", tokenType="
				+ tokenType + ", expiresIn=" + expiresIn + "]";
	}

	public LoginResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoginResponse(int statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}
}
