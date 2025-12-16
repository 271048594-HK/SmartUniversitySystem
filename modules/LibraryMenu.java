package modules;

import database.BookDAO;
import java.util.Scanner;

public class LibraryMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final BookDAO bookDao = new BookDAO();

    public static void show() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Library Module ---");
            System.out.println("1. Add Book");
            System.out.println("2. Issue Book");
            System.out.println("3. Return Book");
            System.out.println("4. Back");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("ISBN: "); String isbn = scanner.nextLine();
                    System.out.print("Title: "); String title = scanner.nextLine();
                    System.out.print("Author: "); String auth = scanner.nextLine();
                    if(bookDao.addBook(isbn, title, auth, 5)) System.out.println(" Book Added");
                    break;
                case "2":
                    System.out.print("ISBN: "); String i = scanner.nextLine();
                    System.out.print("Student ID: "); String s = scanner.nextLine();
                    if(bookDao.issueBook(i, s)) System.out.println(" Book Issued");
                    else System.out.println(" Failed (No copies or invalid ID)");
                    break;
                case "3":
                    System.out.print("ISBN: "); String i2 = scanner.nextLine();
                    System.out.print("Student ID: "); String s2 = scanner.nextLine();
                    if(bookDao.returnBook(i2, s2)) System.out.println(" Book Returned");
                    else System.out.println(" Failed");
                    break;
                case "4": running = false; break;
            }
        }
    }
}