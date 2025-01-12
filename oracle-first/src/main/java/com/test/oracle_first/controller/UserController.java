package com.test.oracle_first.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.oracle_first.model.Employee;
import com.test.oracle_first.repository.EmployeeRepository;

@RestController
public class UserController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@GetMapping("/test")
	public String hello() {
		return "hello";
	}
	
	@PostMapping("/save")
	public Employee saveEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
}
