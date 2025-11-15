import java.util.*;

public class AcademicManager {
    private List<Course> courseCatalog = new ArrayList<>();

    public void createCourse(String code, String title, int credits) {
        Course c = new Course(code, title, credits);
        if (!courseCatalog.contains(c)) courseCatalog.add(c);
    }

    public List<Course> getCourseCatalog() {
        return Collections.unmodifiableList(courseCatalog);
    }

    public Course findCourseByCode(String code) {
        for (Course c : courseCatalog) if (c.getCode().equals(code)) return c;
        return null;
    }

    public void listAllCourses() {
        System.out.println("\n--- Course Catalog ---");
        for (Course c : courseCatalog) c.displayCourseInfo();
    }
}
