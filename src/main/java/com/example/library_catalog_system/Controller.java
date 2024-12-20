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
    import javafx.scene.layout.VBox;
    import javafx.scene.text.Text;
    import javafx.stage.Stage;
    import javafx.util.Duration;

    import java.io.*;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Objects;

    import static java.lang.Integer.parseInt;

    public class Controller {
        @FXML
        private HBox box;
        @FXML
        private Button showBook;
        @FXML
        private TextField usernameField, passwordField,
                R_usernameField, R_passwordField, R_name, R_address, R_phone, R_email,
                bookTitle, bookPages, bookPrice, bookYear, bookCopies,
                authorName, authorSurname, authorPhone, authorEmail,
                updatebookid, updatebookprice, updatebookcopies,
                removebookid, removeadminpassword, removeborrowerid,
                borrowerName, borrowerAddress, borrowerEmail, borrowerPhone,
                updateborrowerid, updateborrowerphone, updateborroweremail, updateborroweraddress,
                borrowerID, borrowedBookTitle, borrowedBookDays,
                customerUsername, customerPassword, customerBookTitle, customerBookQuantity,
                reviewBookNameField, reviewRatingField, reviewTextField,search_Field, viewReviewsBookNameField,
                cartUpdateI, cartUpdateQ,
                buyingUsername, buyingPassword, creditNum, cvv;
        @FXML
        private Labeled messageLabel;

        @FXML
        private TextArea reviewsDisplayArea, searchResultsArea, Cart, customerHistory, bookInventory, borrowerHistory, bookView;

        static String loginUsername = "", loginPassword = "", loginId = "";

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        Library library = new Library("Night Library", "Cairo");

        Cart cart = new Cart();

        Order order = new Order();

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

            if (username.isBlank() || password.isBlank()) {
                messageLabel.setText("Please fill all the fields");
                return;
            }

            if ("admin".equals(username) && "admin".equals(password)) {
                switchToAdmin(actionEvent);
            } else {
                library.loadCustomersFromFile();
                loginUsername = "";
                loginPassword = "";
                loginId = "";
                for (Customer customer : library.getCustomers()) {
                    if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                        loginUsername += username;
                        loginPassword += password;
                        loginId += Integer.toString(customer.getCustomerId());
                        messageLabel.setText("");
                        alert.setTitle("Login");
                        alert.setHeaderText("Login done successfully");
                        alert.showAndWait();
                        switchToUser(actionEvent);
                        break;
                    } else {
                        messageLabel.setText("Incorrect username or password");
                    }
                }
            }
        }

        @FXML
        private void handleRegister(ActionEvent actionEvent) {
            String username = R_usernameField.getText();
            String password = R_passwordField.getText();
            String name = R_name.getText();
            String address = R_address.getText();
            String phone = R_phone.getText();
            String email = R_email.getText();


            if (username.isBlank() || password.isBlank() || name.isBlank() ||
                    address.isBlank() || phone.isBlank() || email.isBlank()) {
                messageLabel.setText("Please fill all the fields");
                return;
            }
            if (password.length() < 8) {
                messageLabel.setText("Password must be at least 8 characters");
                return;
            }
            if (!email.contains("@") || !email.contains(".")) {
                messageLabel.setText("Invalid email format");
                return;
            }
            if (!phone.matches("\\d+")) {
                messageLabel.setText("Phone number must contain only digits");
                return;
            }

            library.loadCustomersFromFile();
            for (int i = 0; i < library.getCustomers().size(); i++) {
                if (library.getCustomers().get(i).getUsername().equals(username)) {
                    messageLabel.setText("Username is already taken");
                    return;
                }
            }

            try {
                Customer customer = new Customer(name, address, phone, email, username, password);
                library.registerCustomer(customer);
                alert.setTitle("Register");
                alert.setHeaderText("Registering done successfully");
                alert.showAndWait();
                switchToLogin(actionEvent);
            } catch (Exception e) {
                messageLabel.setText("An error occurred. Please try again.");
            }
        }

        @FXML
        private void handleSearch() {
            String query = search_Field.getText();

            if (query.isEmpty()) {
                messageLabel.setText("Please enter Book You want to search.");
                return;
            }

            // Search for books
            library.loadBooksFromFile();
            List<Book> results = library.Search_book(query);

            // Display results
            if (results.size() == 1 && results.get(0).getNumOfCopies() > 0) {
                Book book = results.get(0);
                searchResultsArea.setText("Found Book:\n" +
                        "Title: " + book.getTitle() + "\n" +
                        "Author: " + book.getAuthor().getName() + "\n" +
                        "Price: $" + book.getPrice() + "\n" +
                        "Year: " + book.getPublicationYear() + "\n" +
                        "Available Copies: " + book.getNumOfCopies());
            } else {
                StringBuilder recommendations = new StringBuilder("The book is not available. Here are some recommendations:\n");
                for (Book book : results) {
                    recommendations.append("- ").append(book.getTitle()).append(" by ").append(book.getAuthor().getName()).append("\n");
                }
                searchResultsArea.setText(recommendations.toString());
            }
        }

        @FXML
        private void addBooks(ActionEvent actionEvent) throws IOException {
            String authorname = authorName.getText();
            String authorsurname = authorSurname.getText();
            String authorphone = authorPhone.getText();
            String authoremail = authorEmail.getText();
            String booktitle = bookTitle.getText();
            String pages = bookPages.getText();
            String price = bookPrice.getText();
            String year = bookYear.getText();
            String copies = bookCopies.getText();

            if (authoremail.isBlank() || authorname.isBlank() || authorphone.isBlank() ||
                    authorsurname.isBlank() || booktitle.isBlank() || pages.isBlank() ||
                    price.isBlank() || year.isBlank() || copies.isBlank()) {
                messageLabel.setText("Please fill all the fields");
                return;
            }
            if (!authoremail.contains("@") || !authoremail.contains(".")) {
                messageLabel.setText("Invalid email format");
                return;
            }
            if (!authorphone.matches("\\d+")) {
                messageLabel.setText("Phone number must contain only digits");
                return;
            }
            if (!pages.matches("\\d+")) {
                messageLabel.setText("Phone number must contain only digits");
                return;
            }
            if (!price.matches("\\d+(\\.\\d+)?")) {
                messageLabel.setText("Phone number must contain only digits");
                return;
            }
            if (!year.matches("\\d+")) {
                messageLabel.setText("Phone number must contain only digits");
                return;
            }
            if (!copies.matches("\\d+")) {
                messageLabel.setText("Phone number must contain only digits");
                return;
            }
            int bookpages = Integer.parseInt(pages);
            float bookprice = Float.parseFloat(price);
            int bookyear = Integer.parseInt(year);
            int bookcopies = Integer.parseInt(copies);
            Author author = new Author(authorname, authorsurname, authorphone, authoremail);
            Book book = new Book(booktitle, bookpages, bookcopies, bookprice, bookyear, author);
            library.loadBooksFromFile();
            library.addbook(book);
            alert.setTitle("Add Book");
            alert.setHeaderText("Book added successfully");
            alert.showAndWait();
            adminBooks(actionEvent);
        }

        @FXML
        private void updateBooks(ActionEvent actionEvent) throws IOException {
            String Id = updatebookid.getText();
            String price = updatebookprice.getText();
            String copies = updatebookcopies.getText();
            library.loadBooksFromFile();

            if (Id.isBlank()) {
                messageLabel.setText("Please add Book ID");
                return;
            }
            if (copies.isBlank() && price.isBlank()) {
                messageLabel.setText("Please add new Book Price or new Number of Copies");
                return;
            }
            if (copies.isBlank()) {
                int bookId = Integer.parseInt(Id);
                float bookprice = Float.parseFloat(price);
                library.updatebooks(bookId, bookprice);
                alert.setTitle("Update Book");
                alert.setHeaderText("Book updated successfully");
                alert.showAndWait();
                adminBooks(actionEvent);
            }
            if (price.isBlank()) {
                int bookId = Integer.parseInt(Id);
                int bookcopies = Integer.parseInt(copies);
                library.updatebooks(bookId, bookcopies);
                alert.setTitle("Update Book");
                alert.setContentText("Book updated successfully");
                alert.showAndWait();
                adminBooks(actionEvent);
            }
            if (!Id.isBlank() && !copies.isBlank() && !price.isBlank()) {
                int bookId = Integer.parseInt(Id);
                float bookprice = Float.parseFloat(price);
                int bookcopies = Integer.parseInt(copies);
                library.updatebooks(bookId, bookprice, bookcopies);
                alert.setTitle("Update Book");
                alert.setContentText("Book updated successfully");
                alert.showAndWait();
                adminBooks(actionEvent);
            }
        }

        @FXML
        private void removeBooks(ActionEvent actionEvent) throws IOException {
            String bookId = removebookid.getText();
            String password = removeadminpassword.getText();
            library.loadBooksFromFile();

            if (bookId.isBlank()) {
                messageLabel.setText("Please add Book ID");
                return;
            }
            if (password.isBlank()) {
                messageLabel.setText("Please add Admin Password");
                return;
            }
            if (password.equals("admin")) {
                library.removebook(parseInt(bookId));
                alert.setTitle("Remove Book");
                alert.setHeaderText("Book removed successfully");
                alert.showAndWait();
                adminBooks(actionEvent);
            }
        }

        @FXML
        private void addBorrower(ActionEvent actionEvent) throws IOException {
            String name = borrowerName.getText();
            String address = borrowerAddress.getText();
            String phone = borrowerPhone.getText();
            String email = borrowerEmail.getText();

            if (name.isBlank() || address.isBlank() || phone.isBlank() ||
                    email.isBlank()) {
                messageLabel.setText("Please fill all the fields");
                return;
            }
            if (!email.contains("@") || !email.contains(".")) {
                messageLabel.setText("Invalid email format");
                return;
            }
            if (!phone.matches("\\d+")) {
                messageLabel.setText("Phone number must contain only digits");
                return;
            }

            library.loadBorrowersFromFile();
            Borrower borrower = new Borrower(name, address, phone, email);
            library.addBorrower(borrower);
            alert.setTitle("Add Borrower");
            alert.setHeaderText("Borrower added successfully");
            alert.showAndWait();
            adminBorrowers(actionEvent);

        }

        @FXML
        private void updateBorrower(ActionEvent actionEvent) throws IOException {
            String Id = updateborrowerid.getText();
            String address = updateborroweraddress.getText();
            String phone = updateborrowerphone.getText();
            String email = updateborroweremail.getText();

            if (Id.isBlank()) {
                messageLabel.setText("Please add Borrower ID");
                return;
            }
            if (address.isBlank() && phone.isBlank() && email.isBlank()) {
                messageLabel.setText("Please fill at least one field");
                return;
            }

            library.loadBorrowersFromFile();
            library.updateBorrower(parseInt(Id), email, address, phone);
            alert.setTitle("Update Borrower");
            alert.setHeaderText("Borrower updated successfully");
            alert.showAndWait();
            adminBorrowers(actionEvent);
        }

        @FXML
        private void removeBorrower(ActionEvent actionEvent) throws IOException {
            String Id = removeborrowerid.getText();
            String password = removeadminpassword.getText();

            if (Id.isBlank()) {
                messageLabel.setText("Please add Book ID");
                return;
            }
            if (password.isBlank()) {
                messageLabel.setText("Please add Admin Password");
                return;
            }

            library.loadBorrowersFromFile();
            if (password.equals("admin")) {
                library.RemoveBorrower(parseInt(Id));
                alert.setTitle("Remove Borrower");
                alert.setHeaderText("Borrower removed successfully");
                alert.showAndWait();
                adminBorrowers(actionEvent);
            }
        }

        @FXML
        private void borrowBook(ActionEvent actionEvent) throws IOException {
            String borrowerId = borrowerID.getText();
            String title = borrowedBookTitle.getText();
            String borrowDays = borrowedBookDays.getText();

            if (title.isBlank() || borrowDays.isBlank() || borrowerId.isBlank()) {
                messageLabel.setText("Please fill all the fields");
                return;
            }
            if (!borrowerId.matches("\\d+")) {
                messageLabel.setText("Please add valid Book ID");
                return;
            }
            if (!borrowDays.matches("\\d+")) {
                messageLabel.setText("Please add valid number of days");
                return;
            }

            int id = Integer.parseInt(borrowerId);
            int days = Integer.parseInt(borrowDays);
            library.loadBooksFromFile();
            library.loadBorrowersFromFile();
            for (Borrower borrower : library.getBorrowers()) {
                if (id == borrower.getBorrowerId()) {
                    for (Book book : library.getBooks()) {
                        if (book.getTitle().equals(title)) {
                            library.borrowBook(borrower, title, days);
                            alert.setTitle("Borrow Book");
                            alert.setHeaderText("Book borrowed successfully");
                            alert.showAndWait();
                            adminBorrowers(actionEvent);
                            return;
                        }
                    }
                }
            }
        }

        @FXML
        private void borrowerHistory() {
            String borrowedId = borrowerID.getText();
            if (borrowedId.isBlank()) {
                messageLabel.setText("Please add Borrower ID");
                return;
            }
            int id = parseInt(borrowedId);
            library.loadBorrowersFromFile();
            for (Borrower borrower : library.getBorrowers()) {
                if (borrower.getBorrowerId() == id) {
                    List<String> data;
                    data = borrower.viewBorrowingHistory();
                    if (data == null) {
                        borrowerHistory.setText("There is no old borrowing for this id");
                        return;
                    }
                    for (String datum : data) {
                        borrowerHistory.appendText(datum + "\n");
                    }
                } else messageLabel.setText("Borrower is not found");
            }
        }

        @FXML
        private void libraryInventory() {
            library.loadBooksFromFile();
            if (library.getBooks() == null || library.getBooks().isEmpty()) {
                bookInventory.appendText("There is no books in the library");
                return;
            }
            for (int i = 0; i < library.getBooks().size(); i++) {
                bookInventory.appendText("Book: " + library.getBooks().get(i).getTitle() + " - Available Copies: " + library.getBooks().get(i).numOfCopies + "\n");
            }
            showBook.setVisible(false);
        }

        @FXML
        private void userBookView() {
            library.loadBooksFromFile();
            List<Book> books = library.getBooks();
            for (Book book : books) {
                bookView.appendText(book.getTitle() + " - Available copies: " + book.numOfCopies);
            }
            showBook.setVisible(false);
        }

        @FXML
        private void addToCart(ActionEvent actionEvent) throws IOException {
            String username = customerUsername.getText();
            String password = customerPassword.getText();
            String title = customerBookTitle.getText();
            String bookQun = customerBookQuantity.getText();

            if (title.isBlank() || username.isBlank() || password.isBlank() || bookQun.isBlank()) {
                messageLabel.setText("Please fill all the fields");
                return;
            }
            if (!bookQun.matches("\\d+")) {
                messageLabel.setText("Please add valid Book Quantity");
                return;
            }

            library.loadCustomersFromFile();
            for (Customer customer : library.getCustomers()) {
                if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                    messageLabel.setText("");
                    int quantity = Integer.parseInt(bookQun);
                    library.loadBooksFromFile();
                    for (Book book : library.getBooks()) {
                        if (book.getTitle().equals(title)) {
                            cart.LoadBooksFromFile(loginId);
                            cart.addItems(book, quantity, library, Integer.toString(customer.getCustomerId()));
                            alert.setTitle("Add To Cart");
                            alert.setHeaderText("Book added successfully");
                            alert.showAndWait();
                            userView(actionEvent);
                            return;
                        }
                    }
                } else messageLabel.setText("Username or Password is incorrect");
            }

        }

        @FXML
        private void showCart() {
            library.loadBooksFromFile();
            cart.LoadBooksFromFile(loginId);
            if (cart.Cbooks == null || cart.Cbooks.isEmpty()) {
                Cart.appendText("There is no books in the cart");
                return;
            }
            for (int i = 0; i < cart.Cbooks.size(); i++) {
                Cart.appendText(cart.Cbooks.toString());
            }
        }

        @FXML
        private void updateCart(ActionEvent actionEvent) throws IOException {
            String idU = cartUpdateI.getText();
            String quantityU = cartUpdateQ.getText();

            if (idU.isBlank() || quantityU.isBlank()) {
                messageLabel.setText("Please fill all the fields");
                return;
            }
            if (!idU.matches("\\d+")) {
                messageLabel.setText("Please add valid Book ID");
                return;
            }
            if (!quantityU.matches("\\d+")) {
                messageLabel.setText("Please add valid Book Quantity");
                return;
            }

            int id = parseInt(idU);
            int quantity = parseInt(quantityU);
            library.loadBooksFromFile();
            cart.LoadBooksFromFile(loginId);
            if (cart.Cbooks.isEmpty()) {
                messageLabel.setText("There is no books with this ID in the cart");
                return;
            }
            cart.updateItemQuantity(id, quantity, library, loginId);
            alert.setTitle("Update Quantity");
            alert.setHeaderText("Item updated successfully");
            alert.showAndWait();
            customerCart(actionEvent);
        }

        @FXML
        private void deleteCart(ActionEvent actionEvent) throws IOException {
            String idU = cartUpdateI.getText();

            if (idU.isBlank() || !idU.matches("\\d+")) {
                messageLabel.setText("Please add valid Book ID");
                return;
            }

            int id = parseInt(idU);
            library.loadBooksFromFile();
            cart.LoadBooksFromFile(loginId);
            if (cart.Cbooks.isEmpty()) {
                messageLabel.setText("There is no books with this ID in the cart");
                return;
            }
            cart.deleteItem(id, library, loginId);
            alert.setTitle("Delete Item");
            alert.setHeaderText("Item deleted successfully");
            alert.showAndWait();
            customerCart(actionEvent);
        }

        @FXML
        private void handleBorrow(ActionEvent actionEvent) throws IOException {
            String username = customerUsername.getText();
            String password = customerPassword.getText();
            String bookTitle = customerBookTitle.getText();
            String borrowDays = borrowedBookDays.getText();

            if (username.isBlank() || borrowDays.isBlank() || password.isBlank()) {
                messageLabel.setText("Please fill all the fields");
                return;
            }
            if (!borrowDays.matches("\\d+")) {
                messageLabel.setText("Please add valid number of days");
                return;
            }
            if (!username.equals(loginUsername) && password.equals(loginPassword)) {
                messageLabel.setText("Wrong username or password");
                return;
            }

            library.loadCustomersFromFile();
            for (Customer customer : library.getCustomers()) {
                if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                    messageLabel.setText("");
                    int days = Integer.parseInt(borrowDays);
                    library.loadBooksFromFile();
                    for (Book book : library.getBooks()) {
                        if (book.getTitle().equals(bookTitle)) {
                            library.customerBorrowBook(customer, bookTitle, days);
                            alert.setTitle("Borrow Book");
                            alert.setHeaderText("Book borrowed successfully");
                            alert.showAndWait();
                            userView(actionEvent);
                            return;
                        }
                    }
                } else messageLabel.setText("Username or Password is incorrect");
            }
        }

        @FXML
        private void addReview(ActionEvent actionEvent) throws IOException{
            String bookNameText = reviewBookNameField.getText();
            String ratingText = reviewRatingField.getText();
            String reviewText = reviewTextField.getText();

            if (bookNameText.isBlank() || ratingText.isBlank() || reviewText.isBlank()) {
                messageLabel.setText("Please fill all the fields.");
                return;
            }
            if (!ratingText.matches("\\d+")) {
                messageLabel.setText("rating must be numeric.");
                return;
            }

            int rating = Integer.parseInt(ratingText);

            if (rating < 1 || rating > 5) {
                messageLabel.setText("Rating must be between 1 and 5.");
                return;
            }
            library.loadBooksFromFile();
            library.addReview(bookNameText, reviewText, rating);
            alert.setHeaderText("Review added successfully!");
            alert.showAndWait();
            switchToReviews(actionEvent);
            messageLabel.setText("Review added successfully!");
            reviewBookNameField.clear();
            reviewRatingField.clear();
            reviewTextField.clear();
        }

        @FXML
        private void viewBookReviews() {
            String bookNameText = viewReviewsBookNameField.getText();

            if (bookNameText.isBlank()) {
                messageLabel.setText("Please enter a book title.");
                return;
            }
            library.loadBooksFromFile();
            for (Book book : library.getBooks()) {
                if (book.getTitle().equalsIgnoreCase(bookNameText)) {
                    List<String> reviews;
                    reviews = book.showAllRatingsAndReviews();
                    if (reviews.isEmpty() || reviews == null) {
                        reviewsDisplayArea.appendText("No reviews yet.\n");
                        showBook.setVisible(false);
                        return;
                    }
                    for (String datum : reviews) {
                        reviewsDisplayArea.appendText(datum + "\n");
                    }
                    showBook.setVisible(false);
                    return;
                }
            }
            messageLabel.setText("Book not found.");
        }

        @FXML
        private void Cash(ActionEvent actionEvent) throws IOException {
            String username = buyingUsername.getText();
            String password = buyingPassword.getText();
            System.out.println(loginUsername + " " + loginPassword);

            if (username.isBlank() || password.isBlank()) {
                messageLabel.setText("Please fill all the fields.");
                return;
            }
            if (!username.equals(loginUsername) && !password.equals(loginPassword)) {
                messageLabel.setText("Wrong username or password");
                return;
            }

            library.loadBooksFromFile();
            cart.LoadBooksFromFile(loginId);
            order.loadOrdersFromFile(loginId);
            order.orderBooks = cart.Cbooks;
            order.saveBooksInOrder(loginId);
            alert.setTitle("Ordering Transaction");
            alert.setHeaderText("Order added successfully!");
            alert.setContentText("Total Price: " + order.totalPrice());
            alert.showAndWait();
            switchToUser(actionEvent);
        }

        @FXML
        private void Credit(ActionEvent actionEvent) throws IOException {
            String username = buyingUsername.getText();
            String password = buyingPassword.getText();
            String credit = creditNum.getText();
            String cvvn = cvv.getText();

            if (username.isBlank() || password.isBlank() || credit.isBlank() || cvvn.isBlank()) {
                messageLabel.setText("Please fill all the fields.");
                return;
            }
            if (!username.equals(loginUsername) && !password.equals(loginPassword)) {
                messageLabel.setText("Wrong username or password");
                return;
            }
            if (!credit.matches("\\d+") || !cvvn.matches("\\d+")) {
                messageLabel.setText("Please add valid credit card");
                return;
            }
            if (cvvn.length() != 3) {
                messageLabel.setText("Please add valid cvv");
                return;
            }

            library.loadBooksFromFile();
            cart.LoadBooksFromFile(loginId);
            order.loadOrdersFromFile(loginId);
            order.orderBooks = cart.Cbooks;
            order.saveBooksInOrder(loginId);
            cart.Cbooks.clear();
            cart.clearFile(loginId);
            alert.setTitle("Ordering Transaction");
            alert.setHeaderText("Order added successfully!");
            alert.setContentText("Total Price: " + order.totalPrice());
            alert.showAndWait();
            switchToUser(actionEvent);
        }

        @FXML
        private void customerBorrowings() {
            library.loadCustomersFromFile();
            List<String> customers = new ArrayList<>();
            for (Customer customer : library.getCustomers()) {
                if (customer.getUsername().equals(loginUsername) && customer.getPassword().equals(loginPassword)) {
                    customers = customer.viewBorrowingHistory();
                    break;
                }
            }
            if (customers == null){
                customerHistory.appendText("No History Yet");
                return;
            }
            for(String data : customers) {
                customerHistory.appendText(data + "\n");;
            }
            showBook.setVisible(false);
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
        private void backToLogIn(ActionEvent actionEvent) throws IOException {
            switchScene("login.fxml", actionEvent);
        }

        @FXML
        private void adminBooks(ActionEvent actionEvent) throws IOException {
            switchScene("admin_books.fxml", actionEvent);
        }

        @FXML
        private void adminAddBook(ActionEvent actionEvent) throws IOException {
            switchScene("admin_add_book.fxml", actionEvent);
        }

        @FXML
        private void adminUpdateBook(ActionEvent actionEvent) throws IOException {
            switchScene("admin_update_book.fxml", actionEvent);
        }

        @FXML
        private void adminRemoveBook(ActionEvent actionEvent) throws IOException {
            switchScene("admin_remove_book.fxml", actionEvent);
        }

        @FXML
        private void adminBorrowers(ActionEvent actionEvent) throws IOException {
            switchScene("admin_borrowers.fxml", actionEvent);
        }

        @FXML
        private void adminAddBorrower(ActionEvent actionEvent) throws IOException {
            switchScene("admin_add_borrowers.fxml", actionEvent);
        }

        @FXML
        private void adminUpdateBorrower(ActionEvent actionEvent) throws IOException {
            switchScene("admin_update_borrowers.fxml", actionEvent);
        }

        @FXML
        private void adminRemoveBorrower(ActionEvent actionEvent) throws IOException {
            switchScene("admin_remove_borrowers.fxml", actionEvent);
        }

        @FXML
        private void adminBorrowerView(ActionEvent actionEvent) throws IOException {
            switchScene("admin_borrowers_view.fxml", actionEvent);
        }

        @FXML
        private void Search(ActionEvent actionEvent) throws IOException {
            switchScene("customer_Search.fxml", actionEvent);
        }

        @FXML
        private void adminBorrowerAdd(ActionEvent actionEvent) throws IOException {
            switchScene("admin_borrowers_add.fxml", actionEvent);
        }

        @FXML
        private void adminBorrowerHistory(ActionEvent actionEvent) throws IOException {
            switchScene("admin_borrowers_history.fxml", actionEvent);
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

        @FXML
        private void switchToReviews(ActionEvent actionEvent) throws IOException {
            switchScene("customer_ratings_reviews.fxml", actionEvent);
        }

        @FXML
        private void userView(ActionEvent actionEvent) throws IOException {
            switchScene("user_book_view.fxml", actionEvent);
        }

        @FXML
        private void customerShowBooks(ActionEvent actionEvent) throws IOException {
            switchScene("customer_show_books.fxml", actionEvent);
        }

        @FXML
        private void Reviews(ActionEvent actionEvent) throws IOException{
            switchScene("customer_ratings_reviews.fxml",actionEvent);
        }

        @FXML
        private void add_review(ActionEvent actionEvent) throws IOException{
            switchScene("customer_add_review.fxml",actionEvent);
        }

        @FXML
        private void view_review(ActionEvent actionEvent) throws IOException{
            switchScene("customer_show_review.fxml",actionEvent);
        }

        @FXML
        private void customerBorrow(ActionEvent actionEvent) throws IOException {
            switchScene("customer_borrow_book.fxml", actionEvent);
        }

        @FXML
        private void customerBorrowHistory(ActionEvent actionEvent) throws IOException {
            switchScene("customer_borrow_history.fxml", actionEvent);
        }

        @FXML
        private void customerAddToCart(ActionEvent actionEvent) throws IOException {
            switchScene("customer_buy_book.fxml", actionEvent);
        }

        @FXML
        private void customerCart(ActionEvent actionEvent) throws IOException {
            switchScene("customer_cart.fxml", actionEvent);
        }

        @FXML
        private void customerCartUpdate(ActionEvent actionEvent) throws IOException {
            switchScene("customer_cart_update_quantity.fxml", actionEvent);
        }

        @FXML
        private void customerCartDelete(ActionEvent actionEvent) throws IOException {
            switchScene("customer_cart_delete.fxml", actionEvent);
        }

        @FXML
        private void customerBuying(ActionEvent actionEvent) throws IOException {
            switchScene("customer_confirm_buying.fxml", actionEvent);
        }

        @FXML
        private void customerBuyingCash(ActionEvent actionEvent) throws IOException {
            switchScene("customer_cash.fxml", actionEvent);
        }

        @FXML
        private void customerBuyingCredit(ActionEvent actionEvent) throws IOException {
            switchScene("customer_credit.fxml", actionEvent);
        }

    }