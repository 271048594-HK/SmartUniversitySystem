package database;

import core.Department;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {

    // CREATE DEPARTMENT
    public boolean addDepartment(String code, String name, double budget) {
        String sql = "INSERT INTO departments (dept_code, name, budget) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, code);
            pstmt.setString(2, name);
            pstmt.setDouble(3, budget);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error adding department: " + e.getMessage());
            return false;
        }
    }

    //  R: LIST ALL DEPARTMENTS
    public List<String> getAllDepartments() {
        List<String> depts = new ArrayList<>();
        String sql = "SELECT * FROM departments";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                depts.add(String.format("Code: %s | Name: %s | Budget: $%.2f",
                        rs.getString("dept_code"),
                        rs.getString("name"),
                        rs.getDouble("budget")));
            }

        } catch (SQLException e) {
            System.err.println("Error listing departments: " + e.getMessage());
        }
        return depts;
    }

    // DELETE DEPARTMENT
    public boolean deleteDepartment(String code) {
        String sql = "DELETE FROM departments WHERE dept_code = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, code);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting department (Ensure no Faculty are assigned first): " + e.getMessage());
            return false;
        }
    }

    // REPORT:
    // This method aggregates counts from multiple tables for the System Report
    public String getSystemStats() {
        StringBuilder stats = new StringBuilder();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // Count Students
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM students");
            if(rs.next()) stats.append("Total Students: ").append(rs.getInt(1)).append("\n");

            // Count Faculty
            rs = stmt.executeQuery("SELECT COUNT(*) FROM faculty");
            if(rs.next()) stats.append("Total Faculty:  ").append(rs.getInt(1)).append("\n");

            // Count Courses
            rs = stmt.executeQuery("SELECT COUNT(*) FROM courses");
            if(rs.next()) stats.append("Total Courses:  ").append(rs.getInt(1)).append("\n");

            // Sum Budget
            rs = stmt.executeQuery("SELECT SUM(budget) FROM departments");
            if(rs.next()) stats.append("Total Budget:   ").append(rs.getDouble(1)).append("\n");

        } catch (SQLException e) {
            return "Error generating stats: " + e.getMessage();
        }
        return stats.toString();
    }
}
