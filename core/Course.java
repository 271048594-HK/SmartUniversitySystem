package core;
import java.util.*;

public class Course {
    private String code;
    private String title;
    private int creditHours;
    private List<Course> prerequisites = new ArrayList<>();

    public Course(String code, String title, int creditHours) {
        this.code = code;
        this.title = title;
        this.creditHours = creditHours;
    }

    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCreditHours() { return creditHours; }

    public void addPrerequisite(Course c) {
        if (c != null && !prerequisites.contains(c)) prerequisites.add(c);
    }

    public List<Course> getPrerequisites() {
        return Collections.unmodifiableList(prerequisites);
    }

    public void displayCourseInfo() {
        System.out.println(code + " - " + title + " (" + creditHours + " cr)");
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Course)) return false;
        Course other = (Course) obj;
        return code.equals(other.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
