package com.example.library_catalog_system;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Borrower extends User {
    private static int nextBorrowerId = 0;
    private final int borrowerId;
    public List<Transaction> transactions;
    public Borrower(String name, String address, String phone, String email, String username, String password) {
        super(name, address, phone, email, username, password);
        this.transactions = new ArrayList<>();
        borrowerId = ++nextBorrowerId;
    }

    public int getBorrowerId() {
        return borrowerId;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
    //  View Borrowing History
    public void viewBorrowingHistory() {
        System.out.println("Borrowing History for you: " );
        for (Transaction transaction : this.getTransactions()) {
            System.out.println(transaction);
        }
    }


    public void display_info() {
        System.out.println("Display Borrower : " + getUsername() + " Information");
        System.out.println(" ================================================================");
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Address: " + getAddress());
        System.out.println("Phone Number: " + getPhone());
        System.out.println("Borrower ID: " + getBorrowerId());
    }


    public static Borrower fromFileFormat(String line) {
        String[] parts = line.split(",");  // Assuming CSV format for each borrower
        int borrowerId = Integer.parseInt(parts[0]);
        Borrower borrower = new Borrower(parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);
        String borrowDateStr = parts[7];
        String returnDateStr = parts[8];
        LocalDate borrowDate = LocalDate.parse(borrowDateStr);  // Converting to LocalDate
        LocalDate returnDate = LocalDate.parse(returnDateStr);

        Transaction transaction = new Transaction();
        transaction.setBorrowDate();
        transaction.setReturnDate(returnDate);

        borrower.getTransactions().add(transaction);

        return borrower;
    }



    public String toFileFormat() {
        String Borrower=  borrowerId + "," + getName() + "," + getAddress() + "," + getPhone() + "," + getEmail() + "," + getUsername() + "," + getPassword();

        StringBuilder transactionsData = new StringBuilder();
        for (Transaction transaction : transactions) {
            String borrowDate = transaction.getBorrowDate().toString();  // Converting LocalDate to string
            String returnDate = transaction.getReturnDate().toString();
            transactionsData.append("," + borrowDate + "," + returnDate);
        }

        return Borrower + transactionsData.toString();
    }
}
