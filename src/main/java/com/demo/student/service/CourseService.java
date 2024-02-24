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
public class CourseService {

	@Autowired
	CourseRepository courseRepository;
	
	public Course addCourse(Course input) {
		Course course = courseRepository.save(input);
		course = courseRepository.findById(course.getId()).get();
		course.setCode("COURSE"+course.getId());
		return courseRepository.save(course);
	}
	
	public Course findByName(String name) {
		List<Course> list = courseRepository.findByName(name);
		if(list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	public List<Course> findAll(){
		return courseRepository.findAll();
	}
	
	public void deleteCourse(Integer id) {
		courseRepository.deleteById(id);
	}
	
	public Course findById(int id) {
		return courseRepository.findById(id).get();
	}
	
	public List<Course> searchCourse(String code, String name){
		List<Course> list = new ArrayList<>();
		if(code.isEmpty() && name.isEmpty()) {
			list.addAll(courseRepository.findAll());
			return list;
		}
		
		if(!code.isEmpty()) {
			List<Course> coursesByCode =  courseRepository.findByCode(code);
			if(coursesByCode.size() > 0) {
				list.addAll(coursesByCode);
			}
		}
		
		if(!name.isEmpty()){
			List<Course> coursesByName =  courseRepository.findWithCourseName("%"+name+"%");
			if(coursesByName.size() > 0) {
				list.addAll(coursesByName);
			}
		}
		list = list.stream().distinct().toList();
		return list;
	}
}
