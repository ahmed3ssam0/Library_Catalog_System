package com.example.library_catalog_system;

public class Borrowers extends Customers {
    private static int borrowerId = 0;
    public Borrowers(String name, String address, String phone, String email, String gender, String username, String password, Orders order) {
        super(name, address, phone, email, gender, username, password, order);
        ++borrowerId;
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
        System.out.println("Orders: " + getOrder());
        System.out.println("Borrower ID: " + getBorrowerId());
    }
}
