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
import javax.servlet.http.HttpSession;

@WebServlet("/AddProjectServlet")
public class AddProjectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection details
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/kl";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "0706";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String projectName = request.getParameter("projectName");
        String description = request.getParameter("description");

        // Check for missing parameters
        if (username == null || projectName == null || description == null) {
            // Redirect to error page or show an error message
            response.sendRedirect("error.jsp?message=Missing parameters");
            return;
        }

        // Insert project into database
        String sql = "INSERT INTO projects (project_name, description, admin_username) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, projectName);
            stmt.setString(2, description);
            stmt.setString(3, username);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                // Redirect to success page or admin page
                response.sendRedirect("AdminServlet?message=Project added successfully");
            } else {
                // Handle case where no rows were affected
                response.sendRedirect("error.jsp?message=Failed to add project");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Redirect to error page or show an error message
            response.sendRedirect("error.jsp?message=Database error: " + e.getMessage());
        }
    }
}
