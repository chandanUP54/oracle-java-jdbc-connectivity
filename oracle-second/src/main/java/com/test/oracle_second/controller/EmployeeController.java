package com.test.oracle_second.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.oracle_second.DAO.EmployeeDAO;
import com.test.oracle_second.DAO.SelectData;
import com.test.oracle_second.jdbc.OracleJDBCUtil;
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

//	@PostMapping("/getEmployees")
//	public ResponseEntity<List<Map<String, Object>>> getEmployees(@RequestBody Map<String, Object> request) {
//
//		int empId = (int) request.get("empId");
//		List<String> locations = (List<String>) request.get("locations");
//
//		List<Map<String, Object>> employees = new ArrayList<>();
//
//		try (Connection connection = OracleJDBCUtil.getConnection();) {
//
//			String placeholders = String.join(",", locations.stream().map(l -> "?").toArray(String[]::new));
//
//			String sql = "SELECT EMP_ID, DEPARTMENT, EMP_NAME, LOCATION FROM EMP_TBG "
//					+ "WHERE EMP_ID = ? OR LOCATION IN (" + placeholders + ")";
//
//			// now setting the params -->>
//			try (PreparedStatement statement = connection.prepareStatement(sql)) {
//				statement.setInt(1, empId);
//
//				for (int i = 0; i < locations.size(); i++) {
//					statement.setString(i + 2, locations.get(i));
//				}
//
//				try (ResultSet resultSet = statement.executeQuery()) {
//					while (resultSet.next()) {
//						Map<String, Object> employee = new HashMap<>();
//						employee.put("EMP_ID", resultSet.getInt("EMP_ID"));
//						employee.put("DEPARTMENT", resultSet.getString("DEPARTMENT"));
//						employee.put("EMP_NAME", resultSet.getString("EMP_NAME"));
//						employee.put("LOCATION", resultSet.getString("LOCATION"));
//						employees.add(employee);
//					}
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return ResponseEntity.status(500).build(); // Return error status
//		}
//
//		return ResponseEntity.ok(employees);
//	}

	@Autowired
	private SelectData selectData;
	
//	{
//	    "EMP_ID": 1,
//	    "LOCATION": [
//	        "Lucknow","NCR","Ahmedabad"
//	    ],
//	    "DEPARTMENT":["IT"]
//	}

	@PostMapping("/getEmployees")
	public ResponseEntity<List<Map<String, Object>>> getEmployees(@RequestBody Map<String, Object> request) {

		int empId = (int) request.get("EMP_ID");
		List<String> locations = (List<String>) request.get("LOCATION");
		List<String> department = (List<String>) request.get("DEPARTMENT");

		List<Map<String, Object>> employees = new ArrayList<>();

		try (Connection connection = OracleJDBCUtil.getConnection();) {

			String querySql = "SELECT * FROM TABLE_SQL WHERE SCREEN_NAME=?";
			String dynamicSql = null;

			try (PreparedStatement queryStatement = connection.prepareStatement(querySql)) {
				queryStatement.setString(1, "DOC_CD");
				try (ResultSet queryResult = queryStatement.executeQuery()) {
					if (queryResult.next()) {
						dynamicSql = queryResult.getString("QUERY");
					}
				}
			}

			if (dynamicSql == null) {
				return ResponseEntity.status(500)
						.body(Collections.singletonList(Map.of("error", "Query not found for QUERY_ID = 2")));
			}

			Map<Integer, Object> paramMap = new HashMap<>();
			List<String> finalSqlParts = new ArrayList<>();
			int paramIndex = 1;

			Pattern pattern = Pattern.compile(":(\\p{Upper}\\w*)(?=\\b|[^a-zA-Z0-9])");
//	            Pattern pattern = Pattern.compile(":(\\w+)");
			Matcher matcher = pattern.matcher(dynamicSql);
			int lastEnd = 0;

			while (matcher.find()) {
				String paramName = matcher.group(1);
				Object paramValue = request.get(paramName);

				finalSqlParts.add(dynamicSql.substring(lastEnd, matcher.start()));
				lastEnd = matcher.end();

				if (paramValue instanceof List<?>) {
					List<?> values = (List<?>) paramValue;
					List<String> questionMarks = new ArrayList<>();
					for (Object value : values) {
						questionMarks.add("?");
						paramMap.put(paramIndex++, value);
					}
					finalSqlParts.add(String.join(",", questionMarks));
				} else {
					finalSqlParts.add("?");
					paramMap.put(paramIndex++, paramValue);
				}
			}
			finalSqlParts.add(dynamicSql.substring(lastEnd));

			String finalSql = String.join("", finalSqlParts);

			try {

				List<Object> xyz = new ArrayList<>();

				xyz.add(empId);

				// Add all locations to the list
				xyz.addAll(locations);

				// Add all departments to the list
				xyz.addAll(department);

				
				JSONArray jsonArray = selectData.selectDbData(connection, finalSql, xyz.toArray());
				if (jsonArray.length() == 0) {
					return null;
				}

				JSONObject jsonObject = jsonArray.getJSONObject(0);

				System.out.println(jsonObject);
				System.out.println(jsonArray);

			} finally {

			}

		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(500).build();
		}

		return ResponseEntity.ok(employees);
	}

}