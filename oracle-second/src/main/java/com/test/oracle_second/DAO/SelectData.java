package com.test.oracle_second.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;


@Repository
public class SelectData {

	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	public JSONArray selectDbData(Connection connection, String query, Object... inputs) throws SQLException {
		JSONArray jsonArray = new JSONArray();

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			JdbcPreparedStatement.setPreparedStatement(preparedStatement, inputs);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					JSONObject jsonObject = new JSONObject();
					int columnCount = resultSet.getMetaData().getColumnCount();

					for (int i = 1; i <= columnCount; i++) {
						String columnName = resultSet.getMetaData().getColumnName(i);
						Object columnValue = resultSet.getObject(i);
						jsonObject.put(columnName, columnValue);
					}

					jsonArray.put(jsonObject);
				}
			}
		}
		return jsonArray;
	}

}
