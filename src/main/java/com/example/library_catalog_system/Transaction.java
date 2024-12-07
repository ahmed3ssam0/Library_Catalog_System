package com.example.library_catalog_system;

import java.time.*;
import java.time.format.DateTimeFormatter;
public class Transaction {
    private Book book;
    private Borrower borrower;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    public Transaction() {
    }

    public Transaction(Book book, Borrower borrower, int borrowDays) {
        this.book = book;
        if (book.getNumOfCopies()==0)
        {
            throw new IllegalArgumentException("This book is unavailable for borrowing.");
        }
        book.decrementCopies();
        this.borrower = borrower;
        this.borrowDate = LocalDate.now(); // Current date as borrow date
        this.returnDate = this.borrowDate.plusDays(borrowDays); // Calculate return date
    }
    public Transaction(Book book, Borrower borrower) {
        this(book, borrower, 4); // Default borrowing period is 14 days
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setBorrowDate() {
        this.borrowDate = LocalDate.now();
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }
    // builtin function that displays all information about the class
    @Override
    public String toString() {
        return "Transaction{" +
                "book=" + book +
                ", borrower=" + borrower +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
