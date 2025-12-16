package database;

import core.Course;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    //  CREATE (Add Course)
    public boolean addCourse(Course c) {
        String sql = "INSERT INTO courses (code, title, credit_hours) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, c.getCode());
            pstmt.setString(2, c.getTitle());
            pstmt.setInt(3, c.getCreditHours());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error adding course: " + e.getMessage());
            return false;
        }
    }

    //  CREATE (Set Prerequisite)
    public boolean setPrerequisite(String courseCode, String prereqCode) {
        String sql = "INSERT INTO course_prerequisites (course_code, prereq_code) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, courseCode);
            pstmt.setString(2, prereqCode);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error setting prerequisite: " + e.getMessage());
            return false;
        }
    }

    // RETRIEVE (Get Course by Code)
    public Course getCourseByCode(String code) {
        String sql = "SELECT * FROM courses WHERE code = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, code);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Course(
                        rs.getString("code"),
                        rs.getString("title"),
                        rs.getInt("credit_hours")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving course: " + e.getMessage());
        }
        return null;
    }

    // RETRIEVE (List All Courses)
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses ORDER BY code";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                courses.add(new Course(
                        rs.getString("code"),
                        rs.getString("title"),
                        rs.getInt("credit_hours")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error listing courses: " + e.getMessage());
        }
        return courses;
    }

    //  DELETE (Delete Course)
    public boolean deleteCourse(String code) {
        String sql = "DELETE FROM courses WHERE code = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, code);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting course: " + e.getMessage());
            return false;
        }
    }
}