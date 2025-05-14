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

@WebServlet("/UpdateProjectServlet")
public class UpdateProjectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/kl";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "0706";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int projectId = Integer.parseInt(request.getParameter("projectId"));
        String projectName = request.getParameter("projectName");
        String description = request.getParameter("description");

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("UPDATE projects SET project_name = ?, description = ? WHERE id = ?")) {
            stmt.setString(1, projectName);
            stmt.setString(2, description);
            stmt.setInt(3, projectId);
            stmt.executeUpdate();
            response.sendRedirect("admin.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("admin.jsp");
        }
    }
}