public class Tuition {
    private Student student;
    private double perCreditRate;

    public Tuition(Student student, double perCreditRate) {
        this.student = student;
        this.perCreditRate = perCreditRate;
    }

    // sum credit hours of enrolled courses * rate
    public double calculateFee() {
        int credits = 0;
        for (Course c : student.getEnrolledCourses()) credits += c.getCreditHours();
        return credits * perCreditRate;
    }
}
