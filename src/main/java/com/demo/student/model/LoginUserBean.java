package com.demo.student.model;

import jakarta.validation.constraints.NotEmpty;

public class LoginUserBean {

	@NotEmpty(message = "UserCode must not be empty!")
	private String userCode;

	@NotEmpty(message = "UserPassword must not be empty!")
	private String userPassword;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	

}
