package com.example.library_catalog_system;


public class Authors {
    private String name, surname, email, phone;
    static int numOfBooks = 0;
    Books books;

    public Authors(String name, String surname, String email, String phone, Books books) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.books = books;
        ++numOfBooks;
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

    public void display_author_info() {
        System.out.println("Display Author : " + getName() + " Information");
        System.out.println(" ================================================================");
        System.out.println("Name: " + getName());
        System.out.println("Surname: " + getSurname());
        System.out.println("Email: " + getEmail());
        System.out.println("Phone Number: " + getPhone());
        System.out.println("Number of Books: " + numOfBooks);
    }
}
