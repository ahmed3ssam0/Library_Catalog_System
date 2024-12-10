package com.example.library_catalog_system;

import java.util.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.util.List;
import java.time.LocalDate;
import java.util.*;

import java.time.format.DateTimeFormatter;
public class Library {

    private final String FILE_NAME = "E:\\ahmed\\java\\Library_Catalog_System\\Library_Catalog_System\\files\\books.txt";


    private String name, address;
    private static int numOfSections, numOfBooks;
    public static ArrayList<Book> books= new ArrayList<>();
    private static List<Author> authors;
    private static List<Customer> customers;
    private static List<Borrower>borrowers;
    public Library(String name, String address) {
        this.name = name;
        this.address = address;

    }
    public Library(){
        loadBooksFromFile();

    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public static List<Book> getBooks() {
        return books;
    }

    public static List<Borrower> getBorrowers() {
        return borrowers;
    }

    public static List<Customer> getCustomers() {
        return customers;
    }

    public void setBooks(Book book) {
        boolean found = false;
        for (int i = 0; i < Library.getBooks().size(); i++) {
            if (Library.getBooks().get(i).getTitle().equals(book.getTitle()) && Library.getBooks().get(i).getAuthor().equals(book.getAuthor())) {
                found = true;
                Library.getBooks().get(i).incrementCopies();
            }
        }
        if (!found) {
            books.add(book);
        }
    }
    public static List<Author> getAuthors() {
        return authors;
    }
    public void setAuthors(Author author) {
        boolean found = false;
        for (int i = 0; i < Library.getAuthors().size(); i++) {
            if (Library.getAuthors().get(i).getPhone().equals(author.getPhone()) && Library.getAuthors().get(i).getEmail().equals(author.getEmail())) {
                found = true;
                break;
            }
        }
        if (!found) {
            authors.add(author);
        }
    }


    public void sendNotifications() {
        LocalDate today = LocalDate.now();
        System.out.println("Sending Notifications:");
        for (Borrower borrower : borrowers) {
            for (Transaction transaction : borrower.getTransactions()) {
                // Reminder for items due today
                if (transaction.getReturnDate().isEqual(today)) {
                    System.out.println("Reminder: Book '" + transaction.getBook().getTitle() +
                            "' is due today ");
                }
                // Notification for overdue items
                else if (transaction.getReturnDate().isBefore(today)) {
                    System.out.println("Overdue: Book '" + transaction.getBook().getTitle() +
                            "' is overdue ");
                }
            }
        }
    }


    public void addbook(Book newbook) {
        books.add(newbook);
        save_books_to_file();
        System.out.println("Book added successfully .");
    }
    public void updatebooks(int bookId) {
        Scanner input = new Scanner(System.in);

        for (Book book : books) {
            if (book.getBookId() == bookId) {
                System.out.println("Book is found.");
                int mychoice;
                do {
                    System.out.println("Press 1 to update the price of the book.");
                    System.out.println("Press 2 to update the number of copies of the book.");
                    System.out.println("Press 3 to exit from updating process.");
                    System.out.print("Enter your choice: ");
                    mychoice = input.nextInt();

                    switch (mychoice) {
                        case 1:
                            System.out.print("Enter the new price: ");
                            int new_price = input.nextInt();
                            book.setPrice(new_price);
                            System.out.println("Price updated successfully.");
                            break;

                        case 2:
                            System.out.print("Enter the new number of copies: ");
                            int new_numofcopies = input.nextInt();
                            book.numOfCopies = new_numofcopies;
                            System.out.println("Number of copies updated successfully.");
                            break;

                        case 3:
                            System.out.println("Exiting update menu.");
                            break;

                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                } while (mychoice != 3);
                save_books_to_file();
                return;
            }
        }

        System.out.println("Book with ID " + bookId + " not found.");
    }
    public void removebook(int bookId) {
        boolean found = false;
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getBookId() == bookId) {
                books.remove(i);
                save_books_to_file();
                System.out.println("Book with ID " + bookId + " removed successfully.");
                found = true;
                break;

            }
        }

        if (!found) {
            System.out.println("Book with ID " + bookId + " not found in the system.");
        }
    }
    public void displayAvailableBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
        } else {
            System.out.println("List of Available Books:");
            for (Book book : books) {
                System.out.println("ID: " + book.getBookId() + ", Title: " + book.getTitle() + ", Author: " + book.getAuthor().getName() + " " + book.getAuthor().getSurname() + ", Price: " + book.getPrice() +" , numofcopies:"+ book.getNumOfCopies());
            }
        }
    }
    public void displayTotalBooks() {
        int totalBooks = 0;

        for (Book book : books) {
            totalBooks += book.getNumOfCopies();
        }
        System.out.println("Number of distinct book titles: " + books.size());
        System.out.println("Total number of books in the library: " + totalBooks);
    }
    public void save_books_to_file(){

        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            for (Book book : books) {
                writer.write(book.toFileFormat() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving books to file: " + e.getMessage());
        }
    }
    public void loadBooksFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Book book = Book.fromFileFormat(line);
                books.add(book);
            }
        } catch (IOException e) {
            System.out.println("Error loading books from file: " + e.getMessage());
        }
    }
    public void displayLibraryInfo() {
        System.out.println("\nLibrary Information");
        System.out.println("================================================================");
        System.out.println("Library Name: " + getName());
        System.out.println("Library Address: " + getAddress());
        int allCopies = 0;
        for (Book book : Author.getBooks()) {
            allCopies += book.getNumOfCopies();
        }
        System.out.println("The Library has " + Library.getBooks().size() + " books and " + allCopies + " copies");

    }
}

