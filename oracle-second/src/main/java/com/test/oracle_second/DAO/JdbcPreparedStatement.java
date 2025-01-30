package com.test.oracle_second.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcPreparedStatement {

    public static void setPreparedStatement(PreparedStatement preparedStatement, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            Object parameter = parameters[i];
            if (parameter instanceof String) {
                preparedStatement.setString(i + 1, (String) parameter);
            } else if (parameter instanceof Integer) {
                preparedStatement.setInt(i + 1, (Integer) parameter);
            } else if (parameter instanceof Double) {
                preparedStatement.setDouble(i + 1, (Double) parameter);
            } else if (parameter instanceof Boolean) {
                preparedStatement.setBoolean(i + 1, (Boolean) parameter);
            } else if (parameter instanceof java.sql.Date) {
                preparedStatement.setDate(i + 1, (java.sql.Date) parameter);
            } else if (parameter instanceof java.time.LocalDate) {
                preparedStatement.setDate(i + 1, java.sql.Date.valueOf((java.time.LocalDate) parameter));
            } else {
                preparedStatement.setObject(i + 1, parameter); // Fallback for other types
            }
        }
    }
}

