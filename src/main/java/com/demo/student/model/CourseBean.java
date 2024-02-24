package com.demo.student.model;

import jakarta.validation.constraints.NotEmpty;

public class CourseBean {
	
	private int id;
	private String code;
	@NotEmpty(message = "Course Name must not be empty!")
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
