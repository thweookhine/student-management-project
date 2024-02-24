package com.demo.student.model;

import jakarta.validation.constraints.NotEmpty;

public class UserBean {

	private int userId;
	private String userCode;
	@NotEmpty(message = "User Name must not be empty")
	private String userName;
	@NotEmpty(message = "User Email must not be empty")
	private String userEmail;
	@NotEmpty(message = "User Password must not be empty")
	private String userPassword;
	@NotEmpty(message = "Confirm Password must not be empty")
	private String userConfirmPassword;
	private String userRole;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getUserConfirmPassword() {
		return userConfirmPassword;
	}

	public void setUserConfirmPassword(String userConfirmPassword) {
		this.userConfirmPassword = userConfirmPassword;
	}

}
