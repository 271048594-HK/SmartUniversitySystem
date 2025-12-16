package database;

import core.Staff;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {

    public boolean addStaff(Staff s) {
        String sql = "INSERT INTO staff (id, name, age, email, staff_id, role, shift) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, s.getId());
            pstmt.setString(2, s.getName());
            pstmt.setInt(3, s.getAge());
            pstmt.setString(4, s.getEmail());
            pstmt.setString(5, s.getStaffId());
            pstmt.setString(6, s.getRole());
            pstmt.setString(7, s.getShift());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding staff: " + e.getMessage());
            return false;
        }
    }

    public List<Staff> searchStaff(String query) {
        List<Staff> list = new ArrayList<>();
        String sql = "SELECT * FROM staff WHERE id LIKE ? OR name LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + query + "%");
            pstmt.setString(2, "%" + query + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(new Staff(
                        rs.getString("id"), rs.getString("name"), rs.getInt("age"),
                        rs.getString("email"), rs.getString("staff_id"),
                        rs.getString("role"), rs.getString("shift")
                ));
            }
        } catch (SQLException e) { System.err.println("Search error: " + e.getMessage()); }
        return list;
    }

    public boolean deleteStaff(String id) {
        String sql = "DELETE FROM staff WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }
}