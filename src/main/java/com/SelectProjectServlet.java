package com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DBUtil;

@WebServlet("/SelectProjectServlet")
public class SelectProjectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String projectName = request.getParameter("projectName");
        String projectDescription = request.getParameter("projectDescription");

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO selected_projects (project_name, project_description) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, projectName);
                stmt.setString(2, projectDescription);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error", e);
        }

        response.sendRedirect("success.jsp");
    }
}
