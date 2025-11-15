import java.util.*;

public class FinancialManager {
    private List<Tuition> tuitions = new ArrayList<>();
    private List<Salary> salaries = new ArrayList<>();
    private List<Scholarship> scholarships = new ArrayList<>();

    public void addTuition(Tuition t) { if (t != null) tuitions.add(t); }
    public void addSalary(Salary s) { if (s != null) salaries.add(s); }
    public void addScholarship(Scholarship s) { if (s != null) scholarships.add(s); }

    public void processAllSalaries() {
        for (Salary s : salaries) s.processPayment();
    }

    public void listTuitions() {
        for (Tuition t : tuitions) System.out.println("Tuition for student: " + t.calculateFee());
    }
}
