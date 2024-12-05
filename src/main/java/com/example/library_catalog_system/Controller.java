package com.example.library_catalog_system;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class Controller {
    @FXML
    private HBox box, gender;
    @FXML
    private Button login, register, back;
    @FXML
    private TextField usernameField, passwordField;
    @FXML
    private Labeled messageLabel;

    private void switchScene(String fxmlFile, ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFile));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Library Catalog System");
        stage.setWidth(stage.getWidth());
        stage.setHeight(stage.getHeight());
        stage.show();
    }
    private void switchToAdmin(ActionEvent actionEvent) throws IOException {
        switchScene("admin_panel.fxml", actionEvent);
    }

    @FXML
    public void initialize() {
        // Hover
        box.getChildren().filtered(node -> node instanceof Button).forEach(node -> {
            Button button = (Button) node;

            // Define scale transitions
            ScaleTransition scaleUp = new ScaleTransition(Duration.millis(300), button);
            scaleUp.setToX(1.1);
            scaleUp.setToY(1.1);

            ScaleTransition scaleDown = new ScaleTransition(Duration.millis(300), button);
            scaleDown.setToX(1.0);
            scaleDown.setToY(1.0);

            // Add hover listeners
            button.setOnMouseEntered(e -> {
                scaleDown.stop();
                scaleUp.playFromStart();
                button.setStyle("-fx-background-color: #572710; -fx-text-fill: #fff;");
            });

            button.setOnMouseExited(e -> {
                scaleUp.stop();
                scaleDown.playFromStart();
                button.setStyle("-fx-background-color: #fff; -fx-text-fill: #000;");
            });
        });

    }

    @FXML
    private void handleLogin(ActionEvent actionEvent) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if ("admin".equals(username) && "admin".equals(password)) {
            switchToAdmin(actionEvent);
        } else {
            messageLabel.setText("Incorrect username or password");
            messageLabel.setTextFill(Color.RED);
        }
    }

    @FXML
    private void switchToLogin(ActionEvent actionEvent) throws IOException {
        switchScene("login.fxml", actionEvent);
    }
    @FXML
    private void switchToRegister(ActionEvent actionEvent) throws IOException {
        switchScene("register.fxml", actionEvent);
    }
    @FXML
    private void backToHome(ActionEvent actionEvent) throws IOException {
        switchScene("home.fxml", actionEvent);
    }
    @FXML
    private void adminBooks(ActionEvent actionEvent) throws IOException {
        switchScene("admin_books.fxml", actionEvent);
    }
    @FXML
    private void adminBorrowers(ActionEvent actionEvent) throws IOException {
        switchScene("admin_borrowers.fxml", actionEvent);
    }
    @FXML
    private void adminInventory(ActionEvent actionEvent) throws IOException {
        switchScene("admin_inventory.fxml", actionEvent);
    }
    @FXML
    private void backToAdmin(ActionEvent actionEvent) throws IOException {
        switchToAdmin(actionEvent);
    }
}