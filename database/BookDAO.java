package database;

import java.sql.*;

public class BookDAO {

    // ADD BOOK
    public boolean addBook(String isbn, String title, String author, int copies) {
        String sql = "INSERT INTO library_books (isbn, title, author, available_copies) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, isbn);
            pstmt.setString(2, title);
            pstmt.setString(3, author);
            pstmt.setInt(4, copies);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding book: " + e.getMessage());
            return false;
        }
    }

    //  ISSUE BOOK
    public boolean issueBook(String isbn, String studentId) {
        //  Check copies
        if (getAvailableCopies(isbn) <= 0) return false;

        String sql = "INSERT INTO library_transactions (isbn, student_id, issue_date) VALUES (?, ?, NOW())";
        String updateSql = "UPDATE library_books SET available_copies = available_copies - 1 WHERE isbn = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Transaction: Add record AND decrease copies
            try (PreparedStatement p1 = conn.prepareStatement(sql);
                 PreparedStatement p2 = conn.prepareStatement(updateSql)) {

                p1.setString(1, isbn);
                p1.setString(2, studentId);
                p1.executeUpdate();

                p2.setString(1, isbn);
                p2.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Issue failed: " + e.getMessage());
            return false;
        }
    }

    // RETURN BOOK
    public boolean returnBook(String isbn, String studentId) {
        String sql = "UPDATE library_transactions SET return_date = NOW() WHERE isbn = ? AND student_id = ? AND return_date IS NULL";
        String updateSql = "UPDATE library_books SET available_copies = available_copies + 1 WHERE isbn = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            try (PreparedStatement p1 = conn.prepareStatement(sql);
                 PreparedStatement p2 = conn.prepareStatement(updateSql)) {

                p1.setString(1, isbn);
                p1.setString(2, studentId);
                int rows = p1.executeUpdate();

                if (rows > 0) {
                    p2.setString(1, isbn);
                    p2.executeUpdate();
                    return true;
                }
            }
        } catch (SQLException e) { return false; }
        return false;
    }

    private int getAvailableCopies(String isbn) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement p = conn.prepareStatement("SELECT available_copies FROM library_books WHERE isbn = ?")) {
            p.setString(1, isbn);
            ResultSet rs = p.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {}
        return 0;
    }
}