public class Scholarship {
    private Student student;
    private double amount;

    public Scholarship(Student student, double amount) {
        this.student = student;
        this.amount = amount;
    }

    public void apply() {
        System.out.println("Scholarship of " + amount + " applied to " + student.getName());
    }
}
