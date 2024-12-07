package com.example.library_catalog_system;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Borrower extends User {
    private static int nextBorrowerId = 0;
    private final int borrowerId;
    protected List<Transaction> transactions;
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


    public void display_info() {
        System.out.println("Display Borrower : " + getUsername() + " Information");
        System.out.println(" ================================================================");
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Address: " + getAddress());
        System.out.println("Phone Number: " + getPhone());
        System.out.println("Borrower ID: " + getBorrowerId());
    }

}
