package com.example.library_catalog_system;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private static int orderId = 0;
    List<Book> books;
    private int price, quantity;
    public Order(Book book, int price, int quantity) {
        this.books = new ArrayList<>();
        this.price = price;
        this.quantity = quantity;
        List<Book> books1 = new ArrayList<>();
        ++orderId;
    }

    public int getOrderId() {
        return orderId;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Book> getBooks() {
        return books;
    }
    public void setBooks(Book book) {
        books.add(book);
    }

}