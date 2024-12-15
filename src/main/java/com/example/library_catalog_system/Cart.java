package com.example.library_catalog_system;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import static com.example.library_catalog_system.Library.books;
public class Cart {


        Library mylibrary=new Library();

        private final String filename = "C:\\Users\\elturky\\Desktop\\project200\\Library_Catalog_System\\files\\cartbooks.txt";
        ArrayList<CartBook> Cbooks=new ArrayList<>();
        public Cart ()
        {
            Cbooks=new ArrayList<>();
        }
        public void displayBooksSummaryFromFile() {
            try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\elturky\\Desktop\\project200\\Library_Catalog_System\\files\\books.txt"))) {
                String line;
                System.out.printf("%-10s %-20s %-10s  %-10s\n", "ID", "Title", "Price","number of copy");
                System.out.println("----------------------------------------------------------");

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 5) {
                        String bookId = parts[0];
                        String title = parts[1];
                        String price = parts[4];
                        String numOfCopies = parts[3];
                        System.out.printf("%-10s %-20s %-10s %-10s \n", bookId, title, price,numOfCopies);
                    } else {
                        System.out.println("Skipping invalid line: " + line);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading books from file: " + e.getMessage());
            }
        }
        public void addItems(Book book, int quantity) {
            try {
                // Check availability of copies
                if (quantity > book.getNumOfCopies()) {
                    throw new IllegalArgumentException("The requested quantity (" + quantity + ") exceeds the available copies (" + book.getNumOfCopies() + ").");
                }

                for (CartBook cartbook : Cbooks) {
                    // If the book is already available in cart, I will supply the quantity only
                    if (cartbook.getBook().getBookId() == book.getBookId())
                    {
                        cartbook.setQuantity(cartbook.getQuantity() + quantity);
                        System.out.println("Quantity updated successfully in the cart!");
                        return;
                    }
                }
                //If the book does not already exist in cart, I will add a place for it
                Cbooks.add(new CartBook(book, quantity));
                book.setnumofcopies(book.getNumOfCopies() - quantity);
                System.out.println("Book added to the cart successfully!");
                save_booksInCart_to_file();
                mylibrary.save_books_to_file();
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }

        public void deleteItem(int bookId, int quantityToRemove) {
            for (CartBook cartbook : Cbooks)
            {
                if (cartbook.getBook().getBookId() == bookId) {
                    if (quantityToRemove >= cartbook.getQuantity()) {
                        cartbook.getBook().setnumofcopies(cartbook.getBook().getNumOfCopies() + cartbook.getQuantity());
                        Cbooks.remove(cartbook);
                        System.out.println("All copies of the item removed from cart.");
                    } else {

                        cartbook.getBook().setnumofcopies(cartbook.getBook().getNumOfCopies() + quantityToRemove);
                        cartbook.setQuantity(cartbook.getQuantity() - quantityToRemove);
                        System.out.println(quantityToRemove + " copies removed from the book.");
                    }
                    save_booksInCart_to_file();
                    mylibrary.save_books_to_file();
                    return;
                }
            }
            System.out.println("Book not found in cart.");
        }

        public void veiwCart()
        {
            if (Cbooks.isEmpty())
                System.out.println("your cart is empty .");
            else
                for(CartBook cartbook :Cbooks)
                {

                    System.out.println(cartbook);
                }
        }
        public void updateItemQuantity(int bookId, int quantity) {
            for (CartBook cartbook : Cbooks) {
                if (cartbook.getBook().getBookId() == bookId) {
                    int currentCartQuantity = cartbook.getQuantity();
                    int availableCopies = cartbook.getBook().getNumOfCopies();


                    if (quantity <= 0) {
                        cartbook.getBook().setnumofcopies(availableCopies + currentCartQuantity);
                        Cbooks.remove(cartbook);
                        System.out.println("Book removed from cart and copies returned to stock.");
                    }

                    else if (quantity > currentCartQuantity) {
                        int additionalQuantity = quantity - currentCartQuantity;
                        if (additionalQuantity > availableCopies) {
                            System.out.println("Not enough copies available to add " + additionalQuantity + " more.");
                            return;
                        }
                        cartbook.getBook().setnumofcopies(availableCopies - additionalQuantity); // تقليل النسخ المتوفرة
                        cartbook.setQuantity(quantity);
                        System.out.println("Cart updated. Added " + additionalQuantity + " more copies.");
                    }

                    else if (quantity < currentCartQuantity) {
                        int reducedQuantity = currentCartQuantity - quantity;
                        cartbook.getBook().setnumofcopies(availableCopies + reducedQuantity);
                        cartbook.setQuantity(quantity);
                        System.out.println("Cart updated. Removed " + reducedQuantity + " copies.");
                    }
                    save_booksInCart_to_file();
                    mylibrary.save_books_to_file();
                    return;
                }
            }
            System.out.println("Book not found in the cart.");
        }
        public void payment ()
        {
            Scanner input =new Scanner (System.in);
            int choice;

            double total =0;
            if (Cbooks.isEmpty())
            {
                System.out.println("your cart is empty");
                return;
            }
            for (CartBook cartBook :Cbooks)
            {
                total += cartBook.getQuantity() * cartBook.getBook().getPrice();
                System.out.println("your total price is  :" + total + "$");
            }
            while (true )
            {
                System.out.println("choose payment method :");
                System.out.println(" If you want to pay through cash on Delivery press 1 ");
                System.out.println(" If you want to pay through credit card press 2 ");
                System.out.print("Enter your choice (1 or 2) :");
                choice = input.nextInt();
                if (choice ==1 || choice ==2)
                {
                    break;
                }
                else
                {
                    System.out.println("Invalid choice please enter (1 or 2 )");

                }

            }
            if (choice ==1)
            {
                System.out.println("payment method is cash on Delivery .");
                System.out.println("checkout process completed.");
            }
            else
            {
                System.out.println("payment method is credit card .");
                System.out.println("checkout process completed.");
            }

        }
        public void save_booksInCart_to_file()
        {
            try (FileWriter writer = new FileWriter(filename))
            {
                for (CartBook cbook : Cbooks)
                {
                    writer.write(cbook.ToFileFormat() + "\n");
                }
                System.out.println("Cart saved successfully to file!");
            } catch (IOException e)
            {
                System.out.println("Error saving books to file: " + e.getMessage());
            }
        }

        public void LoadBooksFromFile() {
            Cbooks.clear();
            File file = new File(filename);
            if (!file.exists()) {
                try {

                    file.getParentFile().mkdirs();
                    file.createNewFile();
                    System.out.println("File created: " + filename);
                } catch (IOException e) {
                    System.out.println("Error creating file: " + e.getMessage());
                    return;
                }
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    CartBook cbook = CartBook.fromFileFormat(line);
                    Cbooks.add(cbook);
                }
                System.out.println("Cart loaded successfully from file!");
            } catch (IOException e) {
                System.out.println("Error loading books from file: " + e.getMessage());
            }


        }}


