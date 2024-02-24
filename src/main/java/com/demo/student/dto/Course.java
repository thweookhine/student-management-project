package com.demo.student.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(name="code_unique",columnNames = "code"),
		@UniqueConstraint(name="name_unique",columnNames = "name")
})
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "course_generator")
	@SequenceGenerator(name="course_generator",sequenceName = "course_sequence_name",allocationSize = 1)
	private Integer id;
	private String code;
	private String name;
	
	@ManyToMany(mappedBy = "courses")
	private List<Student> students = new ArrayList<>();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public List<Student> getStudents() {
		return students;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	@Override
	public int hashCode() {
		return Objects.hash(code, id, name, students);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		return Objects.equals(code, other.code) && Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(students, other.students);
	}
	@Override
	public String toString() {
		return "Course [id=" + id + ", code=" + code + ", name=" + name + ", students=" + students + "]";
	}
	
	
}
