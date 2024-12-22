package com.example.library_catalog_system;

import java.util.ArrayList;
import java.util.List;

public class Order {
    public static List<CartBook> orderBooks;

    public Order() {
        orderBooks = new ArrayList<>();
    }

    public static void setOrderBooks(List<CartBook> orders) {
        Order.orderBooks = orders;
    }

    public static List<CartBook> getOrderBooks() {
        return orderBooks;
    }

    public String totalPrice() {
        float totalPrice = 0;
        for (CartBook order : orderBooks) {
            totalPrice += order.getBook().price * order.getQuantity();
        }
        return String.format("%.2f", totalPrice);
    }

}