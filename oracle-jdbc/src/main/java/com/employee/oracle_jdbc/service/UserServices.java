package com.employee.oracle_jdbc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.employee.oracle_jdbc.database.UserDB;
import com.employee.oracle_jdbc.model.User;

@Service
public class UserServices {

	@Autowired
	private UserDB userDB;

	public User saveUser(User user) {

		String sql = "INSERT INTO USER_TABLE ( NAME, EMAIL) VALUES (?, ?)";

		try {
			Connection connection = userDB.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}
