package modules;

import core.Student;
import database.EnrollmentDAO;
import database.StudentDAO;
import utilities.GPAService;
import java.util.Scanner;
import java.util.Map;

public class GradingMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final EnrollmentDAO enrollmentDao = new EnrollmentDAO(); // For assigning grades
    private static final StudentDAO studentDao = new StudentDAO();           // For student info
    private static final GPAService gpaService = new GPAService();           // For calculation

    public static void show() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Grading & Academic Records Module ---");
            System.out.println("1. Assign Grade to Student (UPDATE)");
            System.out.println("2. Calculate GPA (RETRIEVE)");
            System.out.println("3. Generate Transcript/View Grades (RETRIEVE)");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": assignGrade(); break;
                case "2": calculateGpa(); break;
                case "3": generateTranscript(); break;
                case "4": running = false; break;
                default: System.out.println("Invalid option.");
            }
        }
    }

    //  Assign Grade Logic
    private static void assignGrade() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();
        System.out.print("Enter Grade Point (0.0 to 4.0): ");
        double gradePoint;
        try {
            gradePoint = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(" Invalid grade format.");
            return;
        }

        if (enrollmentDao.assignGrade(studentId, courseCode, gradePoint)) {
            System.out.println(" Grade " + gradePoint + " assigned to " + studentId + " for " + courseCode + ".");
        } else {
            System.out.println(" Grade assignment failed. (Check if student is enrolled, or grade is invalid).");
        }
    }

    //  Calculate GPA Logic
    private static void calculateGpa() {
        System.out.print("Enter Student ID to calculate GPA: ");
        String studentId = scanner.nextLine();

        Student s = studentDao.getStudentById(studentId);
        if (s == null) {
            System.out.println(" Student not found.");
            return;
        }

        // Call the service layer to perform calculation
        double gpa = gpaService.calculateGPA(studentId);

        System.out.printf("\n--- GPA Report ---");
        System.out.printf("\nStudent: %s (%s)", s.getName(), studentId);
        System.out.printf("\nCalculated GPA: %.2f", gpa);
        System.out.println("\n------------------");
    }

    //  Generate Transcript Logic
    private static void generateTranscript() {
        // This method shows a detailed view of all grades and courses
        System.out.print("Enter Student ID for Transcript: ");
        String studentId = scanner.nextLine();

        Student s = studentDao.getStudentById(studentId);
        if (s == null) {
            System.out.println(" Student not found.");
            return;
        }

        // To generate a proper transcript, we would need a helper method in GradingDAO
        System.out.println("--- Full Transcript (Detail requires complex JOIN, showing summary) ---");
        calculateGpa();
    }
}