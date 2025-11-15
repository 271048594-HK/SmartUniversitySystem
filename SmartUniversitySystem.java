import java.util.*;

public class SmartUniversitySystem {
    public static void main(String[] args) {
        // Create persons
        Student s1 = new Student("S001", "Ali Khan", 20, "ali@uni.edu", "R101", "BSCS", 3);
        Faculty f1 = new Faculty("F001", "Dr. Sara", 40, "sara@uni.edu", "FAC100", "CS", "Professor");
        Staff st1 = new Staff("ST01", "Hassan", 29, "hassan@uni.edu", "ST100", "Lab Asst", "Morning");

        // Person management
        s1.registerStudent();
        f1.hireFaculty();
        st1.addStaff();

        // Academic
        AcademicManager am = new AcademicManager();
        am.createCourse("CS101", "Intro to CS", 3);
        am.createCourse("CS102", "Data Structures", 3);
        Course cs101 = am.findCourseByCode("CS101");
        Course cs102 = am.findCourseByCode("CS102");
        cs102.addPrerequisite(cs101);
        am.listAllCourses();

        // Department
        DepartmentManager dm = new DepartmentManager();
        Department csDept = dm.createDepartment("CS", "Computer Science", 100000);
        csDept.assignFaculty(f1);
        csDept.offerCourse(cs101);
        csDept.offerCourse(cs102);
        dm.listDepartments();

        // Enrollment & grades
        Enrollment en = new Enrollment("EN1", s1);
        boolean enrolled = en.addCourse(cs102); // should fail (prereq not passed)
        System.out.println("Enrolled CS102 without prereq? " + enrolled);
        en.addCourse(cs101); // enroll CS101
        System.out.println("Enrolled courses after adding CS101:");
        for (Course c : en.viewEnrolled()) System.out.println(" - " + c.getCode());

        // Assign grade to CS101 and then enroll CS102
        s1.getGradeRecord().assignGrade(cs101, 3.0); // pass
        boolean enrolledNow = en.addCourse(cs102);
        System.out.println("Enrolled CS102 after passing CS101? " + enrolledNow);

        // Library
        LibraryManager lib = new LibraryManager();
        lib.addBook(new Book("B101", "Algorithms", "Auth A", 2));
        lib.addBook(new Book("B102", "Java Programming", "Auth B", 1));
        lib.listBooks();
        boolean borrowOk = lib.borrowBook(s1, "B101");
        System.out.println("Student borrowed B101: " + borrowOk);

        // Financial
        Tuition t = new Tuition(s1, 50); // per credit
        System.out.println("Tuition due: " + t.calculateFee());
        FinancialManager fm = new FinancialManager();
        fm.addTuition(t);
        fm.addSalary(new Salary(f1.getEmployeeId(), 3000));
        fm.processAllSalaries();

        // Facility
        FacilityManager fac = new FacilityManager();
        Classroom room = new Classroom("R101", 40);
        Lab lab = new Lab("CS Lab", cs101);
        Office office = new Office("O101");
        office.assignFaculty(f1);
        fac.addClassroom(room);
        fac.addLab(lab);
        fac.addOffice(office);
        fac.listFacilities();

        // Polymorphism demo
        System.out.println("\nPolymorphism demo (display persons):");
        Person[] people = new Person[] { s1, f1, st1 };
        for (Person p : people) p.displayInfo();
    }
}

