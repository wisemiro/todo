package com.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class DatabaseBootstrap implements ServletContextListener {
    private static final String DB_URL = "jdbc:postgresql://172.18.0.2:5432/todo";
    private static final String DB_USERNAME = "todo";
    private static final String DB_PASSWORD = "todo";
    private Connection conn;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("Connected to Database.");
            createTables(conn);
            System.out.println("Tables created");
            event.getServletContext().setAttribute("dbConnection", conn);
            System.out.println("Saved connection to ServletContext");
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to database", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        try {
            conn.close();
            System.out.println("Connection closed.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createTables(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            // Create todo
            stmt.execute("CREATE TABLE IF NOT EXISTS todo (id SERIAL PRIMARY KEY, task VARCHAR(255) NOT NULL)");
        }
    }

    public Connection getConnection() {
        return conn;
    }
}
