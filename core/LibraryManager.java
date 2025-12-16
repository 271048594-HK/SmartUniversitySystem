package core;
import java.util.*;

public class LibraryManager {
    private List<Book> books = new ArrayList<>();
    private List<Borrowing> borrowings = new ArrayList<>();

    public void addBook(Book b) { if (b != null) books.add(b); }

    public Book findByIsbn(String isbn) {
        for (Book b : books) if (b.getIsbn().equals(isbn)) return b;
        return null;
    }

    // limits: Student 3, Faculty 10, Staff 5
    public boolean borrowBook(Person p, String isbn) {
        Book b = findByIsbn(isbn);
        if (b == null) return false;
        int limit = getLimit(p);
        int currently = countBorrowedBy(p);
        if (currently >= limit) return false;
        if (!b.borrowCopy()) return false;
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, getLoanDays(p));
        Date due = cal.getTime();
        Borrowing br = new Borrowing(p, b, now, due);
        borrowings.add(br);
        return true;
    }

    private int getLimit(Person p) {
        if (p instanceof Faculty) return 10;
        if (p instanceof Staff) return 5;
        return 3;
    }

    private int getLoanDays(Person p) {
        if (p instanceof Faculty) return 60;
        if (p instanceof Staff) return 30;
        return 14;
    }

    private int countBorrowedBy(Person p) {
        int c = 0;
        for (Borrowing br : borrowings) if (br.getBorrower() == p) c++;
        return c;
    }

    // return book and print fine if any
    public void returnBook(Person p, String isbn) {
        Iterator<Borrowing> it = borrowings.iterator();
        Date now = new Date();
        while (it.hasNext()) {
            Borrowing br = it.next();
            if (br.getBorrower() == p && br.getBook().getIsbn().equals(isbn)) {
                double fine = br.calculateFine(now);
                if (fine > 0) System.out.println("Fine: " + fine);
                br.getBook().returnCopy();
                it.remove();
                System.out.println("Returned: " + isbn);
                return;
            }
        }
        System.out.println("No borrowing record found for " + isbn + " by " + p.getName());
    }

    public void listBooks() { for (Book b : books) b.displayBook(); }
}
