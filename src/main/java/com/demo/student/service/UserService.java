package com.demo.student.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.student.dto.User;
import com.demo.student.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	public User addUser(User user) {
		User u = userRepository.save(user);
		u = userRepository.findById(u.getId()).get();
		u.setCode("USR"+u.getId());
		return userRepository.save(user);
	}
	
	public User updateUser(User input) {
		
		//find user by id
		User user = userRepository.findById(input.getId()).get();
		
		//update user
		user = input;
		
		//save updated user
		user = userRepository.save(user);
		
		return user;
	}
	
	public User findById(int id) {
		return userRepository.findById(id).get();
	}
	
	public void delete(User user) {
		
	}
	
	public List<User> selectAll() {
		return userRepository.findAll();
	}

	public void deleteUser(int id) {
		userRepository.deleteById(id);
	}
	
	public User findByCode(String code) {
		List<User> list = userRepository.findByCode(code);
		if(list.size() > 0) {
			User user = list.get(0);
			return user;
		}
		return null;
	}
	
	public List<User> searchUser(String code, String name){
		List<User> list = new ArrayList<>();
		
		if(code.isEmpty() && name.isEmpty()) {
			list = userRepository.findAll();
			return list;
		}
		
		if(!code.isEmpty()) {
			List<User> usersByCode = userRepository.findByCode(code);
			if(usersByCode.size() > 0) {
				list.addAll(usersByCode);
			}
		}
		
		if(!name.isEmpty()) {
			List<User> usersByName = userRepository.findWithUsrName("%"+name+"%");
			if(usersByName.size() > 0) {
				list.addAll(usersByName);
			}
		}
		list = list
                .stream()
                .distinct()
                .collect(Collectors.toList());
		
		return list;
		
	}
	
}
