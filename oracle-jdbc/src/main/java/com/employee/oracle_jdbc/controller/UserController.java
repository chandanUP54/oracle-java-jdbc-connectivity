package com.employee.oracle_jdbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.oracle_jdbc.model.User;
import com.employee.oracle_jdbc.service.UserServices;

@RestController
public class UserController {

	@Autowired
	private UserServices userServices;

	@PostMapping("/saveUser")
	public User saveUser(@RequestBody User user) {

		return userServices.saveUser(user);
	}
}
