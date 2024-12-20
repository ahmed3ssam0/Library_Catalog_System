package com.example.library_catalog_system;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Borrower extends User {
    private static int nextBorrowerId = 0;
    private int borrowerId;
    public List<Transaction> transactions;
    public Borrower(String name, String address, String phone, String email) {
        super(name, address, phone, email);
        this.transactions = new ArrayList<>();
        borrowerId = ++nextBorrowerId;
    }

    public int getBorrowerId() {
        return borrowerId;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    //  View Borrowing History
    public List<String> viewBorrowingHistory() {
        String filePath= "C:\\Users\\3510\\Desktop\\Library-System\\Library_Catalog_System\\files\\Borrowers\\"+borrowerId + "_history.txt";
        List<String> data = new ArrayList<>();
        System.out.println("Borrowing History for you: " );
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


    public void display_info() {
        System.out.println("Display Borrower : " + getName() + " Information");
        System.out.println(" ================================================================");
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Address: " + getAddress());
        System.out.println("Phone Number: " + getPhone());
        System.out.println("Borrower ID: " + getBorrowerId());
    }


    public static Borrower fromFileFormat(String line) {
        String[] parts = line.split(",");  // Assuming CSV format for each borrower
        int Id = Integer.parseInt(parts[0]);
        Borrower borrower = new Borrower(parts[1], parts[2], parts[3], parts[4]);
        borrower.borrowerId = Id;
        return borrower;
    }



    public String toFileFormat() {
        return borrowerId + "," + getName() + "," + getAddress() + "," + getPhone() + "," + getEmail();
    }
}
