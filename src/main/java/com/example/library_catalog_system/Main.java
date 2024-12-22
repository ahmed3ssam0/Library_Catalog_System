
package com.example.library_catalog_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Controller.loadAll();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Night Library");
        stage.setWidth(1545);
        stage.setHeight(830);
        stage.show();
    }
    @Override
    public void stop() {
        Controller.saveAll();
    }

    public static void main(String[] args) {
        launch();
    }
}


