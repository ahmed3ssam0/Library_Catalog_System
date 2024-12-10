package com.example.library_catalog_system;

import java.util.ArrayList;
import java.util.List;

public class Author {
    private String name, surname, email, phone;
    private static List<Book> books;



    public Author(String name, String surname, String email, String phone) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.books = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        boolean found = false;
        for (int i = 0; i < Author.getBooks().size(); i++) {
            if (Author.getBooks().get(i).getTitle().equals(book.getTitle())) {
                found = true;
                break;
            }
        }
        if (!found) {
            books.add(book);
        }
    }

    public void display_author_info() {
        System.out.println("\nAuthor " + getName() + " " + getSurname() + " Information");
        System.out.println("================================================================");
        System.out.println("Name: " + getName());
        System.out.println("Surname: " + getSurname());
        System.out.println("Email: " + getEmail());
        System.out.println("Phone Number: " + getPhone());
        System.out.println("Number of Books: " + books.size());
        System.out.println("Books: ");
        for (Book book : books) {
            System.out.println(" - " + book.getTitle());
        }
    }
    public String toFileFormat() {
        return name + "," + surname + "," + email + "," + phone;
    }

    public static Author fromFileFormat(String line) {
        String[] parts = line.split(",");
        return new Author(parts[0], parts[1], parts[2], parts[3]);
    }
}
