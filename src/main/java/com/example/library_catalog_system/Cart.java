package com.example.library_catalog_system;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static com.example.library_catalog_system.Library.books;
public class Cart {

        private final String filename = "C:\\Users\\3510\\Desktop\\project\\Library_Catalog_System\\files\\CustomersCart\\";
        public List<CartBook> Cbooks;
        public Cart () {
            Cbooks = new ArrayList<>();
        }

        public void addItems(Book book, int quantity, Library mylibrary, String customerId) {
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
                save_booksInCart_to_file(customerId);
                mylibrary.save_books_to_file();
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }

        public void deleteItem(int bookId, Library mylibrary, String customerId) {
            for (CartBook cartbook : Cbooks) {
                if (cartbook.getBook().getBookId() == bookId) {
                    cartbook.getBook().setnumofcopies(cartbook.getBook().getNumOfCopies() + cartbook.getQuantity());
                    Cbooks.remove(cartbook);
                    System.out.println("All copies of the item removed from cart.");
                    save_booksInCart_to_file(customerId);
                    mylibrary.save_books_to_file();
                    return;
                }
            }
            System.out.println("Book not found in cart.");
        }

        public void updateItemQuantity(int bookId, int quantity, Library mylibrary, String customerId) {
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
                    save_booksInCart_to_file(customerId);
                    mylibrary.save_books_to_file();
                    return;
                }
            }
            System.out.println("Book not found in the cart.");
        }



        public void save_booksInCart_to_file(String customerId) {
            try (FileWriter writer = new FileWriter(filename + customerId + "_cart.txt"))
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

        public void LoadBooksFromFile(String customerId) {
            Cbooks.clear();
            File file = new File(filename + customerId + "_cart.txt");
            if (!file.exists()) {
                try {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                    System.out.println("File created: " + filename + customerId + "_cart.txt");
                } catch (IOException e) {
                    System.out.println("Error creating file: " + e.getMessage());
                    return;
                }
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    CartBook cbook = CartBook.fromFileFormat(line);
                    Cbooks.add(cbook);
                }
                System.out.println("Cart loaded successfully from file!");
            } catch (IOException e) {
                System.out.println("Error loading books from file: " + e.getMessage());
            }


        }

        public void clearFile(String customerId) {
            try (FileWriter _ = new FileWriter(filename  + customerId + "_cart.txt", false)) {
                System.out.println("File cleared successfully.");
            } catch (IOException e) {
                System.out.println("An error occurred while clearing the file: " + e.getMessage());
            }
        }
}


