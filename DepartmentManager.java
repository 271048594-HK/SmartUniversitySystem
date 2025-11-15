import java.util.*;

public class DepartmentManager {
    private List<Department> departments = new ArrayList<>();

    public Department createDepartment(String code, String name, double budget) {
        Department d = new Department(code, name, budget);
        departments.add(d);
        return d;
    }

    public Department findByCode(String code) {
        for (Department d : departments) if (d.getCode().equals(code)) return d;
        return null;
    }

    public void listDepartments() {
        for (Department d : departments) d.displayInfo();
    }
}
