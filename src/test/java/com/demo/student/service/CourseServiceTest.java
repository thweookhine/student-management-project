package com.demo.student.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.student.dto.Course;
import com.demo.student.repository.CourseRepository;

@SpringBootTest
class CourseServiceTest {
	
	@Mock
	CourseRepository courseRepo;
	
	@InjectMocks
	CourseService courseService;

	@Test
	void testAddCourse() {
		Course c = new Course();
		c.setId(16);
		c.setCode("COURSE16");
		c.setName("REACT");
		when(courseRepo.save(c)).thenReturn(c);
	 	when(courseRepo.findById(16)).thenReturn(Optional.of(c));
	 	
	 	Course course = courseService.addCourse(c);
	 	
		assertEquals("REACT", course.getName());
	}

	@Test
	void testFindByName() {
		String courseName = "Spring";
		Course c = new Course();
		c.setName(courseName);
		List<Course> list = new ArrayList<>();
		list.add(c);
		when(courseRepo.findByName(courseName)).thenReturn(list);
		Course course = courseService.findByName(courseName);
		assertEquals(courseName, course.getName());
	}

	@Test
	void testFindAll() {
		
		List<Course> list = new ArrayList<>();
		
		Course c1 = new Course();
		c1.setId(1);
		c1.setCode("C1");
		c1.setName("course1");
		
		Course c2 = new Course();
		c2.setId(2);
		c2.setCode("C2");
		c2.setName("course2");
		
		Course c3 = new Course();
		c3.setId(3);
		c3.setCode("C3");
		c3.setName("course3");
		
		list.add(c1);
		list.add(c2);
		list.add(c3);
		
		when(courseRepo.findAll()).thenReturn(list);
//		for(Course c : list) {
//			System.out.println("id "+ c.getId()+", code "+c.getCode()+", name "+c.getName());
//		}
		
		assertEquals(3, list.size());
	}

	@Test
	void testDeleteCourse() {
		int courseId = 16;
		Course c = new Course();
		c.setId(courseId);
		c.setCode("COURSE16");
		c.setName("Spring");
		courseService.deleteCourse(courseId);
	}

	@Test
	void testFindById() {
		int courseId = 16;
		Course c = new Course();
		c.setId(16);
		c.setCode("COURSE16");
		c.setName("Spring");
		when(courseRepo.findById(courseId)).thenReturn(Optional.of(c));
		Course result = courseService.findById(16);
		assertEquals("Spring", result.getName());
		assertEquals(c, result);
	}

	@Test
	void testSearchCourse() {
		
		List<Course> list = new ArrayList<>();
		
		Course c1 = new Course();
		c1.setId(1);
		c1.setCode("C1");
		c1.setName("course1");
		
		Course c2 = new Course();
		c2.setId(2);
		c2.setCode("C2");
		c2.setName("course2");
		
		Course c3 = new Course();
		c3.setId(3);
		c3.setCode("C3");
		c3.setName("course3");
		
		list.add(c1);
		list.add(c2);
		list.add(c3);
		
		List<Course> cByCode = new ArrayList<>();
		cByCode.add(c2);
		
		List<Course> cByName = new ArrayList<>();
		cByName.add(c3);
		
		when(courseRepo.findAll()).thenReturn(list);
		when(courseRepo.findByCode("C2")).thenReturn(cByCode);
		when(courseRepo.findByName("course3")).thenReturn(cByName);
		
		List<Course> result = courseService.searchCourse("C2", "course3");
		
		for(Course c : result) {
			System.out.println("id "+ c.getId()+", code "+c.getCode()+", name "+c.getName());
		}
		
	}

}
