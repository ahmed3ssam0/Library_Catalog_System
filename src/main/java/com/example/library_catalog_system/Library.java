package com.example.library_catalog_system;

import java.util.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.util.List;
import java.time.LocalDate;

public class Library {

    private final String FILE_NAME = "E:\\ahmed\\java\\Library_Catalog_System\\Library_Catalog_System\\files\\";


    private String name, address;
    public static List<Book> books;
    private static List<Author> authors;
    private static List<Customer> customers;
    private static List<Borrower> borrowers;
    private static List<Transaction> All_Transaction;

    public Library(String name, String address) {
        this.name = name;
        this.address = address;
        borrowers = new ArrayList<>();
        customers = new ArrayList<>();
        books = new ArrayList<>();
        All_Transaction = new ArrayList<>();
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
    public void addReview(String title, String review, int rating) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                book.addReview(review, rating);
                System.out.println("Review added successfully!");
                return;
            }
        }
        System.out.println("Book not found.");
    }

//    public void displayBookReviews(String title) {
//        for (Book book : books) {
//            if (book.getTitle().equalsIgnoreCase(title)) {
//                System.out.println("Reviews for " + book.getTitle() + ":");
//                if(book.getReviews().isEmpty()){
//                    System.out.println("No Reviews For this Book");
//                    return;
//                }
//
//                System.out.println("Average Rating: " + book.getAverageRating());
//                return;
//            }
//        }
//        System.out.println("Book not found.");
//    }

    //-------------------- Customers -----------------------

//    public void loadCustomersFromFile() {
////        Customer.loadNextCustomerId(CUSTOMERS_ID_FILE);
//        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME + "customers.txt"))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                customers.add(Customer.fromFileFormat(line));
//            }
//        } catch (IOException e) {
//            System.out.println("Error loading customers: " + e.getMessage());
//        }
//    }

//    public void save_customers_to_file() {
////        Customer.saveNextCustomerId(CUSTOMERS_ID_FILE);
//        try (FileWriter writer = new FileWriter(FILE_NAME + "customers.txt")) {
//            for (Customer customer : customers) {
//                writer.write(customer.toFileFormat() + "\n");
//            }
//        } catch (IOException e) {
//            System.out.println("Error saving customers: " + e.getMessage());
//        }
//    }

    public void registerCustomer(Customer newCustomer) {
        customers.add(newCustomer);
        save_customers_to_file();
        System.out.println("Customer " + newCustomer.getCustomerId() +  " registered successfully.");
    }

    public void save_customers_to_file() {
        try (FileWriter writer = new FileWriter(FILE_NAME + "customers.txt")) {
            for (Customer customer : customers) {
                writer.write(customer.toFileFormat() + "\n");
                System.out.println("Customer " + customer.toFileFormat() +  " registered successfully.");
            }
        } catch (IOException e) {
            System.out.println("Error saving Customer to file: " + e.getMessage());
        }
    }

    public void loadCustomersFromFile() {
        File writer = new File(FILE_NAME + "customers.txt");
        try (Scanner scan = new Scanner(writer)) {
            while (scan.hasNextLine()) {
                Customer customer = Customer.fromFileFormat(scan.nextLine());
                customers.add(customer);
            }
            for (Customer customer : customers) {
                System.out.println("Customer " + customer.getCustomerId() +  " loaded successfully.");
                System.out.println(customer.toFileFormat() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error loading Customers from file: " + e.getMessage());
        }
    }
    //---------------------------------------------------------------

    //System should record book returns and update availability
    public void recordReturn(Borrower borrower) {
        for (Transaction transaction : borrower.getTransactions()) {
            if (transaction.getReturnDate().isEqual(LocalDate.now())) {
                // ask admin if book returned
                boolean is_returned = true;
                if (is_returned) {
                    transaction.getBook().incrementCopies();
                    System.out.println("Return recorded: " + transaction.getBook().getTitle() + " returned by " + transaction.getBorrower().getName());
                } else {
                    System.out.println("You must pay " + transaction.Calculate_Fines() + " Fine For late returns");
                }
            }
        }
    }

    //Function Recommend 3 Books If The  chosen book is not available
    public List<Book> Recommend_books() {
        ArrayList<Book> A = new ArrayList<>();
        for (int i = 2; i < 5; i++) {
            A.add(books.get(i));
        }
        Collections.shuffle(A);
        return A;
    }

    //Function That Search for Specified Book By title or Author name and recommend some books if book not found
    public List<Book> Search_book(String Word) {
        boolean found = false;
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if ((book.getTitle().equalsIgnoreCase(Word)||book.getAuthor().getName().equalsIgnoreCase(Word)) && book.getNumOfCopies() > 0) {
                result.add(book);
                found = true;

            }
        }
        if (!found) {
            System.out.println("the book You Looking for is not available now , Here are some books you might like :\n ");
            return Recommend_books();
        }
        else
            return result;
    }



    public void addbook(Book newbook) {
        books.add(newbook);
        save_books_to_file();
        System.out.println("Book added successfully.");
    }

    public void updatebooks(int bookId, float new_price, int new_numofcopies) {
        for (Book book : books) {
            if (book.getBookId() == bookId) {
                System.out.println("Book is found.");
                book.setPrice(new_price);
                book.numOfCopies = new_numofcopies;
                save_books_to_file();
                return;
            } else System.out.println("Book with ID " + bookId + " not found.");
        }
    }

    public void updatebooks(int bookId, int new_numofcopies) {
        for (Book book : books) {
            if (book.getBookId() == bookId) {
                System.out.println("Book is found.");
                book.numOfCopies = new_numofcopies;
                save_books_to_file();
                return;
            } else System.out.println("Book with ID " + bookId + " not found.");
        }
    }

    public void updatebooks(int bookId, float new_price) {
        for (Book book : books) {
            if (book.getBookId() == bookId) {
                System.out.println("Book is found.");
                book.setPrice(new_price);
                save_books_to_file();
                return;
            } else System.out.println("Book with ID " + bookId + " not found.");
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
                System.out.println("ID: " + book.getBookId() + ", Title: " + book.getTitle() + ", Author: " + book.getAuthor().getName() + " " + book.getAuthor().getSurname() + ", Price: " + book.getPrice() + " , numofcopies:" + book.getNumOfCopies());
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

    public void save_books_to_file() {
        try (FileWriter writer = new FileWriter(FILE_NAME + "books.txt")) {
            for (Book book : books) {
                writer.write(book.toFileFormat() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving books to file: " + e.getMessage());
        }
    }

    public void loadBooksFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME + "books.txt"))) {
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

    // Record All Transaction
    public void recordTransactionsToFile(int type) {
        if (All_Transaction.isEmpty()) {
            System.out.println("No transactions recorded.");
        } else {
            System.out.println("Library Transactions:");

            try(PrintWriter writer=new PrintWriter("E:\\ahmed\\java\\Library_Catalog_System\\Library_Catalog_System\\files\\All_Transactions.txt")){
                for (Transaction transaction : All_Transaction) {
                    if (type == 1)
                        writer.println("Transaction{" +
                                "borrower :" + transaction.getBorrower().getName() +
                                ", book : " + transaction.getBook().getTitle() +
                                ", borrowDate=" + transaction.getBorrowDate() +
                                ", returnDate=" + transaction.getReturnDate() +
                                '}');
                    else
                        writer.println("Transaction{" +
                                "Customer :" + transaction.getCustomer().getName() +
                                ", book : " + transaction.getBook().getTitle() +
                                ", borrowDate=" + transaction.getBorrowDate() +
                                ", returnDate=" + transaction.getReturnDate() +
                                '}');
                }
            }
            catch (IOException e){
                System.out.println("Error writing transactions to file: " + e.getMessage());
            }

        }
    }

    //Record all transactions for each borrower in file named with his ID
    private void writeTransactionToFile(Borrower borrower) {
        try (PrintWriter writer = new PrintWriter(FILE_NAME + "Borrowers\\"+borrower.getBorrowerId() + "_history.txt")) {
            for(Transaction transaction: borrower.getTransactions()) {
                writer.println("Transaction{" +
                        "book=" + transaction.getBook().getTitle() +
                        ", borrowDate=" + transaction.getBorrowDate() +
                        ", returnDate=" + transaction.getReturnDate() +
                        '}');
            }
        } catch (IOException e) {
            System.out.println("Error writing transaction to file: " + e.getMessage());
        }
    }
    private void writeTransactionToFile(Customer customer) {
        try (PrintWriter writer = new PrintWriter(FILE_NAME + "CustomersBorrowings\\" + customer.getCustomerId() + "_history.txt")) {
            for(Transaction transaction: customer.getTransactions()) {
                writer.println("Transaction{" +
                        "book=" + transaction.getBook().getTitle() +
                        ", borrowDate=" + transaction.getBorrowDate() +
                        ", returnDate=" + transaction.getReturnDate() +
                        '}');
            }
        } catch (IOException e) {
            System.out.println("Error writing transaction to file: " + e.getMessage());
        }
    }
    //---------------> BORROWER
    private final String file_name = FILE_NAME + "borrowers.txt";

    //add borrower
    public void addBorrower(Borrower newborrower) {
        borrowers.add(newborrower);
        System.out.println("Borrower added! " + newborrower.getBorrowerId());
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
        // Create a new Borrower and add it to the list
        Borrower borrower = new Borrower(name, address, phone, email);
        borrowers.add(borrower);
//        borrowBook(borrower);
        save_borrowers_to_file();
    }

    public void customerBorrowBook(Customer customer, String borrowedBook, int days) {
        for (Book book : books) {
            if (book.getTitle().equals(borrowedBook)) {
                if (book.getNumOfCopies() > 0) {
                    Transaction transaction = new Transaction(book, customer, days);
                    customer.getTransactions().add(transaction); // Add to Borrower's list of transactions
                    book.decrementCopies();
                    All_Transaction.add(transaction);
                    recordTransactionsToFile(2);
                    writeTransactionToFile(customer);// Add to Borrower's list of transactions
                    save_books_to_file();
                    System.out.println("Transaction successful! " + transaction);  // Prints the transaction details
                    return;  // Exit the method once the book is successfully borrowed
                } else {
                    System.out.println("Sorry. This book isn't available. ");
                }
            }
        }
        System.out.println("Book is unavailable or doesn't exist.");
    }

    public void borrowBook(Borrower borrower, String borrowedBook, int days) {
        displayAvailableBooks();
        for (Book book : books) {
            if (book.getTitle().equals(borrowedBook)) {
                if (book.getNumOfCopies() > 0) {
                    Transaction transaction = new Transaction(book, borrower, days);
                    borrower.getTransactions().add(transaction); // Add to Borrower's list of transactions
                    book.decrementCopies();
                    All_Transaction.add(transaction);
                    recordTransactionsToFile(1);
                    writeTransactionToFile(borrower);// Add to Borrower's list of transactions
                    save_books_to_file();
                    System.out.println("Transaction successful! " + transaction);  // Prints the transaction details
                    return;  // Exit the method once the book is successfully borrowed
                } else {
                    System.out.println("Sorry. This book isn't available. ");
                }
            }
        }
        System.out.println("Book is unavailable or doesn't exist.");
    }

    //update borrower's contact info
    public void updateBorrower(int borrowerID, String newEmail, String newAddress, String newPhone) {
        for (Borrower borrower : borrowers) {
            if (borrower.getBorrowerId() == borrowerID) {

                if (newEmail != null && !newEmail.isEmpty()) {
                    borrower.setEmail(newEmail);
                }
                if (newAddress != null && !newAddress.isEmpty()) {
                    borrower.setAddress(newAddress);
                }
                if (newPhone != null && !newPhone.isEmpty()) {
                    borrower.setPhone(newPhone);
                }

                System.out.println("Data updated successfully.");
                save_borrowers_to_file();
                return;
            } else System.out.println("Borrower with ID:" + borrowerID + " isn't available.");
        }
    }

    //remove borrower
    public void RemoveBorrower(int borrowerID) {
        boolean exist = false;
        for (int i = 0; i < borrowers.size(); i++) {
            if (borrowers.get(i).getBorrowerId() == borrowerID) {
                borrowers.remove(i);
                save_borrowers_to_file();
                System.out.println("Borrower " + borrowerID + " has been removed.");
                exist = true;
                break;
            }
        }
        if (!exist) {
            System.out.println("The Borrower you are searching for is not found.");
        }
    }

    public void loadBorrowersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file_name))) {
            String line;
            while ((line = reader.readLine()) != null) {
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

