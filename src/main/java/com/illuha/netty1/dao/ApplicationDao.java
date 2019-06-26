package com.illuha.netty1.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ApplicationDao {
    private Connection connection;

    public ApplicationDao(String connectionUrl) {
        getConnection(connectionUrl);
        createTables();
    }

    public Connection getConnection(String connectionUrl) {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(connectionUrl);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            System.out.println("Connected to database #1");
        }
        return connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void createTables() {

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE CUSTOMER (");
        sb.append("    CUSTOMER_ID VARCHAR(10) PRIMARY KEY,");
        sb.append("    CUSTOMER_NAME VARCHAR(50))");

        Statement stmt;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(sb.toString());
        } catch (SQLException e) {
            if ("X0Y32".equals(e.getSQLState())) {
                System.out.println("Warning: tables already exist");
            } else
                e.printStackTrace();
        }
    }


}
