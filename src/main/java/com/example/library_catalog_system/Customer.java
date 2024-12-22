package com.example.library_catalog_system;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Customer extends User{
    private static int nextCustomerId = 0;
    private int customerId;
    List<Order> orders;
    public List<Transaction> transactions;
    private String username, password;
    public Customer(String name, String address, String phone, String email, String username, String password) {
        super(name, address, phone, email);
        this.username = username;
        this.password = password;
        this.orders = new ArrayList<>();
        this.transactions = new ArrayList<>();
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

    public List<Transaction> getTransactions() {
        return transactions;
    }
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<String> viewBorrowingHistory() {
        String filePath= "E:\\ahmed\\java\\Library_Catalog_System\\Library_Catalog_System\\files\\CustomersBorrowings\\" + customerId + "_history.txt";
        List<String> data = new ArrayList<>();
        File writer=new File(filePath);
        try(Scanner scan=new Scanner(writer)){
            while(scan.hasNextLine()) {
                data.add(scan.nextLine());
            }
            return data;
        }
        catch (FileNotFoundException e){
            System.out.println("Error while Reading from file");
        }
        return null;
    }

    public String toFileFormat() {
        return customerId + "," + getName() + "," + getAddress() + "," + getPhone() + "," + getEmail() + "," + username + "," + password;
    }

    public static Customer fromFileFormat(String line) {
        String[] parts = line.split(",");
        int Id = Integer.parseInt(parts[0]);
        String name = parts[1];
        String address = parts[2];
        String phone = parts[3];
        String email = parts[4];
        String username = parts[5];
        String password = parts[6];
        Customer customer = new Customer(name, address, phone, email, username, password);
        customer.customerId = Id;
        return customer;
    }


    public void display_info() {
        System.out.println("Display Customer : " + getUsername() + " Information");
        System.out.println(" ================================================================");
        System.out.println("Customer ID: " + getCustomerId());
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Address: " + getAddress());
        System.out.println("Phone Number: " + getPhone());
    }
}
