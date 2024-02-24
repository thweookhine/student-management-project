package com.demo.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.student.dto.Course;
import com.demo.student.dto.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

	List<Student> findByCode(String code);
	List<Student> findByName(String name);
	List<Student> findByCourses(Course course);
	
	@Query(value = "select * from student s where s.name like ?1",nativeQuery = true)
	List<Student> findWithStuName(String name);
}
