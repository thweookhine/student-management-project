package com.demo.student.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import javax.swing.text.View;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.student.dto.User;
import com.demo.student.model.UserBean;
import com.demo.student.repository.UserRepository;
import com.demo.student.service.UserService;

import ch.qos.logback.core.status.Status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	UserService userService;
	
	@MockBean
	UserRepository userRepository;
	
	UserBean getUserBean() {
		UserBean uBean = new UserBean();
		uBean.setUserId(1);
		uBean.setUserCode("USR1");
		uBean.setUserName("Talia");
		uBean.setUserEmail("talia@g.com");
		uBean.setUserPassword("talia");
		uBean.setUserConfirmPassword("talia");
		uBean.setUserRole("Admin");
		return uBean;
	}

	@Test
	void testSetupUserPage() throws Exception {
		mockMvc.perform(get("/addUser"))
		.andExpect(view().name("sign-up"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("user"));
		
	}

	@Test
	void testAddUserPass() throws Exception {
		UserBean user = getUserBean();
		User u = new User();
//		u.setId(user.getUserId());
//		u.setCode(user.getUserCode());
		u.setName(user.getUserName());
		u.setEmail(user.getUserEmail());
		u.setRole(user.getUserRole());
		u.setPassword(user.getUserPassword());
		
		when(userService.addUser(u)).thenReturn(u);
		when(userRepository.save(u)).thenReturn(u);
		when(userRepository.findById(u.getId())).thenReturn(Optional.of(u));
		
		this.mockMvc.perform(post("/addUser").flashAttr("user", user))
		.andExpect(redirectedUrl("/displayUser"));
	}
	
	@Test
	void testAddUserFail() throws Exception {
		UserBean user = new UserBean();
		mockMvc.perform(post("/addUser").flashAttr("user", user))
		.andExpect(view().name("sign-up"));
	}

	@Test
	void testDisplayUser() throws Exception {
		mockMvc.perform(get("/displayUser"))
		.andExpect(view().name("display-user"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("list"));
	}

	@Test
	void testSetupUpdateUser() throws Exception {
		UserBean userBean = getUserBean();
		mockMvc.perform(get("/updateUser").param("id", "1"))
		.andExpect(view().name("update-user"))
		.andExpect(model().attributeExists("userBean"));
		;
		
	}

	@Test
	void testUpdateUser() throws Exception {
		UserBean uBean = new UserBean();
		uBean.setUserId(1);
		uBean.setUserCode("USR1");
		uBean.setUserName("update");
		uBean.setUserEmail("talia@g.com");
		uBean.setUserPassword("talia");
		uBean.setUserConfirmPassword("talia");
		uBean.setUserRole("Admin");
		User u = new User();
		u.setId(1);
		u.setCode("USR1");
		u.setName("update");
		u.setEmail("talia@g.com");
		u.setPassword("talia");
		u.setRole("Admin");
		
		when(userService.updateUser(u)).thenReturn(u);
		
		
		mockMvc.perform(post("/updateUser").flashAttr("userBean", uBean).sessionAttr("loginUser", u))
		.andExpect(redirectedUrl("displayUser"));
	}
	
	@Test
	void testUpdateUserConfirmPwWrong() throws Exception {
		UserBean uBean = new UserBean();
		uBean.setUserId(1);
		uBean.setUserCode("USR1");
		uBean.setUserName("Talia");
		uBean.setUserEmail("talia@g.com");
		uBean.setUserPassword("talia");
		uBean.setUserConfirmPassword("12345");
		uBean.setUserRole("Admin");
		
		mockMvc.perform(post("/updateUser").flashAttr("userBean", uBean))
		.andExpect(view().name("update-user"))
		.andExpect(model().attributeExists("confirmPw"));
	}

	@Test
	void testDeleteUser() throws Exception {
		mockMvc.perform(get("/deleteUser").param("id", "1"))
		.andExpect(redirectedUrl("/displayUser"));
	}

	@Test
	void testSearchUser() throws Exception {
		mockMvc.perform(post("/searchUser").param("code", "USR1").param("name","Talia" ))
		.andExpect(view().name("display-user"));
	}

}
