
package core;
import java.util.*;

public class Department {
    private String code;
    private String name;
    private Budget budget; // composition
    private List<Faculty> faculties = new ArrayList<>();
    private List<Course> offerings = new ArrayList<>();

    public Department(String code, String name, double initialBudget) {
        this.code = code;
        this.name = name;
        this.budget = new Budget(initialBudget);
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public Budget getBudget() { return budget; }

    public void assignFaculty(Faculty f) {
        if (f != null && !faculties.contains(f)) {
            faculties.add(f);
            f.setDepartmentCode(code);
        }
    }

    public void offerCourse(Course c) {
        if (c != null && !offerings.contains(c)) offerings.add(c);
    }

    public List<Course> getOfferings() {
        return Collections.unmodifiableList(offerings);
    }

    public void displayInfo() {
        System.out.println("\n=== Department " + name + " (" + code + ") ===");
        System.out.println("Budget: " + budget.getAmount());
        System.out.println("Faculties:");
        for (Faculty f : faculties) System.out.println(" - " + f.getName() + " (" + f.getEmployeeId() + ")");
        System.out.println("Courses Offered:");
        for (Course c : offerings) System.out.println(" - " + c.getCode() + " " + c.getTitle());
    }
}
