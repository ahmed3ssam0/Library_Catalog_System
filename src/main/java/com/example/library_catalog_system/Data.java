package com.example.library_catalog_system;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Data {

    private final String FILE_NAME = "E:\\ahmed\\java\\Library_Catalog_System\\Library_Catalog_System\\files\\";

    public List<Borrower> loadBorrowersFromFile() {
        List<Borrower> borrowers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME + "borrowers.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Borrower borrower = Borrower.fromFileFormat(line);
                borrowers.add(borrower);
            }
            return borrowers;
        } catch (IOException e) {
            System.out.println("Error loading Borrowers from file: " + e.getMessage());
            return null;
        }
    }

    public List<Customer> loadCustomersFromFile() {
        File writer = new File(FILE_NAME + "customers.txt");
        List<Customer> customers = new ArrayList<>();
        try (Scanner scan = new Scanner(writer)) {
            while (scan.hasNextLine()) {
                Customer customer = Customer.fromFileFormat(scan.nextLine());
                customers.add(customer);
            }
            return customers;
        } catch (IOException e) {
            System.out.println("Error loading Customers from file: " + e.getMessage());
            return null;
        }
    }

    public List<Book> loadBooksFromFile() {
        List<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME + "books.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Book book = Book.fromFileFormat(line);
                books.add(book);
            }
            return books;
        } catch (IOException e) {
            System.out.println("Error loading books from file: " + e.getMessage());
            return null;
        }
    }

    public List<CartBook> loadCartFromFile(String customerId) {
        List<CartBook> Cbooks = new ArrayList<>();
        File file = new File(FILE_NAME + "CustomersCart\\" + customerId + "_cart.txt");
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                System.out.println("File created: " + FILE_NAME + "CustomersCart\\" + customerId + "_cart.txt");
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                CartBook cbook = CartBook.fromFileFormat(line);
                Cbooks.add(cbook);
            }
            return Cbooks;
        } catch (IOException e) {
            System.out.println("Error loading books from file: " + e.getMessage());
            return null;
        }
    }

    public List<CartBook> loadOrdersFromFile(String customerId) {
        List<CartBook> orderBooks = new ArrayList<>();
        File file = new File(FILE_NAME + "CustomersBuying\\" + customerId + "_buying.txt");
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                System.out.println("File created: " + FILE_NAME + "CustomersBuying\\" + customerId + "_buying.txt");
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                CartBook order = CartBook.fromFileFormat(line);
                orderBooks.add(order);
            }
            return orderBooks;
        } catch (IOException e) {
            System.out.println("Error loading orders from file: " + e.getMessage());
            return null;
        }
    }

    public void save_borrowers_to_file(List<Borrower> borrowers) {
        try (FileWriter writer = new FileWriter(FILE_NAME + "borrowers.txt")) {
            for (Borrower borrower : borrowers) {
                writer.write(borrower.toFileFormat() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving books to file: " + e.getMessage());
        }
    }

    public void save_customers_to_file(List<Customer> customers) {
        try (FileWriter writer = new FileWriter(FILE_NAME + "customers.txt")) {
            for (Customer customer : customers) {
                writer.write(customer.toFileFormat() + "\n");
                System.out.println("Customer " + customer.getUsername() +  " loaded successfully.");
            }
        } catch (IOException e) {
            System.out.println("Error saving Customer to file: " + e.getMessage());
        }
    }


    public void save_books_to_file(List<Book> books) {
        try (FileWriter writer = new FileWriter(FILE_NAME + "books.txt")) {
            for (Book book : books) {
                writer.write(book.toFileFormat() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving books to file: " + e.getMessage());
        }
    }

    public void save_booksInCart_to_file(String customerId, List<CartBook> Cbooks) {
        if (!customerId.isEmpty() && !customerId.isBlank()) {
            try (FileWriter writer = new FileWriter(FILE_NAME + "CustomersCart\\" + customerId + "_cart.txt")) {
                for (CartBook cbook : Cbooks) {
                    writer.write(cbook.ToFileFormat() + "\n");
                }
                System.out.println("Cart saved successfully to file!");
            } catch (IOException e) {
                System.out.println("Error saving books to file: " + e.getMessage());
            }
        }
    }

    public void clearFile(String customerId) {
        if (!customerId.isEmpty() && !customerId.isBlank()) {
            try (FileWriter _ = new FileWriter(FILE_NAME + "CustomersCart\\" + customerId + "_cart.txt", false)) {
                System.out.println("File cleared successfully.");
            } catch (IOException e) {
                System.out.println("An error occurred while clearing the file: " + e.getMessage());
            }
        }
    }

    public void saveBooksInOrder(String customerId, List<CartBook> orderBooks) {
        if (!customerId.isEmpty() && !customerId.isBlank()) {
            try (FileWriter writer = new FileWriter(FILE_NAME + "CustomersBuying\\" + customerId + "_buying.txt")) {
                for (CartBook order : orderBooks) {
                    writer.write(order.ToFileFormat() + "\n");
                }
                System.out.println("Orders saved successfully to file!");
            } catch (IOException e) {
                System.out.println("Error saving orders to file: " + e.getMessage());
            }
        }
    }



    public void writeBorrowerTransactionToFile(List<Borrower> borrowers) {
        for (Borrower borrower : borrowers) {
            try (PrintWriter writer = new PrintWriter(FILE_NAME + "Borrowers\\"+borrower.getBorrowerId() + "_history.txt")) {
                for(Transaction transaction: borrower.getTransactions()) {
                    writer.println("Transaction{" +
                            "book=" + transaction.getBook().getTitle() +
                            ", borrowDate=" + transaction.getBorrowDate() +
                            ", returnDate=" + transaction.getReturnDate() +
                            '}');
                }
            } catch (IOException e) {
                System.out.println("Error writing transaction to file: " + e.getMessage());
            }
        }
    }
    public void writeCustomerTransactionToFile(List<Customer> customers) {
        for (Customer customer : customers) {
            try (PrintWriter writer = new PrintWriter(FILE_NAME + "CustomersBorrowings\\" + customer.getCustomerId() + "_history.txt")) {
                for(Transaction transaction: customer.getTransactions()) {
                    writer.println("Transaction{" +
                            "book=" + transaction.getBook().getTitle() +
                            ", borrowDate=" + transaction.getBorrowDate() +
                            ", returnDate=" + transaction.getReturnDate() +
                            '}');
                }
            } catch (IOException e) {
                System.out.println("Error writing transaction to file: " + e.getMessage());
            }
        }
    }


    public void recordTransactionsToFile(int type) {
        if (Library.All_Transaction.isEmpty()) {
            System.out.println("No transactions recorded.");
        } else {
            System.out.println("Library Transactions:");

            try(PrintWriter writer=new PrintWriter(FILE_NAME + "All_Transactions.txt")){
                for (Transaction transaction : Library.All_Transaction) {
                    if (type == 1)
                        writer.println("Transaction{" +
                                "borrower :" + transaction.getBorrower().getName() +
                                ", book : " + transaction.getBook().getTitle() +
                                ", borrowDate=" + transaction.getBorrowDate() +
                                ", returnDate=" + transaction.getReturnDate() +
                                '}');
                    else
                        writer.println("Transaction{" +
                                "Customer :" + transaction.getCustomer().getName() +
                                ", book : " + transaction.getBook().getTitle() +
                                ", borrowDate=" + transaction.getBorrowDate() +
                                ", returnDate=" + transaction.getReturnDate() +
                                '}');
                }
            }
            catch (IOException e){
                System.out.println("Error writing transactions to file: " + e.getMessage());
            }

        }
    }


    public void writeReviewToFile(int rate, String review, String title) {
        String filePath = FILE_NAME + "Books_reviews\\" + title + "_Reviews.txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println(rate + "," + review);
        } catch (IOException e) {
            throw new RuntimeException("Error writing review to file: " + filePath, e);
        }
    }

    public List<String> readAllReviews(String title) {
        String filePath = FILE_NAME + "Books_reviews\\" + title + "_Reviews.txt";
        List<String> reviews = new ArrayList<>();
        File file = new File(filePath);
        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                // Assuming the format is "rate,review", split to extract review
                String[] parts = line.split(",", 2);
                if (parts.length > 1) {
                    reviews.add(parts[1]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error while reading from file: " + e.getMessage());
        }
        return reviews;
    }

    public List<Integer> readAllRatings(String title) {
        String filePath = FILE_NAME + "Books_reviews\\" + title + "_Reviews.txt";
        List<Integer> ratings = new ArrayList<>();
        File file = new File(filePath);
        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                // Assuming the format is "rate,review", split to extract rate
                String[] parts = line.split(",", 2);
                if (parts.length > 0) {
                    try {
                        ratings.add(Integer.parseInt(parts[0]));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid rating found: " + parts[0]);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error while reading from file: " + e.getMessage());
        }
        return ratings;
    }

}
