import java.util.*;

public class GradeRecord {
    private Student student;
    // map courseCode -> gradePoint (0.0 - 4.0)
    private Map<String, Double> grades = new HashMap<>();

    public GradeRecord(Student student) {
        this.student = student;
    }

    public void assignGrade(Course c, double gradePoint) {
        if (c != null) grades.put(c.getCode(), gradePoint);
    }

    public Double getGradeForCourse(Course c) {
        return grades.get(c.getCode());
    }

    public Map<String, Double> getGrades() {
        return Collections.unmodifiableMap(grades);
    }

    // pass if >= 2.0
    public boolean hasPassed(Course c) {
        Double g = grades.get(c.getCode());
        return g != null && g >= 2.0;
    }
}
