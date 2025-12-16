package database;

import core.Faculty;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacultyDAO {

    public boolean addFaculty(Faculty f) {
        String sql = "INSERT INTO faculty (id, name, age, email, employee_id, dept_code, designation) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, f.getId());
            pstmt.setString(2, f.getName());
            pstmt.setInt(3, f.getAge());
            pstmt.setString(4, f.getEmail());
            pstmt.setString(5, f.getEmployeeId());
            pstmt.setString(6, f.getDepartmentCode());
            pstmt.setString(7, f.getDesignation());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding faculty: " + e.getMessage());
            return false;
        }
    }

    public Faculty getFacultyById(String id) {
        String sql = "SELECT * FROM faculty WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Faculty(
                        rs.getString("id"), rs.getString("name"), rs.getInt("age"),
                        rs.getString("email"), rs.getString("employee_id"),
                        rs.getString("dept_code"), rs.getString("designation")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching faculty: " + e.getMessage());
        }
        return null;
    }

    //  SEARCH
    public List<Faculty> searchFaculty(String query) {
        List<Faculty> list = new ArrayList<>();
        String sql = "SELECT * FROM faculty WHERE id LIKE ? OR name LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + query + "%");
            pstmt.setString(2, "%" + query + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(new Faculty(
                        rs.getString("id"), rs.getString("name"), rs.getInt("age"),
                        rs.getString("email"), rs.getString("employee_id"),
                        rs.getString("dept_code"), rs.getString("designation")
                ));
            }
        } catch (SQLException e) { System.err.println("Search error: " + e.getMessage()); }
        return list;
    }

    public boolean deleteFaculty(String id) {
        String sql = "DELETE FROM faculty WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }
}