public class Staff extends Person {
    private String staffId;
    private String role;
    private String shift;

    public Staff(String id, String name, int age, String email, String staffId, String role, String shift) {
        super(id, name, age, email);
        this.staffId = staffId;
        this.role = role;
        this.shift = shift;
    }

    public String getStaffId() { return staffId; }
    public String getRole() { return role; }
    public String getShift() { return shift; }

    public void addStaff() {
        System.out.println("Staff added: " + name + " (" + staffId + ")");
    }

    @Override
    public void displayInfo() {
        System.out.println("\n--- Staff ---");
        super.displayInfo();
        System.out.println("StaffID: " + staffId + " | Role: " + role + " | Shift: " + shift);
    }
}
