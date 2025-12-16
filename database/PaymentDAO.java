package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    // RECORD PAYMENT
    public boolean recordPayment(String personId, double amount, String type) {
        // type should be 'TUITION' or 'SALARY'
        String sql = "INSERT INTO payments (person_id, amount, payment_type, payment_date) VALUES (?, ?, ?, NOW())";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, personId);
            pstmt.setDouble(2, amount);
            pstmt.setString(3, type);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error recording payment: " + e.getMessage());
            return false;
        }
    }

    //  GET PAYMENT HISTORY
    public List<String> getPaymentHistory(String personId) {
        List<String> history = new ArrayList<>();
        String sql = "SELECT * FROM payments WHERE person_id = ? ORDER BY payment_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, personId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String record = String.format("ID: %d | Type: %s | Amount: $%.2f | Date: %s",
                        rs.getInt("payment_id"),
                        rs.getString("payment_type"),
                        rs.getDouble("amount"),
                        rs.getDate("payment_date"));
                history.add(record);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving history: " + e.getMessage());
        }
        return history;
    }
}
