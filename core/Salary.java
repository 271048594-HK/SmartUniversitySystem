package core;
public class Salary {
    private String payeeId; // staff or faculty id
    private double amount;
    private boolean paid = false;

    public Salary(String payeeId, double amount) {
        this.payeeId = payeeId;
        this.amount = amount;
    }

    public void processPayment() {
        if (!paid) {
            paid = true;
            System.out.println("Salary paid to " + payeeId + " amount: " + amount);
        } else {
            System.out.println("Salary already paid to " + payeeId);
        }
    }
}
