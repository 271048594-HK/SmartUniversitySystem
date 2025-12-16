package modules;

import core.Student;
import core.Faculty;
import core.Staff;
import database.StudentDAO;
import database.FacultyDAO;
import database.StaffDAO;

import java.util.List;
import java.util.Scanner;

public class RegistrationMenu {
    private static final Scanner scanner = new Scanner(System.in);

    // Instantiate DAOs to access database
    private static final StudentDAO studentDao = new StudentDAO();
    private static final FacultyDAO facultyDao = new FacultyDAO();
    private static final StaffDAO staffDao = new StaffDAO();

    //  MAIN MODULE ENTRY POINT
    public static void show() {
        boolean running = true;
        while (running) {
            System.out.println("\n=================================");
            System.out.println("    REGISTRATION MANAGEMENT      ");
            System.out.println("=================================");
            System.out.println("1. Manage Students");
            System.out.println("2. Manage Faculty");
            System.out.println("3. Manage Staff");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": studentCrudMenu(); break;
                case "2": facultyCrudMenu(); break;
                case "3": staffCrudMenu(); break;
                case "4": running = false; break;
                default: System.out.println("Invalid option.");
            }
        }
    }

    //  STUDENT SUB-MENU
    private static void studentCrudMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Student Management ---");
            System.out.println("1. Add New Student");
            System.out.println("2. Search Student");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Back");
            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": addStudent(); break;
                case "2": searchStudent(); break;
                case "3": updateStudent(); break;
                case "4": deleteStudent(); break;
                case "5": running = false; break;
            }
        }
    }

    private static void addStudent() {
        System.out.print("Enter ID (e.g., S001): ");
        String id = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        // Assume simplified inputs for age/sem for brevity
        System.out.print("Enter Age: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Program: ");
        String prog = scanner.nextLine();

        // Creating core object
        Student s = new Student(id, name, age, email, "R-"+id, prog, 1);

        // Sending to Database
        if (studentDao.addStudent(s)) System.out.println(" Student added successfully!");
        else System.out.println(" Failed to add student.");
    }

    private static void searchStudent() {
        System.out.print("Enter ID or Name: ");
        String q = scanner.nextLine();
        List<Student> list = studentDao.searchStudents(q);
        if (list.isEmpty()) System.out.println("No students found.");
        else list.forEach(Student::displayInfo);
    }

    private static void updateStudent() {
        System.out.print("Enter ID to Update: ");
        String id = scanner.nextLine();
        Student s = studentDao.getStudentById(id);
        if(s == null) { System.out.println("Student not found."); return; }

        System.out.print("Enter New Name (Current: "+s.getName()+"): ");
        String name = scanner.nextLine();
        if(!name.isEmpty()) s.setName(name);

        if(studentDao.updateStudent(s)) System.out.println(" Student updated!");
        else System.out.println(" Update failed.");
    }

    private static void deleteStudent() {
        System.out.print("Enter ID to Delete: ");
        String id = scanner.nextLine();
        if(studentDao.deleteStudent(id)) System.out.println(" Student deleted!");
        else System.out.println(" Delete failed (Check constraints).");
    }

    // FACULTY SUB-MENU
    private static void facultyCrudMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Faculty Management ---");
            System.out.println("1. Add Faculty");
            System.out.println("2. Search Faculty");
            System.out.println("3. Delete Faculty");
            System.out.println("4. Back");
            String choice = scanner.nextLine();

            switch(choice) {
                case "1":
                    System.out.print("ID: "); String id = scanner.nextLine();
                    System.out.print("Name: "); String name = scanner.nextLine();
                    System.out.print("Dept Code: "); String dept = scanner.nextLine();
                    Faculty f = new Faculty(id, name, 40, name+"@uni.edu", "EMP"+id, dept, "Lecturer");
                    if(facultyDao.addFaculty(f)) System.out.println(" Added!");
                    else System.out.println(" Failed.");
                    break;
                case "2":
                    System.out.print("Search: ");
                    List<Faculty> flist = facultyDao.searchFaculty(scanner.nextLine());
                    flist.forEach(Faculty::displayInfo);
                    break;
                case "3":
                    System.out.print("ID to delete: ");
                    if(facultyDao.deleteFaculty(scanner.nextLine())) System.out.println(" Deleted!");
                    else System.out.println(" Failed.");
                    break;
                case "4": running = false; break;
            }
        }
    }

    // STAFF SUB-MENU
    private static void staffCrudMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Staff Management ---");
            System.out.println("1. Add Staff");
            System.out.println("2. Search Staff");
            System.out.println("3. Delete Staff");
            System.out.println("4. Back");
            String choice = scanner.nextLine();

            switch(choice) {
                case "1":
                    System.out.print("ID: "); String id = scanner.nextLine();
                    System.out.print("Name: "); String name = scanner.nextLine();
                    System.out.print("Role: "); String role = scanner.nextLine();
                    Staff s = new Staff(id, name, 30, name+"@uni.edu", "ST"+id, role, "Morning");
                    if(staffDao.addStaff(s)) System.out.println(" Added!");
                    else System.out.println(" Failed.");
                    break;
                case "2":
                    System.out.print("Search: ");
                    List<Staff> slist = staffDao.searchStaff(scanner.nextLine());
                    slist.forEach(Staff::displayInfo);
                    break;
                case "3":
                    System.out.print("ID to delete: ");
                    if(staffDao.deleteStaff(scanner.nextLine())) System.out.println(" Deleted!");
                    else System.out.println(" Failed.");
                    break;
                case "4": running = false; break;
            }
        }
    }
}