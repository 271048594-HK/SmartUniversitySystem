import java.util.*;

public class Enrollment {
    private String id;
    private Student student;
    private List<Course> enrolled = new ArrayList<>();

    public Enrollment(String id, Student student) {
        this.id = id;
        this.student = student;
    }

    // Add course with prerequisite checking using student's GradeRecord
    public boolean addCourse(Course c) {
        if (c == null) return false;
        // check prerequisites
        for (Course pre : c.getPrerequisites()) {
            if (!student.getGradeRecord().hasPassed(pre)) return false;
        }
        boolean success = student.enrollCourse(c);
        if (success) enrolled.add(c);
        return success;
    }

    public boolean dropCourse(Course c) {
        boolean removedFromStudent = student.dropCourse(c);
        if (removedFromStudent) return enrolled.remove(c);
        return false;
    }

    public List<Course> viewEnrolled() {
        return Collections.unmodifiableList(enrolled);
    }
}
