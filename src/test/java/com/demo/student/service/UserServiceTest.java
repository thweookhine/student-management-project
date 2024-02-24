package com.demo.student.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.student.dto.User;
import com.demo.student.repository.UserRepository;

@SpringBootTest
class UserServiceTest {
	
	@Mock
	UserRepository userRepository;
	
	@InjectMocks
	UserService userService;
	
	
	User createAdminUser() {
		User user = new User();
		user.setId(1);
		user.setName("Talia");
		user.setCode("USR1");
		user.setEmail("talia@g.com");
		user.setPassword("talia");
		user.setRole("Admin");
		return user;
	}
	
	@Test
	void testAddUser() {
		User user = createAdminUser();
		when(userRepository.save(user)).thenReturn(user);
		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		
		User result = userService.addUser(user);
		
		assertNotNull(result);
		assertEquals(1, result.getId());
		assertEquals("Talia", result.getName());
		assertEquals("USR1", result.getCode());
		assertEquals("talia@g.com", result.getEmail());
		assertEquals("talia", result.getPassword());
		assertEquals("Admin", result.getRole());
	}

	@Test
	void testUpdateUser() {
		
		User user = createAdminUser();
		
		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		
		System.out.println(user.getName());

		user.setId(1);
		user.setName("Lily");
		user.setCode("USR1");
		user.setEmail("lily@g.com");
		user.setPassword("lily");
		user.setRole("Admin");
		
		when(userRepository.save(user)).thenReturn(user);
		
		System.out.println(user.getName());
		
		User result = userService.addUser(user);
		assertEquals(1, result.getId());
		assertEquals("Lily", result.getName());
		assertEquals("USR1", result.getCode());
		assertEquals("lily@g.com", result.getEmail());
		assertEquals("lily", result.getPassword());
		assertEquals("Admin", result.getRole());
	}

	@Test
	void testFindById() {
		User user = createAdminUser();
		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		User result = userService.findById(1);
		assertNotNull(result);
		assertEquals(1, result.getId());
		assertEquals("Talia", result.getName());
		assertEquals("USR1", result.getCode());
		assertEquals("talia@g.com", result.getEmail());
		assertEquals("talia", result.getPassword());
		assertEquals("Admin", result.getRole());
	}

	@Test
	void testDelete() {
		User user = createAdminUser();
		userService.delete(user);
	}

	@Test
	void testSelectAll() {
		User user = new User();
		user.setId(1);
		user.setName("Talia");
		user.setCode("USR1");
		user.setEmail("talia@g.com");
		user.setPassword("talia");
		user.setRole("Admin");
		
		User user2 = new User();
		user2.setId(2);
		user2.setName("Lily");
		user2.setCode("USR2");
		user2.setEmail("lily@g.com");
		user2.setPassword("lily");
		user2.setRole("User");
		
		User user3 = new User();
		user.setId(3);
		user.setName("Emily");
		user.setCode("USR3");
		user.setEmail("emily@g.com");
		user.setPassword("emily");
		user.setRole("Admin");
		
		List<User> list = new ArrayList<>();
		list.add(user);
		list.add(user2);
		list.add(user3);
		
		when(userRepository.findAll()).thenReturn(list);
		List<User> result = userService.selectAll();
		assertEquals(3, result.size());
	}

	@Test
	void testDeleteUser() {
		User user = createAdminUser();
		userService.delete(user);
	}

	@Test
	void testFindByCode() {
		User user = createAdminUser();
		List<User> list = new ArrayList<>();
		list.add(user);
		when(userRepository.findByCode("USR1")).thenReturn(list);
		assertEquals(1, list.size());
		assertEquals("Talia", list.get(0).getName());
		
	}

	@Test
	void testSearchUser() {
		User user = new User();
		user.setId(1);
		user.setName("Talia");
		user.setCode("USR1");
		user.setEmail("talia@g.com");
		user.setPassword("talia");
		user.setRole("Admin");
		
		User user2 = new User();
		user2.setId(2);
		user2.setName("Lily");
		user2.setCode("USR2");
		user2.setEmail("lily@g.com");
		user2.setPassword("lily");
		user2.setRole("User");
		
		User user3 = new User();
		user.setId(3);
		user.setName("Emily");
		user.setCode("USR3");
		user.setEmail("emily@g.com");
		user.setPassword("emily");
		user.setRole("Admin");
		
		List<User> list = new ArrayList<>();
		list.add(user);
		list.add(user2);
		list.add(user3);
		
		List<User> user2List = new ArrayList<>();
		user2List.add(user2);
	
		//when(userRepository.findAll()).thenReturn(list);
		when(userRepository.findByCode(user2.getCode())).thenReturn(user2List);
		when(userRepository.findByName("Lily")).thenReturn(user2List);
		
		List<User> result = userService.searchUser("USR2", "Lily");
		
		assertEquals(1, result.size());
		
	}

}
