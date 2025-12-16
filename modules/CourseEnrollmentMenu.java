package modules;

import core.Course;
import core.Student;
import database.CourseDAO;
import database.EnrollmentDAO;
import database.StudentDAO;
import java.util.Scanner;
import java.util.List;

public class CourseEnrollmentMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CourseDAO courseDao = new CourseDAO();
    private static final EnrollmentDAO enrollmentDao = new EnrollmentDAO();
    private static final StudentDAO studentDao = new StudentDAO();

    public static void show() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Course & Enrollment Module ---");
            System.out.println("1. Course Catalog Management (CRUD)");
            System.out.println("2. Student Enrollment / Drop Course");
            System.out.println("3. Set Course Prerequisite");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": courseCrudMenu(); break;
                case "2": enrollOrDropMenu(); break;
                case "3": setPrerequisite(); break;
                case "4": running = false; break;
                default: System.out.println("Invalid option.");
            }
        }
    }

    // Course Management (CRUD)
    private static void courseCrudMenu() {
        //  (Menu switch similar to studentCrudMenu, calling addCourse, deleteCourse, etc.)
        System.out.println("\n--- Course Catalog CRUD ---");
        System.out.println("1. Add New Course");
        System.out.println("2. Delete Course");
        System.out.println("3. List All Courses");
        System.out.println("4. Back");
        System.out.print("Enter choice: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1": addNewCourse(); break;
            case "2": deleteCourse(); break;
            case "3": listAllCourses(); break;
            case "4": return;
            default: System.out.println("Invalid option.");
        }
    }

    private static void addNewCourse() {
        System.out.print("Enter Course Code (e.g., CS101): ");
        String code = scanner.nextLine();
        System.out.print("Enter Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Credit Hours: ");
        int credits = Integer.parseInt(scanner.nextLine());

        Course c = new Course(code, title, credits);
        if (courseDao.addCourse(c)) {
            System.out.println(" Course " + code + " added successfully.");
        } else {
            System.out.println(" Failed to add course (Code may exist).");
        }
    }

    private static void deleteCourse() {
        System.out.print("Enter Course Code to Delete: ");
        String code = scanner.nextLine();
        if (courseDao.deleteCourse(code)) {
            System.out.println(" Course " + code + " deleted successfully.");
        } else {
            System.out.println(" Failed to delete course (Check for existing prerequisites/enrollments).");
        }
    }

    private static void listAllCourses() {
        List<Course> courses = courseDao.getAllCourses();
        System.out.println("\n--- Course Catalog ---");
        for (Course c : courses) {
            c.displayCourseInfo(); // Uses the OOP method
        }
    }

    // Enrollment Logic
    private static void enrollOrDropMenu() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        Student s = studentDao.getStudentById(studentId);

        if (s == null) {
            System.out.println(" Student not found.");
            return;
        }

        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();
        Course c = courseDao.getCourseByCode(courseCode);

        if (c == null) {
            System.out.println(" Course not found.");
            return;
        }

        System.out.println("\n1. Enroll in Course");
        System.out.println("2. Drop Course");
        System.out.print("Choice: ");
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            // Check Prerequisite using the DAO
            if (courseCode.equals("CS102")) {
                if (!enrollmentDao.hasPassedCourse(studentId, "CS101")) {
                    System.out.println(" Enrollment failed: Prerequisite CS101 not passed.");
                    return;
                }
            }

            if (enrollmentDao.enrollStudent(studentId, courseCode)) {
                System.out.println(" " + s.getName() + " enrolled in " + courseCode + ".");
            } else {
                System.out.println(" Enrollment failed (Student already enrolled or DB error).");
            }
        } else if (choice.equals("2")) {
            if (enrollmentDao.dropCourse(studentId, courseCode)) {
                System.out.println(" " + s.getName() + " dropped " + courseCode + ".");
            } else {
                System.out.println(" Drop failed (Student not enrolled).");
            }
        }
    }

    //  Set Prerequisite Logic
    private static void setPrerequisite() {
        System.out.print("Enter Course Code that REQUIRES the prerequisite (e.g., CS102): ");
        String courseCode = scanner.nextLine();
        System.out.print("Enter Prerequisite Course Code (e.g., CS101): ");
        String prereqCode = scanner.nextLine();

        // Basic check to ensure both courses exist
        if (courseDao.getCourseByCode(courseCode) == null || courseDao.getCourseByCode(prereqCode) == null) {
            System.out.println(" Error: One or both course codes do not exist.");
            return;
        }

        if (courseDao.setPrerequisite(courseCode, prereqCode)) {
            System.out.println(" Prerequisite set: " + courseCode + " now requires " + prereqCode + ".");
        } else {
            System.out.println(" Failed to set prerequisite (may already exist).");
        }
    }
}
