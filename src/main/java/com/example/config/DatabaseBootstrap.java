package com.example.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DatabaseBootstrap implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        DatabaseConn dbConnection = new DatabaseConn("jdbc:postgresql://localhost:5432/todo?sslmode=disable", "todo",
                "todo");

        Statement statement = null;
        Statement statement2 = null;

        try {
            statement = dbConnection.connect().createStatement();
            statement.execute("CREATE DATABASE IF NOT EXISTS todos");
            DatabaseConn dbConnection2 = new DatabaseConn("jdbc:postgresql://localhost:5432/todo?sslmode=disable",
                    "todo",
                    "todo");
            statement2 = dbConnection2.connect().createStatement();

            statement2.execute(
                    "create table if not exists todo(id bigint not null, task text, PRIMARY KEY(id))");

            sce.getServletContext().setAttribute("dbConnection", dbConnection2.connect());

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

    public void contextDestroyed(ServletContextEvent sce) {
        Connection connection = (Connection) sce.getServletContext().getAttribute("dbConnection");

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
        }

    }
}