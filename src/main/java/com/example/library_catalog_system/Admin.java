package com.example.library_catalog_system;

public class Admin extends User {
    public Admin(String name, String address, String phone, String email, String gender, String username, String password) {
        super(name, address, phone, email, gender, username, password);
    }

    public void display_info() {
        System.out.println("\nAdmin : " + getUsername() + " Information");
        System.out.println("================================================================");
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Gender: " + getGender());
        System.out.println("Address: " + getAddress());
        System.out.println("Phone Number: " + getPhone());
        System.out.println("Username: " + getUsername());
        System.out.println("Password: " + getPassword());
    }
}
