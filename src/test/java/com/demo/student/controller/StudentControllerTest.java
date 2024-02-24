package com.demo.student.controller;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.student.dto.Course;
import com.demo.student.dto.Student;
import com.demo.student.model.StudentBean;
import com.demo.student.repository.CourseRepository;
import com.demo.student.repository.StudentRepository;
import com.demo.student.service.CourseService;
import com.demo.student.service.StudentService;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	StudentService studentService;
	
	@MockBean
	StudentRepository studentRepository;
	
	@MockBean
	CourseRepository courseRepository;
	
	@MockBean
	CourseService courseService;
	
	
	StudentBean getStu() {
		StudentBean s = new StudentBean();
		s.setId(1);
		s.setCode("STU1");
		s.setName("Talia");
		s.setDob("17/08/2001");
		s.setPhone("09898989898");
		s.setEducation("Diploma in IT");
		s.setGender("Female");
		s.setCourses(List.of("Java","Spring","Angular"));
		return s;
	}
	
	List<Course> getList(){
		List<Course> list = new ArrayList<>();
		Course c = new Course();
		c.setId(1);
		c.setCode("COURSE1");
		c.setName("Java");
		
		Course c2 = new Course();
		c2.setId(2);
		c2.setCode("COURSE2");
		c2.setName("Spring");
		
		Course c3 = new Course();
		c3.setId(3);
		c3.setCode("COURSE3");
		c3.setName("Angular");
		
		list.add(c);
		list.add(c2);
		list.add(c3);
		
		return list;
	}

	@Test
	void testSetupAddStudent() throws Exception {
		
		mockMvc.perform(get("/addStudent"))
		.andExpect(view().name("add-student"))
		.andExpect(model().attributeExists("student"))
		.andExpect(status().isOk())
		;
	}

	@Test
	void testAddStudent() throws Exception {
		StudentBean s = getStu();
		mockMvc.perform(post("/addStudent").flashAttr("student", s))
		.andExpect(redirectedUrl("/displayStudent"));
		
	}
	
	@Test
	void testAddStudentFail() throws Exception {
		StudentBean s = new StudentBean();
		mockMvc.perform(post("/addStudent").flashAttr("student", s))
		.andExpect(view().name("add-student"));
		
	}

	@Test
	void testDisplayStudent() throws Exception {
		mockMvc.perform(get("/displayStudent"))
		.andExpect(view().name("display-student"))
		.andExpect(model().attributeExists("stuList"))
		;
	}

	@Test
	
	void testSetupUpdateStudent() throws Exception {
		
		Student s = new Student();
		s.setId(1);
		s.setCode("STU1");
		s.setName("Talia");
		s.setDob("17/08/2001");
		s.setPhone("09898989898");
		s.setEducation("Diploma in IT");
		s.setGender("Female");
		s.setCourses(getList());
		
		when(studentService.findById(1)).thenReturn(s);
		
		mockMvc.perform(get("/updateStudent").param("id", "1"))
		.andExpect(view().name("update-student"))
		;
		
	}

	@Test
	void testUpdateStudent() throws Exception {
		StudentBean s = new StudentBean();
		s.setId(1);
		s.setCode("STU1");
		s.setName("Talia");
		s.setDob("2023-01-06");
		s.setPhone("09898989898");
		s.setEducation("Diploma in IT");
		s.setGender("Female");
		s.setCourses(List.of("Java","Spring","Angular"));
		Student stu = new Student();
		stu.setId(s.getId());
		stu.setCode(s.getCode());
		stu.setDob(s.getDob());
		stu.setEducation(s.getEducation());
		stu.setGender(s.getGender());
		stu.setPhone(s.getPhone());
		stu.setCourses(getList());
		stu.setName(s.getName());
		when(courseService.findByName("Java")).thenReturn(stu.getCourses().get(0));
		when(courseService.findByName("Spring")).thenReturn(stu.getCourses().get(1));
		when(courseService.findByName("Angular")).thenReturn(stu.getCourses().get(2));
		
		when(studentService.updateStudent(stu)).thenReturn(stu);
		
		mockMvc.perform(post("/updateStudent").flashAttr("studentBean", s))
		.andExpect(redirectedUrl("/displayStudent"));
	}

	@Test
	void testDeleteStudent() throws Exception {
		mockMvc.perform(get("/deleteStudent").param("id", "1"))
		.andExpect(redirectedUrl("/displayStudent"))
		;
	}

	@Test
	void testSearchStudent() throws Exception {
		mockMvc.perform(post("/searchStudent").param("code", "STU1").param("name", "Talia").param("courseName", "Java"))
		.andExpect(view().name("display-student"))
		;
	}
	
	@Test
	void testSearchStudentWithNoData() throws Exception {
		mockMvc.perform(post("/searchStudent").param("code", "").param("name", "").param("courseName", ""))
		.andExpect(view().name("display-student"))
		;
	}
	
	
	

}
