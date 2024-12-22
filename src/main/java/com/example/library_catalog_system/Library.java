package com.example.library_catalog_system;

import java.util.*;
import java.util.ArrayList;
import java.util.List;

public class Library {


    private String name, address;
    public static List<Book> books;
    private static List<Author> authors;
    private static List<Customer> customers;
    private static List<Borrower> borrowers;
    static List<Transaction> All_Transaction;

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

    public static void setBorrowers(List<Borrower> borrowers) {
        Library.borrowers = borrowers;
    }

    public static List<Customer> getCustomers() {
        return customers;
    }

    public static void setCustomers(List<Customer> customers) {
        Library.customers = customers;
    }

    public void setBooks(Book book) {
        boolean found = false;
        for (int i = 0; i < Library.getBooks().size(); i++) {
            if (books.get(i).getTitle().equals(book.getTitle()) && Library.getBooks().get(i).getAuthor().equals(book.getAuthor())) {
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

    public void registerCustomer(Customer newCustomer) {
        customers.add(newCustomer);
        System.out.println("Customer " + newCustomer.getCustomerId() +  " registered successfully.");
    }
    //---------------------------------------------------------------

    //Function returns a list of random objects from another list
    public static <T> List<T> getRandomElements(List<T> list, int count) {
        if (count > list.size()) {
            throw new IllegalArgumentException("Count cannot be greater than the size of the list.");
        }

        // Create a copy of the list to avoid modifying the original
        List<T> copy = new ArrayList<>(list);

        // Shuffle the copy
        Collections.shuffle(copy);

        // Return the first 'count' elements
        return copy.subList(0, count);
    }

    //Function recommend the half total books if the chosen book is not available
    public List<Book> Recommend_books() {
        int num;
        if (Library.books.size() == 1) num = 1;
        else num = Library.books.size() / 2;
        return getRandomElements(Library.books, num);
    }

    //Function That Search for Specified Book By title or Author name and recommend some books if book not found
    public List<Book> Search_book(String Word) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if ((book.getTitle().equalsIgnoreCase(Word)||book.getAuthor().getName().equalsIgnoreCase(Word)) && book.getNumOfCopies() > 0) {
                result.add(book);
            }
        }
        return result;
    }



    public void addbook(Book newbook) {
        books.add(newbook);
        System.out.println("Book added successfully.");
    }

    public void updatebooks(int bookId, float new_price, int new_numofcopies) {
        for (Book book : books) {
            if (book.getBookId() == bookId) {
                System.out.println("Book is found.");
                book.setPrice(new_price);
                book.numOfCopies = new_numofcopies;
                return;
            } else System.out.println("Book with ID " + bookId + " not found.");
        }
    }

    public void updatebooks(int bookId, int new_numofcopies) {
        for (Book book : books) {
            if (book.getBookId() == bookId) {
                System.out.println("Book is found.");
                book.numOfCopies = new_numofcopies;
                return;
            } else System.out.println("Book with ID " + bookId + " not found.");
        }
    }

    public void updatebooks(int bookId, float new_price) {
        for (Book book : books) {
            if (book.getBookId() == bookId) {
                System.out.println("Book is found.");
                book.setPrice(new_price);
                return;
            } else System.out.println("Book with ID " + bookId + " not found.");
        }
    }

    public void removebook(int bookId) {
        boolean found = false;
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getBookId() == bookId) {
                books.remove(i);
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

    //add borrower
    public void addBorrower(Borrower newborrower) {
        borrowers.add(newborrower);
        System.out.println("Borrower added! " + newborrower.getBorrowerId());
    }

    public void customerBorrowBook(Customer customer, String borrowedBook, int days) {
        for (Book book : books) {
            if (book.getTitle().equals(borrowedBook)) {
                if (book.getNumOfCopies() > 0) {
                    Transaction transaction = new Transaction(book, customer, days);
                    customer.getTransactions().add(transaction); // Add to Borrower's list of transactions
                    book.decrementCopies();
                    All_Transaction.add(transaction);
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
                System.out.println("Borrower " + borrowerID + " has been removed.");
                exist = true;
                break;
            }
        }
        if (!exist) {
            System.out.println("The Borrower you are searching for is not found.");
        }
    }
}

