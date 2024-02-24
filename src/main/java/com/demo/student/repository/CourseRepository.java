package com.demo.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.student.dto.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {
	List<Course> findByName(String name);
	List<Course> findByCode(String code);
	
	@Query(value = "select * from course c where c.name like ?1",nativeQuery = true)
	List<Course> findWithCourseName(String name);
}
