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
import com.demo.student.model.CourseBean;
import com.demo.student.repository.CourseRepository;
import com.demo.student.service.CourseService;

@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerTest {
	
	@Autowired 
	private MockMvc mockMvc;
	
	@MockBean
	CourseService coSr;
	
	@MockBean
	CourseRepository coRp;
	
	Course setup() {
		Course c = new Course();
		c.setId(1);
		c.setCode("COURSE1");
		c.setName("Java");
		return c;
	}
	
	CourseBean getCBean() {
		CourseBean cBean = new CourseBean();
		cBean.setId(1);
		cBean.setCode("COURSE1");
		cBean.setName("Java");
		return cBean;
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
	void testSetupAddCourse() throws Exception {
		
		mockMvc.perform(get("/addCourse"))
		.andExpect(view().name("add-course"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("course"));
	}

	@Test
	void testAddCourse() throws Exception {
		CourseBean courseBean = getCBean();
		this.mockMvc.perform(post("/addCourse").flashAttr("course", courseBean))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/displayCourse"));	
		
	}
	
	@Test
	void testAddCourseFail() throws Exception {
		CourseBean courseBean = new CourseBean();
		this.mockMvc.perform(post("/addCourse").flashAttr("course", courseBean))
		.andExpect(view().name("add-course"));	
		
	}

	@Test
	void testDisplayCourse() throws Exception {
		
		System.out.println(this.mockMvc.perform(get("/displayCourse")));
		this.mockMvc.perform(get("/displayCourse"))
		.andExpect(status().isOk())
		.andExpect(view().name("display-course"))
		.andExpect(model().attributeExists("courses"));
	}

	@Test
	void testDeleteCourse() throws Exception {
		this.mockMvc.perform(get("/deleteCourse").param("id", "1"))
			.andExpect(redirectedUrl("/displayCourse"));
	}

	@Test
	void testSearchCourse() throws Exception {
		this.mockMvc.perform(post("/searchCourse").param("code", "COURSE1").param("name", "Java"))
			.andExpect(view().name("display-course"));
	}
	
	

}
