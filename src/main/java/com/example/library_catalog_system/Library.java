package com.example.library_catalog_system;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private String name, address;
    private static int numOfSections, numOfBooks;
    private static List<Book> books;
    public Library(String name, String address) {
        this.name = name;
        this.address = address;
        books = new ArrayList<>();
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public static List<Book> getBooks() {
        return books;
    }
    public void setBooks(Book book) {
        books.add(book);
    }
}
