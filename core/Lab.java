package core;
public class Lab {
    private String labName;
    private Course course; // association

    public Lab(String labName, Course course) {
        this.labName = labName;
        this.course = course;
    }

    public void displayLabInfo() {
        System.out.println(labName + " - Course: " + (course != null ? course.getCode() : "None"));
    }
}
