package com.example.library_catalog_system;

public class Books {
    private static int BookId = 0;
    private int price, publication_year;
    private String title;
    private boolean status;
    Authors author;
    public Books(String title, int price, int publication_year, boolean status, Authors author) {
        this.title = title;
        this.price = price;
        this.publication_year = publication_year;
        this.status = status;
        this.author = author;
        ++BookId;
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
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public Authors getAuthor() {
        return author;
    }

}
