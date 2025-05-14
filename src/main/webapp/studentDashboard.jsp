<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.Project" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f4f4f4;
        }
        .select-button {
            padding: 5px 10px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .select-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <h1>Welcome, <%= session.getAttribute("username") %>!</h1>
    <h2>Projects Available</h2>
    <table>
        <tr>
            <th>Project ID</th>
            <th>Project Name</th>
            <th>Description</th>
            <th>Select</th>
        </tr>
        <%
            List<Project> projects = (List<Project>) request.getAttribute("projectDetails");
            if (projects != null && !projects.isEmpty()) {
                for (Project project : projects) {
        %>
            <tr>
                <td><%= project.getId() %></td>
                <td><%= project.getProjectName() %></td>
                <td><%= project.getDescription() %></td>
                <td>
                    <form action="SelectProjectServlet" method="post">
                        <input type="hidden" name="projectId" value="<%= project.getId() %>">
                        <input type="submit" value="Select" class="select-button">
                    </form>
                </td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="4">No projects available</td>
            </tr>
        <%
            }
        %>
    </table>

    <h2>Your Selected Projects</h2>
    <table>
        <tr>
            <th>Project ID</th>
            <th>Project Name</th>
            <th>Description</th>
        </tr>
        <%
            List<Project> selectedProjects = (List<Project>) request.getAttribute("selectedProjects");
            if (selectedProjects != null && !selectedProjects.isEmpty()) {
                for (Project project : selectedProjects) {
        %>
            <tr>
                <td><%= project.getId() %></td>
                <td><%= project.getProjectName() %></td>
                <td><%= project.getDescription() %></td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="3">No selected projects</td>
            </tr>
        <%
            }
        %>
    </table>
</body>
</html>
