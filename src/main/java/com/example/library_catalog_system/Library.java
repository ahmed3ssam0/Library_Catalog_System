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
        borrowers = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
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

    //Function Recommend 4 Books If The  chosen book is not available
    public void Recommend_books(){
        ArrayList <Book>A = new ArrayList<>();
        for(int i=2;i<6;i++){
            A.add(books.get(i));
        }
        Collections.shuffle(A);
        for (Book book:A) {
            book.displayBookInfo();
        }
    }
    //Function That Search for Specified Book By title and recommend some books if book not found
    public void Search_book(String title){
        boolean found = false;
        for(Book book : books){
            if (book.getTitle().equalsIgnoreCase(title)&&book.getNumOfCopies()>0){
                book.displayBookInfo();
                found=true;
                break;
            }
        }
        if(!found){
            System.out.println("the book "+title+" is not available now , Here are some books you might like :\n ");
            Recommend_books();
        }
    }
    // OverLoading Method for Previous Method
    public void Search_book(Author author){
        boolean found = false;
        for(Book book : books){
            if (book.getAuthor().equals(author)&&book.getNumOfCopies()>0){
                book.displayBookInfo();
                found=true;
                break;
            }
        }
        if(!found){
            System.out.println("the book you looking for is not available now , Here are some books you might like : ");
            Recommend_books();
        }
    }

    public void addbook(Book newbook) {
        books.add(newbook);
        save_books_to_file();
        System.out.println("Book added successfully .");
    }
    public void updatebooks(int bookId, float new_price, int new_numofcopies) {
        for (Book book : books) {
            if (book.getBookId() == bookId) {
                System.out.println("Book is found.");
                book.setPrice(new_price);
                book.numOfCopies = new_numofcopies;
                save_books_to_file();
                return;
            }
            else System.out.println("Book with ID " + bookId + " not found.");
        }
    }
    public void updatebooks(int bookId, int new_numofcopies) {
        for (Book book : books) {
            if (book.getBookId() == bookId) {
                System.out.println("Book is found.");
                book.numOfCopies = new_numofcopies;
                save_books_to_file();
                return;
            }
            else System.out.println("Book with ID " + bookId + " not found.");
        }
    }
    public void updatebooks(int bookId, float new_price) {
        for (Book book : books) {
            if (book.getBookId() == bookId) {
                System.out.println("Book is found.");
                book.setPrice(new_price);
                save_books_to_file();
                return;
            }
            else System.out.println("Book with ID " + bookId + " not found.");
        }
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
            books.clear();
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

    //---------------> BORROWER
    private final String file_name = "E:\\ahmed\\java\\Library_Catalog_System\\Library_Catalog_System\\files\\borrowers.txt";
    //add borrower
    public void addBorrower(Borrower newborrower){
        borrowers.add(newborrower);
        System.out.println("Borrower added! "+ newborrower.getBorrowerId());
        save_borrowers_to_file();
    }
    public void addBorrower() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Borrower Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Borrower Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter Borrower Phone Number: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Borrower Email Address: ");
        String email = scanner.nextLine();
        System.out.print("Enter Borrower Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Borrower Password: ");
        String password = scanner.nextLine();
        // Create a new Borrower and add it to the list
        Borrower borrower = new Borrower(name, address, phone, email, username, password);
        borrowers.add(borrower);
        borrowBook(borrower);
        save_borrowers_to_file();
    }
    public void borrowBook(Borrower borrower) {
        displayAvailableBooks();
        System.out.print("Enter the Book Title you want to borrow: ");
        Scanner scanner = new Scanner(System.in);
        String borrowedBook = scanner.nextLine();
        for (Book book : books) {
            if (book.getTitle().equals(borrowedBook)) {
                if (book.getNumOfCopies() > 0) {
                    Transaction transaction = new Transaction(book, borrower);
                    borrower.getTransactions().add(transaction); // Add to Borrower's list of transactions
                    save_books_to_file();
                    System.out.println("Transaction successful! " + transaction);  // Prints the transaction details
                    return;  // Exit the method once the book is successfully borrowed
                }
                else{
                    System.out.println("Sorry. This book isn't available. ");
                }
            }   }
        System.out.println("Book is unavailable or doesn't exist.");
    }
    //update borrower's contact info
    public void updateBorrower(int borrowerID){
        Scanner input = new Scanner(System.in);
        for (Borrower borrower : borrowers){
            if (borrower.getBorrowerId() == borrowerID){
                int c;
                do {
                    System.out.println("Press 1 to update the borrower's phone number");
                    System.out.println("Press 2 to update the borrower's email address");
                    System.out.println("Press 3 to update the borrower's address");
                    System.out.println("Press 4 to exit from updating process.");
                    System.out.print("Enter your choice: ");
                    c = input.nextInt();
                    input.nextLine();
                    switch (c){
                        case 1:
                            System.out.println("Enter borrower's new phone number: ");
                            String NewPhone = input.nextLine();
                            borrower.setPhone(NewPhone);
                            System.out.println("Phone Number updated successfully.");
                            break;
                        case 2:
                            System.out.println("Enter new email address: ");
                            String newEmail = input.nextLine();
                            borrower.setEmail(newEmail);
                            System.out.println("Email Addreess updated successfully.");
                            break;
                        case 3:
                            System.out.println("Enter the new Address: ");
                            String newAddress = input.nextLine();
                            borrower.setAddress(newAddress);
                            System.out.println("Address updated successfully.");
                            break;
                        case 4:
                            System.out.println("Exiting update borrower menu.");
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                }while (c != 4);
                save_borrowers_to_file();
                return;
            }
        }
        System.out.println("the Borrower you are searching for is not found.");
    }
    //remove borrower
    public void RemoveBorrower(int borrowerID){
        boolean exist = false;
        for(int i =0; i<borrowers.size(); i++){
            if(borrowers.get(i).getBorrowerId() == borrowerID)
            {
                borrowers.remove(i);
                save_borrowers_to_file();
                System.out.println("Borrower "+ borrowerID +" has been removed.");
                exist = true;
                break;
            }
        }
        if(!exist){
            System.out.println("The Borrower you are searching for is not found.");
        }
    }
    public void loadBorrowersFromFile(){
        try (BufferedReader reader = new BufferedReader(new FileReader(file_name))) {
            String line;
            while((line = reader.readLine()) != null) {
                Borrower borrower = Borrower.fromFileFormat(line);
                borrowers.add(borrower);
            }
        } catch (IOException e) {
            System.out.println("Error loading Borrowers from file: " + e.getMessage());
        }
    }
    public void save_borrowers_to_file() {
        try (FileWriter writer = new FileWriter(file_name)) {
            for (Borrower borrower : borrowers) {
                writer.write(borrower.toFileFormat() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving books to file: " + e.getMessage());
        }
    }
}

