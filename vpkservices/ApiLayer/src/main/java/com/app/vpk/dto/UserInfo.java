package com.app.vpk.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAnySetter;

public class UserInfo implements Serializable {
	
	private static final long serialVersionUID = -6588405544368102390L;

	@JsonAlias({ "firstName", "fName", "First_Name" })
	private String firstName;

	@JsonAlias({ "lastName", "lName", "Last_Name" })
	private String lastName;

	private String city;

	private Map<String, Object> additionInfo = new HashMap<String, Object>();

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Map<String, Object> getAdditionInfo() {
		return additionInfo;
	}

	@JsonAnySetter
	public void add(String key, Object value) {
		this.additionInfo.put(key, value);
	}

	@Override
	public String toString() {
		return "UserInfo [firstName=" + firstName + ", lastName=" + lastName + ", city=" + city + ", additionInfo="
				+ additionInfo + "]";
	}

	public UserInfo(String firstName, String lastName, String city, Map<String, Object> additionInfo) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.additionInfo = additionInfo;
	}

	public UserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

}
