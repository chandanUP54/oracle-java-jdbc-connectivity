package com.test.oracle_second.controller;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class EmployeeQuery {
    private static final String URL = "jdbc:oracle:thin:@//localhost:1521/ORCLPDB";
    private static final String USERNAME = "user1";
    private static final String PASSWORD = "password1";

    public static void main(String[] args) {
        int empId = 101;  // Example EMP_ID
        List<String> locations = Arrays.asList("New York", "Los Angeles");  // Example locations

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "SELECT EMP_ID, DEPARTMENT, EMP_NAME, LOCATION FROM EMP_TBG " +
                         "WHERE EMP_ID = ? OR LOCATION IN (?)";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, empId);
                statement.setString(2, String.join("','", locations)); // Handling multiple locations

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        System.out.println("EMP_ID: " + resultSet.getInt("EMP_ID") +
                                ", DEPARTMENT: " + resultSet.getString("DEPARTMENT") +
                                ", EMP_NAME: " + resultSet.getString("EMP_NAME") +
                                ", LOCATION: " + resultSet.getString("LOCATION"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

