package com.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {
    private String jdbcUrl;
    private String dbUser;
    private String dbPassword;

    public DBConn(String jdbcUrl, String dbUser, String dbPassword) {
        this.dbPassword = dbPassword;
        this.dbUser = dbUser;
        this.jdbcUrl = jdbcUrl;

    }

    public Connection connect() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return connection;
    }
}