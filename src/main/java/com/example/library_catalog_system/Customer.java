package com.example.library_catalog_system;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User{
    private static int nextCustomerId = 0;
    private final int customerId;
    List<Order> orders;
    public Customer(String name, String address, String phone, String email, String username, String password) {
        super(name, address, phone, email, username, password);
        this.orders = new ArrayList<>();
        customerId = ++nextCustomerId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public List<Order> getOrders() {
        return orders;
    }
    public void setOrders(Order order) {
        orders.add(order);
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
