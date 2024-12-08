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

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;

public class Controller {
    @FXML
    private HBox box, gender;
    @FXML
    private Button login, register, back;
    @FXML
    private TextField usernameField, passwordField, R_usernameField, R_passwordField, R_name, R_address, R_phone, R_email;
    @FXML
    private Labeled messageLabel, messageLabel1;

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
        String fileName = "E:\\ahmed\\java\\Library_Catalog_System\\Library_Catalog_System\\files\\customers.txt";
        List<String> data = readData(fileName);
        String cusUser, cusPassword;
        if ("admin".equals(username) && "admin".equals(password)) {
            switchToAdmin(actionEvent);
        } else {
            for (String datum : data) {
                int cnt = 0, i1 = 0, i2 = 0;
                for (int j = 0; j < datum.length(); j++) {
                    if (datum.charAt(j) == ',') {
                        cnt++;
                        if (cnt == 5) {
                            i1 = j + 1;
                        } else if (cnt == 6) {
                            i2 = j + 1;
                        }
                    }
                }
                cusUser = datum.substring(i1, i2 - 1).trim();
                cusPassword = datum.substring(i2, datum.length() - 1).trim();
                if (cusUser.equals(username) && cusPassword.equals(password)) {
                    switchToUser(actionEvent);
                } else {
                    messageLabel.setText("Incorrect username or password");
                }
            }
        }
    }

    @FXML
    private void handleRegister(ActionEvent actionEvent) throws IOException {
        String username = R_usernameField.getText();
        String password = R_passwordField.getText();
        String name = R_name.getText();
        String address = R_address.getText();
        String phone = R_phone.getText();
        String email = R_email.getText();

        String fileName = "E:\\ahmed\\java\\Library_Catalog_System\\Library_Catalog_System\\files\\customers.txt";
        List<String> allData = readData(fileName);

        int cnt = 0, i1 = 0, i2 = 0, i3 = 0;
        String cusUser, id;
        List<Integer> takenIds = new ArrayList<>();
        for (String datum : allData) {
            for (int j = 0; j < datum.length(); j++) {
                if (datum.charAt(j) == ',') {
                    cnt++;
                    if (cnt == 5) {
                        i1 = j + 1;
                    } else if (cnt == 1) {
                        i3 = j;
                    } else if (cnt == 6) {
                        i2 = j + 1;
                    }
                }
            }
            cusUser = datum.substring(i1, i2 - 1).trim();
            id = datum.substring(1, i3).trim();
            takenIds.add(parseInt(id));

            if (cusUser.equals(username)) {
                messageLabel1.setText("Username is already taken");
            }
        }

        if (username.isBlank() || password.isBlank() || name.isBlank() ||
                address.isBlank() || phone.isBlank() || email.isBlank()) {
            messageLabel1.setText("Please fill all the fields");
            return;
        }

        if (password.length() < 8) {
            messageLabel1.setText("Password must be at least 8 characters");
            return;
        }

        if (!email.contains("@") || !email.contains(".")) {
            messageLabel1.setText("Invalid email format");
            return;
        }

        if (!phone.matches("\\d+")) {
            messageLabel1.setText("Phone number must contain only digits");
            return;
        }

        try {
            Customer customer = new Customer(username, password, name, address, phone, email);
            int cusId = customer.getCustomerId();
            for (int i = 0; i < takenIds.size(); ++i) {
                if (takenIds.get(i) == cusId) {
                    customer.setCustomerId(++cusId);
                } else break;
            }
            List<String> data = List.of(
                    String.valueOf(customer.getCustomerId()),
                    name, address, phone, email, username, password
            );

            saveData(data, fileName);
            switchToLogin(actionEvent);
        } catch (Exception e) {
            messageLabel1.setText("An error occurred. Please try again.");
            e.printStackTrace();
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

    @FXML
    private void switchToUser(ActionEvent actionEvent) throws IOException {
        switchScene("user_panel.fxml", actionEvent);
    }


    private void saveData(List<String> data, String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
        writer.write(data.toString() + '\n');
        writer.close();
    }

    private List<String> readData(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        List<String> data = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            data.add(line);
        }
        reader.close();
        return data;
    }
}