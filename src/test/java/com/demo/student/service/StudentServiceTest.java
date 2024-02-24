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
import com.demo.student.dto.Student;
import com.demo.student.repository.CourseRepository;
import com.demo.student.repository.StudentRepository;

@SpringBootTest
class StudentServiceTest {

	@Mock
	StudentRepository studentRepository;

	@InjectMocks
	StudentService studentService;

	@Mock
	CourseRepository courseRepository;

	@InjectMocks
	CourseService courseService;

	Student createStudent() {
		Student stu = new Student();

		Course course = new Course();
		course.setName("Java");

		Course course2 = new Course();
		course2.setName("Spring");

		stu.setId(1);
		stu.setCode("STU1");
		stu.setName("Lily");
		stu.setDob("18/01/2022");
		stu.setPhone("097878787878");
		stu.setEducation("blah blah");
		stu.setCourses(List.of(course, course2));

		return stu;
	}

	List<Student> getList() {
		Student stu = new Student();

		Course course = new Course();
		course.setName("Java");

		Course course2 = new Course();
		course2.setName("Spring");

		stu.setId(1);
		stu.setCode("STU1");
		stu.setName("Lily");
		stu.setDob("18/01/2022");
		stu.setPhone("097878787878");
		stu.setEducation("blah blah");
		stu.setCourses(List.of(course, course2));
		
		Student stu2 = new Student();

		Course course3 = new Course();
		course.setName("PHP");

		Course course4 = new Course();
		course2.setName("Laravel");

		stu2.setId(2);
		stu2.setCode("STU2");
		stu2.setName("Talia");
		stu2.setDob("18/01/2022");
		stu2.setPhone("097878787878");
		stu2.setEducation("blah blah");
		stu2.setCourses(List.of(course3, course4));
		
		Student stu3 = new Student();

		Course course5 = new Course();
		course.setName("Html");

		Course course6 = new Course();
		course2.setName("css");

		stu3.setId(3);
		stu3.setCode("STU3");
		stu3.setName("John");
		stu3.setDob("18/01/2022");
		stu3.setPhone("097878787878");
		stu3.setEducation("blah blah");
		stu3.setCourses(List.of(course5, course6));
		
		List<Student> list = new ArrayList<>();
		list.add(stu);
		list.add(stu2);
		list.add(stu3);
		return list;
	}

	@Test
	void testAddStudent() {
		Student stu = createStudent();
		when(studentRepository.findById(1)).thenReturn(Optional.of(stu));
		when(studentRepository.save(stu)).thenReturn(stu);
		Student result = studentService.addStudent(stu);
		assertEquals("Lily", result.getName());
		assertEquals("Java", result.getCourses().get(0).getName());
		assertEquals("Spring", result.getCourses().get(1).getName());
		verify(studentRepository,times(2)).save(stu);
	}

	@Test
	void testUpdateStudent() {
		Student stu = createStudent();

		when(studentRepository.findById(1)).thenReturn(Optional.of(stu));

		stu.setName("Emily");
		stu.setEducation("Diploma in IT");
		Course course = new Course();
		course.setName("React");

		Course course2 = new Course();
		course2.setName("Vue");

		stu.setCourses(List.of(course, course2));

		when(studentRepository.save(stu)).thenReturn(stu);

		Student result = studentService.updateStudent(stu);

		assertNotNull(result);
		assertEquals("Emily", result.getName());
		assertEquals("React", result.getCourses().get(0).getName());
		assertEquals("Vue", result.getCourses().get(1).getName());
		verify(studentRepository,times(1)).save(stu);
		verify(studentRepository,times(1)).findById(1);
	}

	@Test
	void testSelectAll() {
		List<Student> list = getList();
		when(studentRepository.findAll()).thenReturn(list);
		List<Student> result = studentService.findAll();
		assertEquals(3, result.size());
		assertEquals("Lily", result.get(0).getName());
		assertEquals("Talia", result.get(1).getName());
		assertEquals("John", result.get(2).getName());
		
	}

	@Test
	void testFindById() {
		Student stu = createStudent();
		when(studentRepository.findById(1)).thenReturn(Optional.of(stu));
		Student result = studentService.findById(1);
		assertEquals(1, result.getId());
		assertEquals("STU1", result.getCode());
		assertEquals("Lily", result.getName());
		assertEquals("18/01/2022", result.getDob());
		assertEquals("097878787878", result.getPhone());
		assertEquals("blah blah", result.getEducation());
		assertEquals("Java", result.getCourses().get(0).getName());
		assertEquals("Spring", result.getCourses().get(1).getName());
	}

	@Test
	void testDeleteStudent() {
		Student stu = createStudent();
		studentService.deleteStudent(stu.getId());
		verify(studentRepository, times(1)).deleteById(1);
	}


	@Test
	void testSearchStudent() {
		List<Student> list = getList();
		
		List<Student> list2 = new ArrayList<>();
		list2.add(list.get(1));
		list2.add(list.get(0));
		
		Course course = new Course();
		course.setName("Laravel");

		
		when(studentRepository.findByCode("STU2")).thenReturn(list2);
		when(studentRepository.findByName("Talia")).thenReturn(list2);
		when(studentRepository.findByCourses(course)).thenReturn(list2);
		
		List<Student> result = studentService.searchStudent("STU2", "John", course);
		
		assertEquals(2,result.size() );
		assertEquals("Talia", result.get(0).getName());
		assertEquals("Lily", result.get(1).getName());
		assertEquals("STU2", result.get(0).getCode());
		assertEquals("STU1", result.get(1).getCode());
		for(Student s : result) {
			System.out.println(s.getName());
		}
	}

}
