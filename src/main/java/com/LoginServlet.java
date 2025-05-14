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
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/kl";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "0706";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String role = fetchUserRole(conn, username, password);

            if (role != null) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                session.setAttribute("role", role);

                if ("admin".equalsIgnoreCase(role)) {
                    List<User> users = fetchAllUsers(conn);
                    request.setAttribute("userDetails", users);
                    List<Project> projects = fetchAdminProjects(conn, username);
                    request.setAttribute("projectDetails", projects);
                    request.getRequestDispatcher("admin.jsp").forward(request, response);
                } else if ("student".equalsIgnoreCase(role)) {
                    List<Project> projects = fetchAllProjects(conn);
                    request.setAttribute("projectDetails", projects);
                    request.getRequestDispatcher("student.jsp").forward(request, response);
                } else {
                    response.sendRedirect("guest.jsp");
                }
            } else {
                response.sendRedirect("login.jsp?error=invalid_credentials");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=database_error");
        }
    }

    private String fetchUserRole(Connection conn, String username, String password) throws SQLException {
        String role = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT role FROM users WHERE username = ? AND password = ?")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    role = rs.getString("role");
                }
            }
        }
        return role;
    }

    private List<User> fetchAllUsers(Connection conn) throws SQLException {
        List<User> users = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT username, role FROM users")) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String username = rs.getString("username");
                    String role = rs.getString("role");
                    users.add(new User(username, role));
                }
            }
        }
        return users;
    }

    private List<Project> fetchAdminProjects(Connection conn, String username) throws SQLException {
        List<Project> projects = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT id, project_name, description FROM projects WHERE admin_username = ?")) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int projectId = rs.getInt("id");
                    String projectName = rs.getString("project_name");
                    String description = rs.getString("description");
                    projects.add(new Project(projectId, projectName, description));
                }
            }
        }
        return projects;
    }

    private List<Project> fetchAllProjects(Connection conn) throws SQLException {
        List<Project> projects = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT id, project_name, description FROM projects")) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int projectId = rs.getInt("id");
                    String projectName = rs.getString("project_name");
                    String description = rs.getString("description");
                    projects.add(new Project(projectId, projectName, description));
                }
            }
        }
        return projects;
    }
}
