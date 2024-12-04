package com.example.library_catalog_system;

import java.util.ArrayList;
import java.util.List;

public class Borrower extends User {
    private static int nextBorrowerId = 0;
    private final int borrowerId;
    protected List<Order> orders;
    public Borrower(String name, String address, String phone, String email, String gender, String username, String password) {
        super(name, address, phone, email, gender, username, password);
        this.orders = new ArrayList<>();
        borrowerId = ++nextBorrowerId;
    }
    public int getBorrowerId() {
        return borrowerId;
    }
    public void display_info() {
        System.out.println("Display Borrower : " + getUsername() + " Information");
        System.out.println(" ================================================================");
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Gender: " + getGender());
        System.out.println("Address: " + getAddress());
        System.out.println("Phone Number: " + getPhone());
        System.out.println("Borrower ID: " + getBorrowerId());
    }
}
