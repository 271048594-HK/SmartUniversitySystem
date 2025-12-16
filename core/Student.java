package core;
import java.util.*;

public class

Student extends Person {
    private String rollNumber;
    private String program; // e.g., BSCS, BBA
    private int semester;
    private List<Course> enrolledCourses = new ArrayList<>();
    private GradeRecord gradeRecord;

    public Student(String id, String name, int age, String email, String rollNumber, String program, int semester) {
        super(id, name, age, email);
        this.rollNumber = rollNumber;
        this.program = program;
        this.semester = semester;
        this.gradeRecord = new GradeRecord(this);
    }

    public String getRollNumber() { return rollNumber; }
    public String getProgram() { return program; }
    public int getSemester() { return semester; }

    // Return unmodifiable list to preserve encapsulation
    public List<Course> getEnrolledCourses() { return Collections.unmodifiableList(enrolledCourses); }
    public GradeRecord getGradeRecord() { return gradeRecord; }

    public void registerStudent() {
        System.out.println("Student registered: " + name + " (" + rollNumber + ")");
    }

    // Enrollment actions used by Enrollment class
    public boolean enrollCourse(Course course) {
        if (course == null) return false;
        if (enrolledCourses.contains(course)) return false;
        enrolledCourses.add(course);
        return true;
    }

    public boolean dropCourse(Course course) {
        return enrolledCourses.remove(course);
    }

    @Override
    public void displayInfo() {
        System.out.println("\n--- Student ---");
        super.displayInfo();
        System.out.println("Roll#: " + rollNumber + " | Program: " + program + " | Sem: " + semester);
    }
}
