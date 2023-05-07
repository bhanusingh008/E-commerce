package com.e_commerce.ecommerce;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Callable;

public class UserInterface {
    public UserInterface() { // make sure to create constructor, and run needed methods within it.
        createLoginPage();
        createHeader();
        createFootBar();
    }

    GridPane loginPage;
    HBox headBar;
    HBox footBar;
    Button signInButton;
    Button signOutButton;
    VBox body;
    customer loggedInCustomer;
    ProductList productList = new ProductList();

    Button Home = new Button();

    Button placeOrder = new Button("Place Order");

    ObservableList<Product> itemsInCart = FXCollections.observableArrayList();
    VBox productPage;

    Label cus;

    Label curr_position = new Label("You're on Login Page");
    public BorderPane createContent(){
        BorderPane root = new BorderPane();
        Image img = new Image("C:\\Users\\Bhanu\\IdeaProjects\\E-commerce\\src\\main\\resources\\background.jpg");
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(2000);
        imageView.setFitHeight(1000);
        root.getChildren().add(imageView);
        //root.setStyle("-fx-border-color:black");
        root.setPrefSize(600, 600);
        //root.getChildren().add(loginPage); // method to add the child node to parent
        root.setCenter(loginPage);
        root.setTop(headBar);
        root.setBottom(footBar);
        productPage = productList.getAllProducts();
        body = new VBox();
        body.setAlignment(Pos.CENTER);
        body.getChildren().add(productPage);
        body.setPadding(new Insets(10));

        root.setCenter(body);
//        root.setCenter(productTable);
        return root;
    }
    private void createLoginPage() {
        Text userNameText = new Text("User Name");
        userNameText.setFill(Color.WHITE);
        Text passwordText = new Text("Password");
        passwordText.setFill(Color.WHITE);

        TextField userName = new TextField();
        userName.setText("abhaysingh@gmail.com");
        PasswordField password = new PasswordField();
        password.setText("abhaysingh");

        loginPage = new GridPane();
        loginPage.setHgap(10);
        loginPage.setVgap(10);
        loginPage.setAlignment(Pos.CENTER);

        loginPage.add(userNameText, 0, 1);
        loginPage.add(userName, 1,1);
        loginPage.add(passwordText, 0, 2);
        loginPage.add(password, 1,2);

        ImageView img = new ImageView("C:\\Users\\Bhanu\\IdeaProjects\\E-commerce\\src\\main\\resources\\profile.png");
        img.setFitHeight(50);
        img.setFitWidth(50);
        img.setTranslateX(175);
        img.setTranslateY(-50);

        loginPage.addRow(0,img);

        Label messageLabel = new Label("Welcome to YourStore, Login to buy, Or feel free to browse!");
        messageLabel.setTextFill(Color.WHITE);
        loginPage.add(messageLabel, 1,5);

        Button login_button = new Button("Login");
        loginPage.add(login_button, 1,3);


        login_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = userName.getText();
                String pass = password.getText();

                Login login = new Login();
                loggedInCustomer = login.customerLogin(name, pass);
                if(loggedInCustomer == null){
                    messageLabel.setText("Invalid Email or Password, Please try again.");
                }
                else{
                    body.getChildren().clear();
                    body.getChildren().add(productPage);
                    if(!headBar.getChildren().contains(cus)){
                        cus = new Label("Welcome-"+loggedInCustomer.getName());
                        cus.setTextFill(Color.WHITE);
                        cus.setMinWidth(70);
                        cus.setPadding(new Insets(10));
                        curr_position.setText("You're on Home Page");
                        curr_position.setTextFill(Color.WHITE);
                        curr_position.setMinWidth(100);
                        curr_position.setPadding(new Insets(10));
                        curr_position.setStyle("-fx-border-color: white;");
                        headBar.getChildren().addAll(cus, curr_position);
                    }
                    headBar.setVisible(true);
                    footBar.setVisible(true);
                    signOutButton.setVisible(true);
                    Home.setVisible(false);

                    curr_position.setText("You're on Home Page");
                }
            }
        });
    }
    private void  createHeader(){
        headBar = new HBox();
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search");
        searchBar.setLayoutX(20);
        searchBar.setLayoutY(30);

        signInButton = new Button("Sign In");
        signOutButton = new Button("Sign Out");
        signOutButton.setVisible(false);

        Button searchButton = new Button("Search");
        Button CartButton = new Button("Cart");
        CartButton.setMinWidth(50);
        searchButton.setMinWidth(70);

        headBar.setPadding(new Insets(10));
        headBar.setSpacing(10);
        //headBar.setAlignment(Pos.CENTER);

        headBar.getChildren().addAll(searchBar, searchButton, signInButton, CartButton, signOutButton, Home);
        Home.setVisible(false);

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().add(loginPage);
                signInButton.setVisible(false);
                footBar.setVisible(false);
                Home.setVisible(true);

                curr_position.setText("You're on Login Page");
            }

        });

        signOutButton.setMinWidth(70);

        signOutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // creating an alert for sign out.
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Sign Out?");
                alert.setHeaderText("You're about to sign out");
                alert.setContentText("Do you want to continue?");
                // that is done
                // adding icon to the dialog
                Image img = new Image("ecom.png");
                Stage alertWin = (Stage) alert.getDialogPane().getScene().getWindow();
                alertWin.getIcons().add(img);
                // that is done
                // If user choose OK
                if(alert.showAndWait().get() == ButtonType.OK){
                body.getChildren().clear();
                loggedInCustomer = null;
                body.getChildren().add(loginPage);
                signOutButton.setVisible(false);
                footBar.setVisible(false);
                cus.setVisible(false);
                Home.setVisible(true);
                }

                curr_position.setText("You're on Login Page");
            }
        });

        CartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(loggedInCustomer == null){
                    showDialog("Login First to view your Cart!");
                    return;
                }
                body.getChildren().clear();
                VBox CartList = productList.getProductsInCart(itemsInCart);
                CartList.setPadding(new Insets(10));
                CartList.setSpacing(10);
                body.getChildren().addAll(CartList, placeOrder);
                Home.setVisible(true);
                footBar.setVisible(false);
                CartButton.setVisible(false);
                curr_position.setText("You're on Cart Page");

            }
        });

        placeOrder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // need list of products and a customer.
                if(loggedInCustomer == null){
                    showDialog("Please Login First in order to place order");
                    return;
                }
                if(itemsInCart == null){
                    showDialog("Please add Some Products in the cart to place order");
                    return;
                }

                int prodCount = Order.placeMultipleOrders(loggedInCustomer, itemsInCart);

                showDialog(prodCount+" Products are placed successfully");

                itemsInCart.clear();
            }
        });

        Home.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().addAll(productPage);
                CartButton.setVisible(true);
                footBar.setVisible(true);
                headBar.setVisible(true);
                Home.setVisible(false);
                if(loggedInCustomer == null){
                    signInButton.setVisible(true);
                }

                curr_position.setText("You're on Home Page");
            }
        });
    }
    private void createFootBar(){
        footBar = new HBox();

        Button buyNow = new Button("Buy Now");
        Button addToCart = new Button("Add to Cart");
        Image image = new Image("C:\\Users\\Bhanu\\IdeaProjects\\E-commerce\\src\\main\\resources\\pngegg.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(30);
        Home.setGraphic(imageView);

        footBar.setPadding(new Insets(10));
        footBar.setSpacing(10);
        footBar.setAlignment(Pos.CENTER);

        footBar.getChildren().addAll(buyNow, addToCart);
        buyNow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product curr_product = productList.getSelectedProduct();
                if(loggedInCustomer == null){
                    showDialog("Please Login First in order to place order");
                    return;
                }
                if(curr_product == null){
                    showDialog("No Product is selected, Please select to buy.");
                    return;
                }
                boolean status = Order.placeOrder(loggedInCustomer, curr_product);
                if(status){
                    showDialog("Order Placed Successfully.");
                }
                else{
                    showDialog("Order Could not be Placed.");
                }
            }
        });

        addToCart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product curr_product = productList.getSelectedProduct();
                if(loggedInCustomer == null){
                    showDialog("Please Login First in order to place order");
                    return;
                }
                if(curr_product == null){
                    showDialog("Please select a product first to add to Cart!");
                    return;
                }
                itemsInCart.add(curr_product);
                showDialog(curr_product.getName()+" is added to your Cart!");
            }
        });
    }
    private void showDialog(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Message");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
