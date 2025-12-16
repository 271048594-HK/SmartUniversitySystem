package core;
public class Office {
    private String officeNumber;
    private Faculty assignedFaculty; // association

    public Office(String officeNumber) {
        this.officeNumber = officeNumber;
    }

    public String getOfficeNumber() { return officeNumber; }

    public void assignFaculty(Faculty f) { this.assignedFaculty = f; }

    public Faculty getAssignedFaculty() { return assignedFaculty; }
}
