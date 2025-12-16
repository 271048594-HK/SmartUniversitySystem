
package core;
public class Budget {
    private double amount;

    public Budget(double amount) { this.amount = amount; }

    public double getAmount() { return amount; }
    public void allocate(double amt) {
        if (amt <= amount) amount -= amt;
        else System.out.println("Insufficient budget to allocate " + amt);
    }
    public void addFunds(double amt) { amount += amt; }
}

