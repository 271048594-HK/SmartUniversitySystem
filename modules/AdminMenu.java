package modules;

import database.DepartmentDAO;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DepartmentDAO deptDao = new DepartmentDAO();

    public static void show() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Admin Module ---");
            System.out.println("1. Manage Departments (Add/List/Delete)");
            System.out.println("2. Generate Full System Report");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": manageDepartments(); break;
                case "2": generateSystemReport(); break;
                case "3": running = false; break;
                default: System.out.println("Invalid option.");
            }
        }
    }

    private static void manageDepartments() {
        System.out.println("\n-- Department Management --");
        System.out.println("1. Add New Department");
        System.out.println("2. List All Departments");
        System.out.println("3. Delete Department");
        System.out.print("Choice: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                System.out.print("Enter Dept Code (e.g., CS): ");
                String code = scanner.nextLine();
                System.out.print("Enter Dept Name (e.g., Computer Science): ");
                String name = scanner.nextLine();
                System.out.print("Enter Initial Budget: ");
                double budget = Double.parseDouble(scanner.nextLine());

                if (deptDao.addDepartment(code, name, budget)) {
                    System.out.println(" Department added.");
                } else {
                    System.out.println(" Failed to add department.");
                }
                break;
            case "2":
                List<String> depts = deptDao.getAllDepartments();
                if (depts.isEmpty()) System.out.println("No departments found.");
                else depts.forEach(System.out::println);
                break;
            case "3":
                System.out.print("Enter Dept Code to DELETE: ");
                String delCode = scanner.nextLine();
                if (deptDao.deleteDepartment(delCode)) {
                    System.out.println(" Department deleted.");
                } else {
                    System.out.println(" Delete failed (Check if faculty exist in this dept).");
                }
                break;
        }
    }

    private static void generateSystemReport() {
        System.out.println("\n=================================");
        System.out.println("    UNIVERSITY SYSTEM REPORT     ");
        System.out.println("=================================");
        // Calls the aggregated stats method we wrote in the DAO
        System.out.println(deptDao.getSystemStats());
        System.out.println("=================================");
    }
}
