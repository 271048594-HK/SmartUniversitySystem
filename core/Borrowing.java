package core;
import java.util.*;

public class Borrowing {
    private Person borrower; // Student/Faculty/Staff
    private Book book;
    private Date borrowDate;
    private Date dueDate;

    public Borrowing(Person borrower, Book book, Date borrowDate, Date dueDate) {
        this.borrower = borrower;
        this.book = book;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    public Person getBorrower() { return borrower; }
    public Book getBook() { return book; }
    public Date getDueDate() { return dueDate; }

    // fine = 1 unit per day late
    public double calculateFine(Date returnDate) {
        long diffMs = returnDate.getTime() - dueDate.getTime();
        long days = diffMs <= 0 ? 0 : (diffMs / (1000L*60*60*24));
        return days * 1.0;
    }
}
