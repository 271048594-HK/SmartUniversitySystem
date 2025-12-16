package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO {

    // --- C: CREATE (Enroll Student - Course & Enrollment Module) ---
    public boolean enrollStudent(String studentId, String courseCode) {
        // enrollment_id is auto-incremented. grade_point is initially NULL.
        String sql = "INSERT INTO enrollment (student_id, course_code) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, studentId);
            pstmt.setString(2, courseCode);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error enrolling student: " + e.getMessage());
            return false;
        }
    }

    // --- R: RETRIEVE (Check Prerequisite Status - Core Logic) ---
    public boolean hasPassedCourse(String studentId, String courseCode) {
        // Checks if a grade exists AND if it is a passing grade (>= 2.0 as per GradeRecord.java logic)
        String sql = "SELECT grade_point FROM enrollment WHERE student_id = ? AND course_code = ? AND grade_point >= 2.0";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, studentId);
            pstmt.setString(2, courseCode);
            ResultSet rs = pstmt.executeQuery();

            return rs.next(); // True if a matching, passing record is found

        } catch (SQLException e) {
            System.err.println("Error checking prerequisite: " + e.getMessage());
            return false;
        }
    }

    // --- U: UPDATE (Assign Grade - Grading Module) ---
    public boolean assignGrade(String studentId, String courseCode, double gradePoint) {
        String sql = "UPDATE enrollment SET grade_point = ? WHERE student_id = ? AND course_code = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Validate grade is within 0.0 to 4.0 range
            if (gradePoint < 0.0 || gradePoint > 4.0) return false;

            pstmt.setDouble(1, gradePoint);
            pstmt.setString(2, studentId);
            pstmt.setString(3, courseCode);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error assigning grade: " + e.getMessage());
            return false;
        }
    }

    // --- D: DELETE (Drop Course - Course & Enrollment Module) ---
    public boolean dropCourse(String studentId, String courseCode) {
        String sql = "DELETE FROM enrollment WHERE student_id = ? AND course_code = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, studentId);
            pstmt.setString(2, courseCode);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error dropping course: " + e.getMessage());
            return false;
        }
    }
}