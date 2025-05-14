package com;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;

public class ProjectDAO {
    // Example method to get all projects
    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        try {
            Connection conn = DBUtil.getConnection(); // Assume you have a Database class to get the connection
            String query = "SELECT * FROM projects";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Project project = new Project(0, query, query); // Use the default constructor
                project.setId(rs.getInt("id"));
                project.setProjectName(rs.getString("project_name"));
                project.setDescription(rs.getString("description"));
                projects.add(project);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return projects;
    }
}