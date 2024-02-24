package com.demo.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.student.dto.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	List<User> findByCode(String code);
	List<User> findByName(String name);
	
	@Query(value = "select * from user u where u.name like ?1",nativeQuery=true)
	List<User> findWithUsrName(String name);

}
