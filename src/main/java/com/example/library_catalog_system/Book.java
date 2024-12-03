package com.example.library_catalog_system;

public class Book {
    private static int BookId = 0, numOfCopies = 0;
    private int price;
    private int publication_year;
    private final int numOfPages;
    private String title;
    protected Author author;
    boolean found = false;
    public Book(String title, int numOfPages, int price, int publication_year, Author author) {
        this.title = title;
        this.price = price;
        this.publication_year = publication_year;
        this.numOfPages = numOfPages;
        this.author = author;
        for (int i = 0; i < Library.getBooks().size(); i++) {
            if (Library.getBooks().get(i).getTitle().equals(title) && Library.getBooks().get(i).getAuthor().getName().equals(author.getName())) {
                found = true;
                numOfCopies++;
            }
        }
        if (!found) {
            ++BookId;
        }
    }

    public int getBookId() {
        return BookId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getPublication_year() {
        return publication_year;
    }
    public void setPublication_year(int publication_year) {
        this.publication_year = publication_year;
    }
    public Author getAuthor() {
        return author;
    }
    public void setAuthor(Author author) {
        this.author = author;
    }

    public void display_book_info() {
        System.out.println("Display Book : " + getTitle() + " Information");
        System.out.println(" ================================================================");
        System.out.println("Book ID: " + getBookId());
        System.out.println("Title: " + getTitle());
        System.out.println("Number of Pages: " + numOfPages);
        System.out.println("Publication Year: " + getPublication_year());
        System.out.println("Price: " + getPrice());
        System.out.println("Author: " + getAuthor().getName());
        if (numOfCopies > 0) {
            System.out.println("Status: Available - " + numOfCopies + " copies");
        } else {
            System.out.println("Status: Not available - " + numOfCopies + " copies");
        }
    }

}
