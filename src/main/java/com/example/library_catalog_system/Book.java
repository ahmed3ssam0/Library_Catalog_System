package com.example.library_catalog_system;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.io.*;

public class Book {
    private static int nextBookId = 1;
    private int bookId;
    private final int numOfPages;
    private String title;
    public float price;
    public int publicationYear, numOfCopies;
    private Author author;
    private List<String> reviews;
    private List<Integer> ratings;


    public Book(String title, int numOfPages, int numOfCopies, float price, int publicationYear, Author author) {
        if (title == null || title.isEmpty() || author == null) {
            throw new IllegalArgumentException("Title and Author cannot be null or empty");
        }

        this.bookId = nextBookId++;
        this.title = title;
        this.numOfPages = numOfPages;
        this.numOfCopies = numOfCopies;
        this.price = price;
        this.publicationYear = publicationYear;
        this.author = author;
        author.addBook(this);
        this.reviews = new ArrayList<>();
        this.ratings = new ArrayList<>();
    }

    public int getBookId() {
        return bookId;
    }
    public String getTitle() {
        return title;
    }
    public void setNumOfCopies(int numOfCopies){
        this.numOfCopies=numOfCopies;

    }



    public void setTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
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
    public List<String> getReviews() {
        return reviews;
    }
    public void addReview(String review, int rating) {
        this.reviews.add(review);
        this.ratings.add(rating);
        writeReviewToFile(rating, review);
    }
    public double getAverageRating() {
        if (ratings.isEmpty()) return 0.0;
        return ratings.stream().mapToInt(r -> r).average().orElse(0.0);
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
    public void remove_book(){
        if (this.numOfCopies==0)
            remove_book();
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
    public String toFileFormat() {
        return bookId + "," + title + "," + numOfPages + "," + numOfCopies + "," + price + "," + publicationYear + "," + author.toFileFormat();
    }

    public static Book fromFileFormat(String line) {
        String[] parts = line.split(",");
        int Id = Integer.parseInt(parts[0]);
        String title = parts[1];
        int numOfPages = Integer.parseInt(parts[2]);
        int numOfCopies = Integer.parseInt(parts[3]);
        float price = Float.parseFloat(parts[4]);
        int publicationYear = Integer.parseInt(parts[5]);
        Author author = Author.fromFileFormat(String.join(",", parts[6], parts[7], parts[8], parts[9]));
        Book book = new Book(title, numOfPages, numOfCopies, price, publicationYear, author);
        book.bookId = Id;

        return book;
    }

    public void setnumofcopies(int numOfCopies){
        this.numOfCopies=numOfCopies;
    }

    private void writeReviewToFile(int rate, String review) {
        String filePath = "E:\\ahmed\\java\\Library_Catalog_System\\Library_Catalog_System\\files\\Books_reviews\\" + title + "_Reviews.txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println("Rating: " + rate+" , Review: " + review);
            writer.println();
        } catch (IOException e) {
            throw new RuntimeException("Error writing review to file: " + filePath, e);
        }
    }

    public List<String> showAllRatingsAndReviews() {
        String filePath = "E:\\ahmed\\java\\Library_Catalog_System\\Library_Catalog_System\\files\\Books_reviews\\" + title + "_Reviews.txt";
        List<String> data = new ArrayList<>();
        File writer=new File(filePath);
        try(Scanner scan=new Scanner(writer)){
            while(scan.hasNextLine()) {
                data.add(scan.nextLine());
            }
            return data;
        }
        catch (FileNotFoundException e){
            System.out.println("Error while Reading from file");
        }
        return null;
    }
}

