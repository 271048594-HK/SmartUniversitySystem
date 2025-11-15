public class Faculty extends Person {
    private String employeeId;
    private String departmentCode;
    private String designation;

    public Faculty(String id, String name, int age, String email, String employeeId, String departmentCode, String designation) {
        super(id, name, age, email);
        this.employeeId = employeeId;
        this.departmentCode = departmentCode;
        this.designation = designation;
    }

    public String getEmployeeId() { return employeeId; }
    public String getDepartmentCode() { return departmentCode; }
    public void setDepartmentCode(String departmentCode) { this.departmentCode = departmentCode; }
    public String getDesignation() { return designation; }

    public void hireFaculty() {
        System.out.println("Faculty hired: " + name + " (" + employeeId + ")");
    }

    @Override
    public void displayInfo() {
        System.out.println("\n--- Faculty ---");
        super.displayInfo();
        System.out.println("EmpID: " + employeeId + " | Dept: " + departmentCode + " | " + designation);
    }
}
