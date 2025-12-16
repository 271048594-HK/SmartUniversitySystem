package database;

import core.Student;
import java.util.List;

public interface StudentDAOInterface {
    boolean addStudent(Student student);          // C: Create (Insert)
    Student getStudentById(String id);            // R: Retrieve (Select)
    List<Student> getAllStudents();              // R: Retrieve All
    boolean updateStudent(Student student);      // U: Update
    boolean deleteStudent(String id);            // D: Delete
    List<Student> searchStudents(String query);  // Search (Select WHERE LIKE)
}
