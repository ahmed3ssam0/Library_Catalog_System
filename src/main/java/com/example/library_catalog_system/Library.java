package com.example.library_catalog_system;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private String name, address;
    private static int numOfSections, numOfBooks;
    private static List<Book> books;
    private static List<Author> authors;
    public Library(String name, String address) {
        this.name = name;
        this.address = address;
        books = new ArrayList<>();
        authors = new ArrayList<>();
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
        boolean found = false;
        for (int i = 0; i < Library.getBooks().size(); i++) {
            if (Library.getBooks().get(i).getTitle().equals(book.getTitle()) && Library.getBooks().get(i).getAuthor().equals(book.getAuthor())) {
                found = true;
                Library.getBooks().get(i).incrementCopies();
            }
        }
        if (!found) {
            books.add(book);
        }
    }
    public static List<Author> getAuthors() {
        return authors;
    }
    public void setAuthors(Author author) {
        boolean found = false;
        for (int i = 0; i < Library.getAuthors().size(); i++) {
            if (Library.getAuthors().get(i).getPhone().equals(author.getPhone()) && Library.getAuthors().get(i).getEmail().equals(author.getEmail())) {
                found = true;
                break;
            }
        }
        if (!found) {
            authors.add(author);
        }
    }

    public void displayLibraryInfo() {
        System.out.println("\nLibrary Information");
        System.out.println("================================================================");
        System.out.println("Library Name: " + getName());
        System.out.println("Library Address: " + getAddress());
        int allCopies = 0;
        for (Book book : getBooks()) {
            allCopies += book.getNumOfCopies();
        }
        System.out.println("The Library has " + Library.getBooks().size() + " books and " + allCopies + " copies");
        System.out.println("Number of Authors: " + Library.getAuthors().size());
    }
}
