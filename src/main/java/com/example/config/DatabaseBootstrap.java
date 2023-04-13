package com.example.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class DatabaseBootstrap implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        DBConn dbConnection = new DBConn("jdbc:postgresql://localhost:5432/todo?sslmode=disable", "todo",
                "todo");

        Statement statement = null;
        Statement statement2 = null;

        try {
            statement = dbConnection.connect().createStatement();
            DBConn dbConnection2 = new DBConn("jdbc:postgresql://localhost:5432/todo?sslmode=disable",
                    "todo",
                    "todo");
            statement2 = dbConnection2.connect().createStatement();
            statement2.execute(
                    "create table if not exists todo(id bigserial primary key, task text");

            sce.getServletContext().setAttribute("dbConn", dbConnection2.connect());

        } catch (SQLException sqEx) {
            sqEx.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();

                if (statement2 != null)
                    statement2.close();

            } catch (SQLException sqlEx2) {
                sqlEx2.printStackTrace();
            }
        }
    }

    public void contextRm(ServletContextEvent sce) {
        Connection connection = (Connection) sce.getServletContext().getAttribute("dbConn");

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
        }

    }
}