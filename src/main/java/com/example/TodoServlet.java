package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.models.Todo;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/* to access use: http://0.0.0.0:8080/todo-app/todo/ */

@WebServlet(name = "todoServlet", urlPatterns = { "/todo/*" })
public class TodoServlet extends HttpServlet {

    private Map<String, Controller> controllers;

    @Override
    public void init() throws ServletException {
        super.init();
        controllers = new HashMap<>();
        controllers.put("/todo/", new ListTodoController());
        controllers.put("/todo/one/", new GetTodoController());
        controllers.put("/todo/create/", new CreateTodoController());
        controllers.put("/todo/update/", new UpdateTodoController());
        controllers.put("/todo/delete/", new DeleteTodoController());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "/";
        }

        Controller controller = controllers.get(pathInfo);
        if (controller == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        controller.handleRequest(request, response, getServletContext());
    }

}

interface Controller {
    void handleRequest(HttpServletRequest request, HttpServletResponse response,
            ServletContext servletContext)
            throws ServletException, IOException;
}

// ListTodos
class ListTodoController implements Controller {

    public void handleRequest(HttpServletRequest request, HttpServletResponse response, ServletContext srv)
            throws ServletException, IOException {
        Connection dbConnection = (Connection) srv.getAttribute("dbConnection");
        response.setContentType("application/json");
        List<Todo> todos = new ArrayList<Todo>();

        try {
            PreparedStatement statement = dbConnection.prepareStatement("SELECT * FROM todo");
            statement.execute();
            ResultSet result = statement.getResultSet();

            while (result.next()) {
                Todo todo = new Todo();
                todo.setTask(result.getString("task"));
                todo.setId(result.getInt("id"));
                todos.add(todo);
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().print(mapper.writeValueAsString(todos));
    }

}

// GetTodo
class GetTodoController implements Controller {

    public void handleRequest(HttpServletRequest request, HttpServletResponse response, ServletContext srv)
            throws ServletException, IOException {

        Connection dbConnection = (Connection) srv.getAttribute("dbConnection");
        response.setContentType("application/json");
        Todo todo = new Todo();
        int todoId = Integer.parseInt(request.getParameter("id"));

        try {
            PreparedStatement statement = dbConnection.prepareStatement("SELECT * FROM todo where id = ?");
            statement.setInt(1, todoId);
            statement.execute();
            ResultSet result = statement.getResultSet();
            if (result.next()) {
                int id = result.getInt("id");
                todo.setId(id);
                todo.setTask(result.getString("task"));
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().print(mapper.writeValueAsString(todo));
    }

}

// CreateTodo
class CreateTodoController implements Controller {
    public void handleRequest(HttpServletRequest request, HttpServletResponse response, ServletContext srv)
            throws ServletException, IOException {

        Connection dbConnection = (Connection) srv.getAttribute("dbConnection");

        ObjectMapper mapper = new ObjectMapper();
        Todo todo = mapper.readValue(request.getInputStream(), Todo.class);

        try {
            PreparedStatement statement = dbConnection.prepareStatement(
                    "insert into todo (task) values (?)",
                    Statement.RETURN_GENERATED_KEYS);
            // statement.setInt(1, todo.getId());
            statement.setString(1, todo.getTask());
            statement.executeUpdate();
            // ResultSet tableKeys = statement.getGeneratedKeys();
            // tableKeys.next();
            // int autoGeneratedID = tableKeys.getInt(1);
            // todo.setId(autoGeneratedID);

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                todo.setId(generatedId);
            }
            rs.close();

            response.setStatus(HttpServletResponse.SC_CREATED);

        } catch (SQLException sqlEx) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            sqlEx.printStackTrace();
        }
    }

}

// UpdateTodo
class UpdateTodoController implements Controller {
    public void handleRequest(HttpServletRequest request, HttpServletResponse response, ServletContext srv)
            throws ServletException, IOException {

        Connection dbConnection = (Connection) srv.getAttribute("dbConnection");

        ObjectMapper mapper = new ObjectMapper();
        Todo todo = mapper.readValue(request.getInputStream(), Todo.class);
        int todoId = Integer.parseInt(request.getParameter("id"));

        try {
            PreparedStatement statement = dbConnection.prepareStatement("UPDATE todo SET task = ? WHERE id = ?");
            statement.setString(1, todo.getTask());
            statement.setInt(2, todoId);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 1) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }

        } catch (SQLException sqlEx) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            sqlEx.printStackTrace();
        }
    }

}

// DeleteTodo
class DeleteTodoController implements Controller {
    public void handleRequest(HttpServletRequest request, HttpServletResponse response, ServletContext srv)
            throws ServletException, IOException {

        Connection dbConnection = (Connection) srv.getAttribute("dbConnection");

        int todoId = Integer.parseInt(request.getParameter("id"));

        try {
            PreparedStatement statement = dbConnection.prepareStatement("DELETE FROM todo WHERE id = ?");
            statement.setInt(1, todoId);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 1) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }

        } catch (SQLException sqlEx) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            sqlEx.printStackTrace();
        }
    }

}