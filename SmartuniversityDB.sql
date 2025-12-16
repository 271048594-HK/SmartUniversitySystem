CREATE DATABASE IF NOT EXISTS SmartUniversityDB;
USE SmartUniversityDB;

-- 1. Departments (Matches Department.java)
CREATE TABLE departments (
    dept_code VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    budget DOUBLE DEFAULT 0.0
);

-- 2. Persons: Students (Matches Student.java)
CREATE TABLE students (
    id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT,
    email VARCHAR(100),
    roll_number VARCHAR(20) UNIQUE,
    program VARCHAR(50),
    semester INT
);

-- 3. Persons: Faculty (Matches Faculty.java)
CREATE TABLE faculty (
    id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT,
    email VARCHAR(100),
    employee_id VARCHAR(20) UNIQUE,
    dept_code VARCHAR(10),
    designation VARCHAR(50),
    FOREIGN KEY (dept_code) REFERENCES departments(dept_code)
);

-- 4. Persons: Staff (Matches Staff.java)
CREATE TABLE staff (
    id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT,
    email VARCHAR(100),
    staff_id VARCHAR(20) UNIQUE,
    role VARCHAR(50),
    shift VARCHAR(50)
);

-- 5. Courses (Matches Course.java)
CREATE TABLE courses (
    code VARCHAR(10) PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    credit_hours INT
);

-- 6. Course Prerequisites (Many-to-Many relationship for Course.java)
CREATE TABLE course_prerequisites (
    course_code VARCHAR(10),
    prereq_code VARCHAR(10),
    PRIMARY KEY (course_code, prereq_code),
    FOREIGN KEY (course_code) REFERENCES courses(code),
    FOREIGN KEY (prereq_code) REFERENCES courses(code)
);

-- 7. Enrollment & Grades (Matches Enrollment.java & GradeRecord.java)
-- This table handles both "Who is in what class" and "What grade did they get"
CREATE TABLE enrollment (
    enrollment_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(20),
    course_code VARCHAR(10),
    grade_point DOUBLE DEFAULT NULL, -- Null implies currently enrolled, Value implies completed
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (course_code) REFERENCES courses(code)
);

-- 8. Library: Books (Matches Book.java)
CREATE TABLE library_books (
    isbn VARCHAR(20) PRIMARY KEY,
    title VARCHAR(150),
    author VARCHAR(100),
    total_copies INT,
    available_copies INT
);

-- 9. Library: Transactions (Matches Borrowing.java)
CREATE TABLE library_transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    borrower_id VARCHAR(20), -- Can be Student or Faculty ID
    isbn VARCHAR(20),
    borrow_date DATE,
    due_date DATE,
    return_date DATE DEFAULT NULL,
    fine_amount DOUBLE DEFAULT 0.0,
    FOREIGN KEY (isbn) REFERENCES library_books(isbn)
);

-- 10. Financials (Matches Tuition.java and Salary.java)
CREATE TABLE payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    person_id VARCHAR(20), -- Links to Student, Faculty, or Staff ID
    amount DOUBLE,
    payment_type ENUM('TUITION', 'SALARY') NOT NULL,
    payment_date DATE,
    status VARCHAR(20) DEFAULT 'PAID'
);

-- 11. Facilities (Matches FacilityManager.java logic)
CREATE TABLE facilities (
    facility_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50), -- e.g., "R101", "CS Lab"
    type ENUM('CLASSROOM', 'LAB', 'OFFICE'),
    capacity INT,
    assigned_faculty_id VARCHAR(20) DEFAULT NULL -- For Offices
);