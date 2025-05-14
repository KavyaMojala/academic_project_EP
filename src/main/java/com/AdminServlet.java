package com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection details
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/kl";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "0706";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Project> projectDetails = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT id, project_name, description FROM projects");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Project project = new Project(0, null, null);
                project.setId(rs.getInt("id"));
                project.setProjectName(rs.getString("project_name"));
                project.setDescription(rs.getString("description"));
                projectDetails.add(project);
            }

            request.setAttribute("projectDetails", projectDetails);
            request.getRequestDispatcher("admin.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            // Forward to an error page if needed
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}