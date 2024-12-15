package com.example.library_catalog_system;

public class CartBook {
    private Book book;
    private int quantity;

    public CartBook(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;

    }

    public Book getBook() {
        return book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.quantity = quantity;
    }


    public void setBook(Book book) {
        this.book = book;
    }


    public String toString() {
        return "Book---> "+"Book id :"+book.getBookId() +",Book title :"+ book.getTitle() + ", Quantity: " + quantity + ", Total Price:" + (quantity * book.getPrice())+"$";
    }


    public String ToFileFormat() {
        return book.getBookId() + "," + book.getTitle() + "," + quantity + "," + (quantity * book.getPrice());

    }


    public static CartBook fromFileFormat(String line) {
        String[] parts = line.split(",");
        int bookId = Integer.parseInt(parts[0]);
        String title = parts[1];
        int quantity = Integer.parseInt(parts[2]);
        Book book = null;
        for (Book b : Library.books) {
            if (b.getBookId() == bookId) {
                book = b;
                break;
            }
        }



        return new CartBook(book, quantity);
    }

}

