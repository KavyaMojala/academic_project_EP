package com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteProjectServlet")
public class DeleteProjectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection details
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/kl";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "0706";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String projectId = request.getParameter("projectId");

        if (projectId == null) {
            response.sendRedirect("AdminServlet");
            return;
        }

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM projects WHERE id = ?")) {

            stmt.setInt(1, Integer.parseInt(projectId));
            stmt.executeUpdate();
            response.sendRedirect("AdminServlet");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("AdminServlet");
        }
    }
}