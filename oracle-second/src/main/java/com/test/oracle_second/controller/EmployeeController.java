package com.test.oracle_second.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.oracle_second.DAO.EmployeeDAO;
import com.test.oracle_second.model.Employee;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    @PostMapping("/save")
    public String addEmployee(@RequestBody Employee employee) {
        employeeDAO.addEmployee(employee);
        return "Employee added successfully!";
    }

    @GetMapping("/getAll")
    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }
}