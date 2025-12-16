package database;

import java.sql.*;

public class TuitionDAO {

    private static final double PER_CREDIT_RATE = 100.0;


    //  CALCULATE TUITION FEE
    public double calculateTotalFee(String studentId) {
        // JOIN query to sum credit hours for all courses the student is currently enrolled in
        String sql = "SELECT SUM(c.credit_hours) AS total_credits " +
                "FROM enrollment e " +
                "JOIN courses c ON e.course_code = c.course_code " + // Ensure column names match your DB schema
                "WHERE e.student_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int totalCredits = rs.getInt("total_credits");
                return totalCredits * PER_CREDIT_RATE;
            }

        } catch (SQLException e) {
            System.err.println("Error calculating tuition: " + e.getMessage());
        }
        return 0.0;
    }
}
