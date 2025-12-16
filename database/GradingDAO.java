package database;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class GradingDAO {

    // RETRIEVE (Get All Grades for GPA Calculation)
    public Map<String, Double> getGradesForStudent(String studentId) {
        // Map: Course Code to  Grade Point
        Map<String, Double> grades = new HashMap<>();

        // Only select records where a grade has actually been assigned (grade_point is NOT NULL)
        String sql = "SELECT course_code, grade_point FROM enrollment WHERE student_id = ? AND grade_point IS NOT NULL";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                grades.put(rs.getString("course_code"), rs.getDouble("grade_point"));
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving grades for student: " + e.getMessage());
        }
        return grades;
    }
}