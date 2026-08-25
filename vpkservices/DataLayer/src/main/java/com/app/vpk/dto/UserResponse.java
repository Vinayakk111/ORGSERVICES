package com.app.vpk.dto;

public class UserResponse {
	private Long id;

	private String username;

	private String role;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserResponse [id=" + id + ", username=" + username + ", role=" + role + "]";
	}

	public UserResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserResponse(Long id, String username, String role) {
		super();
		this.id = id;
		this.username = username;
		this.role = role;
	}

}
