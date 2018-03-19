package com.proquest.interview.util;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtil {

    private Connection connection;

    public final Connection getConnection() throws SQLException, ClassNotFoundException {
        if(connection == null){
            connection = DatabaseUtil.getConnection();
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if(connection != null)
                connection.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
}
