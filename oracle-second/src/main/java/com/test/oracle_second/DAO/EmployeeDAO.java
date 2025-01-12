package com.test.oracle_second.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.test.oracle_second.jdbc.OracleJDBCUtil;
import com.test.oracle_second.model.Employee;

public class EmployeeDAO {

	// Add a new employee
	public void addEmployee(Employee employee) {
		String sql = "INSERT INTO EMP_TBG ( EMP_NAME, DEPARTMENT, LOCATION) VALUES (?, ?, ?)";

		try {
			Connection connection = OracleJDBCUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, employee.getEmpName());
			ps.setString(2, employee.getDepartment());
			ps.setString(3, employee.getLocation());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Fetch all employees
	public List<Employee> getAllEmployees() {
		List<Employee> employees = new ArrayList<>();
		String sql = "SELECT EMP_ID, EMP_NAME, DEPARTMENT, LOCATION FROM EMP_TBG";
		try {
			Connection connection = OracleJDBCUtil.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Employee employee = new Employee();
				employee.setEmpId(rs.getInt("EMP_ID"));
				employee.setEmpName(rs.getString("EMP_NAME"));
				employee.setDepartment(rs.getString("DEPARTMENT"));
				employee.setLocation(rs.getString("LOCATION"));

				employees.add(employee);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employees;
	}
}
