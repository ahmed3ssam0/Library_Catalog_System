package com.example.library_catalog_system;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User{
    private static int nextCustomerId = 0;
    private int customerId;
    List<Order> orders;
    private String username, password;
    public Customer(String name, String address, String phone, String email, String username, String password) {
        super(name, address, phone, email);
        this.username = username;
        this.password = password;
        this.orders = new ArrayList<>();
        customerId = ++nextCustomerId;
    }


    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public List<Order> getOrders() {
        return orders;
    }
    public void setOrders(Order order) {
        orders.add(order);
    }


    public String toFileFormat() {
        return customerId + "," + getName() + "," + getAddress() + "," + getPhone() + "," + getEmail() + "," + username + "," + password;
    }

    public static Customer fromFileFormat(String line) {
        String[] parts = line.split(",");
        int customerId = Integer.parseInt(parts[0]);
        String username = parts[1];
        String password = parts[2];
        String name = parts[3];
        String address = parts[4];
        String phone = parts[5];
        String email = parts[6];
        return new Customer(name, address, phone, email, username, password);
    }


    public void display_info() {
        System.out.println("Display Customer : " + getUsername() + " Information");
        System.out.println(" ================================================================");
        System.out.println("Customer ID: " + getCustomerId());
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Address: " + getAddress());
        System.out.println("Phone Number: " + getPhone());
        System.out.println("Orders: ");
        for (Order order : orders) {
            System.out.println(" - " + order.getOrderId());
            for (Book book : order.getBooks()) {
                System.out.println(" - " + book.getTitle());
            }
        }
    }
}
