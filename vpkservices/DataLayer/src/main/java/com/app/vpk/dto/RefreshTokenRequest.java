package com.app.vpk.dto;

import javax.validation.constraints.NotBlank;

public class RefreshTokenRequest {
	@NotBlank
	private String refreshToken;

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public String toString() {
		return "RefreshTokenRequest [refreshToken=" + refreshToken + "]";
	}

	public RefreshTokenRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RefreshTokenRequest(String refreshToken) {
		super();
		this.refreshToken = refreshToken;
	}

}
