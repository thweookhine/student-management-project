package com.demo.student.model;

import java.util.List;

import com.demo.student.dto.Course;
import com.demo.student.dto.Student;

import jakarta.validation.constraints.NotEmpty;

public class StudentBean {

	private int id;
	private String code;
	@NotEmpty(message = "Student Name must not be empty!")
	private String name;
	@NotEmpty(message = "Student Dob must not be empty!")
	private String dob;
	@NotEmpty(message = "Student Gender must not be empty!")
	private String gender;
	@NotEmpty(message = "Student Phone must not be empty!")
	private String phone;
	@NotEmpty(message = "Student Education must not be empty!")
	private String education;
	@NotEmpty(message = "Student Course must not be empty!")
	private List<String> courses;
	
	
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
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public List<String> getCourses() {
		return courses;
	}
	public void setCourses(List<String> courses) {
		this.courses = courses;
	}
	
	
	

}
