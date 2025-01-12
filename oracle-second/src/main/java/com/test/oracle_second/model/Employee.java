package com.test.oracle_second.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

	private int empId;
	private String empName;
	private String department;
	private String location;
	
	
}
