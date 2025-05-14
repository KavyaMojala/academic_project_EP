<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ page import="java.util.List" %>
<%@ page import="com.User" %>
<%@ page import="com.Project" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <script type="text/javascript">
        function toggleUserDetails() {
            var userDetailsSection = document.getElementById("userDetailsSection");
            if (userDetailsSection.style.display === "none") {
                userDetailsSection.style.display = "block";
            } else {
                userDetailsSection.style.display = "none";
            }
        }

        function toggleProjectDetails() {
            var projectDetailsSection = document.getElementById("projectDetailsSection");
            if (projectDetailsSection.style.display === "none") {
                projectDetailsSection.style.display = "block";
            } else {
                projectDetailsSection.style.display = "none";
            }
        }
    </script>
</head>
<body>
    <h1>Welcome, Admin!</h1>
    <p>Hello, <%= session.getAttribute("username") %>. You have administrative privileges.</p>
    <button onclick="toggleUserDetails()">Show User Details</button>
    <div id="userDetailsSection">
        <h2>User Details</h2>
        <table>
            <tr>
                <th>Username</th>
                <th>Role</th>
                <!-- Add more columns as needed -->
            </tr>
            <%
                List<User> users = (List<User>) request.getAttribute("userDetails");
                if (users != null) {
                    for (User user : users) {
            %>
            <tr>
                <td><%= user.getUsername() %></td>
                <td><%= user.getRole() %></td>
                <!-- Add more columns as needed -->
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
    
    <button onclick="toggleProjectDetails()">Show Project Details</button>
    <div id="projectDetailsSection">
        <h2>Project Details</h2>
        <table>
            <tr>
                <th>Project Name</th>
                <th>Description</th>
                <th>Actions</th>
                <!-- Add more columns as needed -->
            </tr>
            <%
                List<Project> projects = (List<Project>) request.getAttribute("projectDetails");
                if (projects != null) {
                    for (Project project : projects) {
            %>
            <tr>
                <td><%= project.getProjectName() %></td>
                <td><%= project.getDescription() %></td>
                <td>
                    <!-- CRUD operation buttons/forms for each project -->
                    
                    <form action="DeleteProjectServlet" method="post" style="display:inline;">
                        <input type="hidden" name="projectId" value="<%= project.getId() %>">
                        <input type="submit" value="Delete">
                    </form>
                </td>
            </tr>
            <%
                    }
                }
            %>
        </table>
        
        <h3>Add New Project</h3>
        <form action="AddProjectServlet" method="post">
            <label for="projectName">Project Name:</label>
            <input type="text" id="projectName" name="projectName"><br>
            <label for="description">Description:</label>
            <textarea id="description" name="description"></textarea><br>
            <input type="submit" value="Add Project">
        </form>
    </div>

    <a href="logout.jsp">Logout</a>
</body>
</html>