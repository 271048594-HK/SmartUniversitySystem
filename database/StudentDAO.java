package database;

import core.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // CREATE
    public boolean addStudent(Student s) {
        String sql = "INSERT INTO students (id, name, age, email, roll_number, program, semester) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, s.getId());
            pstmt.setString(2, s.getName());
            pstmt.setInt(3, s.getAge());
            pstmt.setString(4, s.getEmail());
            pstmt.setString(5, s.getRollNumber());
            pstmt.setString(6, s.getProgram());
            pstmt.setInt(7, s.getSemester());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
            return false;
        }
    }

    // RETRIEVE (Get by ID)
    public Student getStudentById(String id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Student(
                        rs.getString("id"), rs.getString("name"), rs.getInt("age"),
                        rs.getString("email"), rs.getString("roll_number"),
                        rs.getString("program"), rs.getInt("semester")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching student: " + e.getMessage());
        }
        return null;
    }

    // SEARCH (By Name or ID)
    public List<Student> searchStudents(String query) {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE id LIKE ? OR name LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + query + "%");
            pstmt.setString(2, "%" + query + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(new Student(
                        rs.getString("id"), rs.getString("name"), rs.getInt("age"),
                        rs.getString("email"), rs.getString("roll_number"),
                        rs.getString("program"), rs.getInt("semester")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Search error: " + e.getMessage());
        }
        return list;
    }

    // UPDATE
    public boolean updateStudent(Student s) {
        String sql = "UPDATE students SET name=?, age=?, email=?, program=?, semester=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, s.getName());
            pstmt.setInt(2, s.getAge());
            pstmt.setString(3, s.getEmail());
            pstmt.setString(4, s.getProgram());
            pstmt.setInt(5, s.getSemester());
            pstmt.setString(6, s.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Update error: " + e.getMessage());
            return false;
        }
    }

    //  DELETE
    public boolean deleteStudent(String id) {
        String sql = "DELETE FROM students WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Delete error (Check constraints): " + e.getMessage());
            return false;
        }
    }
}