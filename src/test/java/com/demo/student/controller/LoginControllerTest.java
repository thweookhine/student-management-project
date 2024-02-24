package com.demo.student.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.student.dto.User;
import com.demo.student.model.LoginUserBean;
import com.demo.student.repository.UserRepository;
import com.demo.student.service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {
	
	@Autowired 
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private UserRepository userRepository;
	

	@Test
	void testLoginPage() throws Exception {
		
		mockMvc.perform(get("/"))
		.andExpect(view().name("login"))
		.andExpect(model().attributeExists("loginUserBean"))
		;
	}

	@Test
	void testLogin() throws Exception {
		LoginUserBean loginU = new LoginUserBean();
		loginU.setUserCode("USR1");
		loginU.setUserPassword("thwe");
		
		User u = new User();
		u.setCode("USR1");
		u.setPassword("thwe");
		
		when(userService.findByCode("USR1")).thenReturn(u);
		mockMvc.perform(post("/login").flashAttr("loginUserBean", loginU).sessionAttr("loginUser", u))
		.andExpect(redirectedUrl("/displayUser"))
		;
	}
	
	@Test
	void testLoginFail() throws Exception {
		LoginUserBean loginU = new LoginUserBean();
		loginU.setUserCode("USR1");
		loginU.setUserPassword("thwe");
		mockMvc.perform(post("/login").flashAttr("loginUserBean", loginU))
		.andExpect(view().name("login"))
		;
	}

	@Test
	void testLogout() throws Exception {
		mockMvc.perform(get("/logout")).andExpect(redirectedUrl("/"));
	}

}
