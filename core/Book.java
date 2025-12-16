
package core;
public class Book {
    private String isbn;
    private String title;
    private String author;
    private int totalCopies;
    private int available;

    public Book(String isbn, String title, String author, int totalCopies) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.totalCopies = totalCopies;
        this.available = totalCopies;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }

    public synchronized boolean borrowCopy() {
        if (available <= 0) return false;
        available--;
        return true;
    }

    public synchronized void returnCopy() {
        if (available < totalCopies) available++;
    }

    public void displayBook() {
        System.out.println(title + " by " + author + " | available: " + available);
    }
}
