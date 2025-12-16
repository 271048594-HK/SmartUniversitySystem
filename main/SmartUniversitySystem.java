package main;

import database.DatabaseConnection;
import modules.AdminMenu;
import modules.CourseEnrollmentMenu;
import modules.FinanceMenu;
import modules.GradingMenu;
import modules.LibraryMenu;
import modules.RegistrationMenu;

import java.sql.Connection;
import java.util.Scanner;

public class SmartUniversitySystem {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        //  Initialize System & Check Database
        System.out.println("Initializing Smart University System...");
        Connection conn = DatabaseConnection.getConnection();

        if (conn == null) {
            System.err.println(" CRITICAL ERROR: Database Connection Failed.");
            System.err.println("Please check XAMPP/MySQL and your DatabaseConnection.java credentials.");
            return;
        } else {
            System.out.println(" Database Connected Successfully!");
        }

        // Main Application Loop
        boolean running = true;
        while (running) {
            displayMainMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    // Module 1: Manage Students, Faculty, Staff
                    RegistrationMenu.show();
                    break;
                case "2":
                    // Module 2: Manage Courses and Enrollments
                    CourseEnrollmentMenu.show();
                    break;
                case "3":
                    // Module 3: Grading & Transcripts
                    GradingMenu.show();
                    break;
                case "4":
                    // Module 4: Library Management
                    LibraryMenu.show();
                    break;
                case "5":
                    // Module 5: Tuition & Salaries
                    FinanceMenu.show();
                    break;
                case "6":
                    // Module 6: Admin Department & Reports
                    AdminMenu.show();
                    break;
                case "7":
                    System.out.println("System shutting down. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
            }
        }

        // Cleanup
        scanner.close();
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n==============================================");
        System.out.println("           SMART UNIVERSITY SYSTEM");
        System.out.println("==============================================");
        System.out.println("1. Registration Module (Students/Faculty/Staff)");
        System.out.println("2. Course & Enrollment Module");
        System.out.println("3. Grading & Academic Records");
        System.out.println("4. Library Module (Books/Issue/Return)");
        System.out.println("5. Finance Module (Tuition/Salaries)");
        System.out.println("6. Admin Module (Departments/Reports)");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
    }
}