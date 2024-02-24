package com.demo.student.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.student.dto.Course;
import com.demo.student.dto.Student;
import com.demo.student.repository.CourseRepository;
import com.demo.student.repository.StudentRepository;

@Service
public class StudentService {
	
	@Autowired
	StudentRepository studentRepository;
	
	public Student addStudent(Student stu) {
		Student student = studentRepository.save(stu);
		student = studentRepository.findById(student.getId()).get();
		student.setCode("STU"+student.getId());
		return studentRepository.save(student);
	}
	
	public Student updateStudent(Student stu) {
		Student student = studentRepository.findById(stu.getId()).get();
		student = stu;
		return studentRepository.save(student);
	}
	
	public List<Student> selectAll(){
		return studentRepository.findAll();
	}
	
	public Student findById(int id) {
		return studentRepository.findById(id).get();
	}
	
	public void deleteStudent(int id) {
		studentRepository.deleteById(id);
	}
	
	public List<Student> findAll(){
		return studentRepository.findAll();
	}

	public List<Student> searchStudent(String code, String name, Course course) {
		List<Student> list = new ArrayList<>();
//		if(code.isEmpty() && name.isEmpty() && course == null ) {
//			list = studentRepository.findAll();
//			return list;
//		}
		
		if(!code.isEmpty()) {
			List<Student> stuByCode = studentRepository.findByCode(code);
			if(stuByCode.size() > 0) {
				list.addAll(stuByCode);
			}
		}
		
		if(!name.isEmpty()) {
			List<Student> stuByName = studentRepository.findWithStuName("%"+name+"%");
			if(stuByName.size() > 0) {
				list.addAll(stuByName);
			}
		}
		
		if(course != null) {
			List<Course> courses = new ArrayList<>();
			courses.add(course);
			List<Student> stuByCourse = studentRepository.findByCourses(course);
			if(stuByCourse.size() > 0) {
				list.addAll(stuByCourse);
			}
		}
		list = list.stream().distinct().toList();
		
		return list;
	}
	
	

}
