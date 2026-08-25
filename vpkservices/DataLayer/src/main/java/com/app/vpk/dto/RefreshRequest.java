package com.app.vpk.dto;

import javax.validation.constraints.NotBlank;

public class RefreshRequest {
	
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
		return "RefreshRequest [refreshToken=" + refreshToken + "]";
	}

	public RefreshRequest(@NotBlank String refreshToken) {
		super();
		this.refreshToken = refreshToken;
	}

	public RefreshRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

}
