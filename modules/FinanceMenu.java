package modules;

import database.PaymentDAO;
import database.TuitionDAO;
import database.StudentDAO; // Reusing existing DAO to verify IDs
import database.FacultyDAO; // Reusing existing DAO to verify IDs
import core.Student;
import core.Faculty;
import java.util.List;
import java.util.Scanner;

public class FinanceMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final PaymentDAO paymentDao = new PaymentDAO();
    private static final TuitionDAO tuitionDao = new TuitionDAO();
    private static final StudentDAO studentDao = new StudentDAO();
    private static final FacultyDAO facultyDao = new FacultyDAO();

    public static void show() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Finance Module ---");
            System.out.println("1. Collect Tuition Fee (Student)");
            System.out.println("2. Process Salary (Faculty)");
            System.out.println("3. View Payment History");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": collectTuition(); break;
                case "2": processSalary(); break;
                case "3": viewHistory(); break;
                case "4": running = false; break;
                default: System.out.println("Invalid option.");
            }
        }
    }

    private static void collectTuition() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();

        //  Verify Student Exists
        Student s = studentDao.getStudentById(studentId);
        if (s == null) {
            System.out.println(" Student not found.");
            return;
        }

        //  Calculate Fee dynamically based on enrolled courses
        double fee = tuitionDao.calculateTotalFee(studentId);
        System.out.printf("Total Tuition Due (based on enrolled credits): $%.2f\n", fee);

        if (fee > 0) {
            System.out.print("Process payment of $" + fee + "? (Y/N): ");
            if (scanner.nextLine().equalsIgnoreCase("Y")) {
                if (paymentDao.recordPayment(studentId, fee, "TUITION")) {
                    System.out.println(" Tuition payment recorded successfully.");
                } else {
                    System.out.println(" Payment failed.");
                }
            }
        } else {
            System.out.println("Student has no enrolled credits or fee is 0.");
        }
    }

    private static void processSalary() {
        System.out.print("Enter Faculty ID: ");
        String facultyId = scanner.nextLine();

        //  Verify Faculty Exists
        Faculty f = facultyDao.getFacultyById(facultyId);
        if (f == null) {
            System.out.println(" Faculty not found.");
            return;
        }

        System.out.print("Enter Salary Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        if (paymentDao.recordPayment(facultyId, amount, "SALARY")) {
            System.out.println(" Salary recorded for " + f.getName());
        } else {
            System.out.println(" Transaction failed.");
        }
    }

    private static void viewHistory() {
        System.out.print("Enter Student or Faculty ID: ");
        String id = scanner.nextLine();

        List<String> history = paymentDao.getPaymentHistory(id);
        if (history.isEmpty()) {
            System.out.println("No financial records found for this ID.");
        } else {
            System.out.println("\n--- Financial History ---");
            for (String record : history) {
                System.out.println(record);
            }
        }
    }
}