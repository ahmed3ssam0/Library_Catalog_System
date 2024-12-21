package com.example.library_catalog_system;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private final String filename = "C:\\Users\\3510\\Desktop\\project\\Library_Catalog_System\\files\\CustomersBuyings\\";
    public List<CartBook> orderBooks;

    public Order() {
        orderBooks = new ArrayList<>();
    }

    public String totalPrice() {
        float totalPrice = 0;
        for (CartBook order : orderBooks) {
            totalPrice += order.getBook().price * order.getQuantity();
        }
        return String.format("%.2f", totalPrice);
    }

    public void saveBooksInOrder(String customerId) {
        try (FileWriter writer = new FileWriter(filename + customerId + "_buying.txt"))
        {
            for (CartBook order : orderBooks)
            {
                writer.write(order.ToFileFormat() + "\n");
            }
            System.out.println("Orders saved successfully to file!");
        } catch (IOException e)
        {
            System.out.println("Error saving orders to file: " + e.getMessage());
        }
    }

    public void loadOrdersFromFile(String customerId) {
        orderBooks.clear();
        File file = new File(filename + customerId + "_buying.txt");
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                System.out.println("File created: " + filename + customerId + "_buying.txt");
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
                return;
            }
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                CartBook order = CartBook.fromFileFormat(line);
                orderBooks.add(order);
            }
            System.out.println("Orders loaded successfully from file!");
        } catch (IOException e) {
            System.out.println("Error loading orders from file: " + e.getMessage());
        }


    }

}