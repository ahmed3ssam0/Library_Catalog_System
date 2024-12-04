package com.example.library_catalog_system;

public class Book {
    private static int nextBookId = 1;
    private final int bookId, numOfPages;
    private String title;
    private int price, publicationYear, numOfCopies;
    private Author author;

    public Book(String title, int numOfPages, int price, int publicationYear, Author author) {
        if (title == null || title.isEmpty() || author == null) {
            throw new IllegalArgumentException("Title and Author cannot be null or empty");
        }

        this.bookId = nextBookId++;
        this.title = title;
        this.numOfPages = numOfPages;
        this.price = price;
        this.publicationYear = publicationYear;
        this.author = author;
        this.numOfCopies = 1;

        author.addBook(this);
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        if (publicationYear <= 0) {
            throw new IllegalArgumentException("Invalid publication year");
        }
        this.publicationYear = publicationYear;
    }

    public int getNumOfPages() {
        return numOfPages;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        if (author == null) {
            throw new IllegalArgumentException("Author cannot be null");
        }
        this.author = author;
        author.addBook(this);
    }

    public int getNumOfCopies() {
        return numOfCopies;
    }

    public void incrementCopies() {
        numOfCopies++;
    }

    public void decrementCopies() {
        if (numOfCopies > 0) {
            numOfCopies--;
        } else {
            System.out.println("No copies left to remove");
        }
    }

    public void displayBookInfo() {
        System.out.println("\nBook " + getTitle() + " Information");
        System.out.println("================================================================");
        System.out.println("Book ID: " + getBookId());
        System.out.println("Title: " + getTitle());
        System.out.println("Number of Pages: " + getNumOfPages());
        System.out.println("Publication Year: " + getPublicationYear());
        System.out.println("Price: $" + getPrice());
        System.out.println("Author: " + getAuthor().getName());
        System.out.println("Status: " + (numOfCopies > 0 ? "Available - " + numOfCopies + " copies" : "Not available"));
    }
}
