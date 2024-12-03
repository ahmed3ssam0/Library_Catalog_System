package com.example.library_catalog_system;

public class Customers extends User{
    private static int customerId = 0;
    Orders order;
    public Customers(String name, String address, String phone, String email, String gender, String username, String password, Orders order) {
        super(name, address, phone, email, gender, username, password);
        this.order = order;
        ++customerId;
    }

    public int getCustomerId() {
        return customerId;
    }
    public Orders getOrder() {
        return order;
    }

    public void display_info() {
        System.out.println("Display Customer : " + getUsername() + " Information");
        System.out.println(" ================================================================");
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Gender: " + getGender());
        System.out.println("Address: " + getAddress());
        System.out.println("Phone Number: " + getPhone());
        System.out.println("Customer ID: " + getCustomerId());
    }
}
