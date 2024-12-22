package com.example.library_catalog_system;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static com.example.library_catalog_system.Library.books;
public class Cart {
        public static List<CartBook> Cbooks;
        public Cart () {
            Cbooks = new ArrayList<>();
        }

    public static void setCbooks(List<CartBook> cbooks) {
        Cbooks = cbooks;
    }

    public static List<CartBook> getCbooks() {
        return Cbooks;
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
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }

        public void deleteItem(int bookId) {
            for (CartBook cartbook : Cbooks) {
                if (cartbook.getBook().getBookId() == bookId) {
                    cartbook.getBook().setnumofcopies(cartbook.getBook().getNumOfCopies() + cartbook.getQuantity());
                    Cbooks.remove(cartbook);
                    System.out.println("All copies of the item removed from cart.");
                    return;
                }
            }
            System.out.println("Book not found in cart.");
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
                    return;
                }
            }
            System.out.println("Book not found in the cart.");
        }
}


