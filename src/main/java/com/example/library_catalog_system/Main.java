package com.example.library_catalog_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
//        launch();
//        Library library = new Library("H&M", "Cairo");
//        Author author = new Author("Ahmed", "Essam", "ahmed@gmail.com", "01", "male");
//        library.setAuthors(author);
//
//        Book book1 = new Book("Math", 300, 200, 2005, author);
//        Book book2 = new Book("Arabic", 300, 200, 2005, author);
//        Book book3 = new Book("Math", 300, 200, 2005, author);
//        library.setBooks(book1);
//        library.setBooks(book2);
//        library.setBooks(book3);
//        for (int i = 0; i < library.getBooks().size(); i++) {
//            library.getBooks().get(i).displayBookInfo();
//        }
//        author.display_author_info();
//        library.displayLibraryInfo();
//        Admin admin = new Admin("ahmed","cairo","01","a@a.com","male", "admin", "admin");
    }
}