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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
    public BorderPane createContent(){
        BorderPane root = new BorderPane();
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
        Text passwordText = new Text("Password");

        TextField userName = new TextField();
        userName.setText("bhxnusingh0605@gmail.com");
        PasswordField password = new PasswordField();
        password.setText("passkey");

        loginPage = new GridPane();
        loginPage.setHgap(10);
        loginPage.setVgap(10);
        loginPage.setAlignment(Pos.CENTER);

        loginPage.add(userNameText, 0, 0);
        loginPage.add(userName, 1,0);
        loginPage.add(passwordText, 0, 1);
        loginPage.add(password, 1,1);

        Label messageLabel = new Label("Welcome to YourStore, Login to buy, Or feel free to browse!");
        loginPage.add(messageLabel, 1,5);

        Button login_button = new Button("Login");
        loginPage.add(login_button, 1,2);


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
                        cus.setPadding(new Insets(10));
                        headBar.getChildren().add(cus);
                    }
                    headBar.setVisible(true);
                    footBar.setVisible(true);
                    signOutButton.setVisible(true);
                    Home.setVisible(false);
                }
            }
        });
    }
    private void  createHeader(){
        headBar = new HBox();
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search");
        searchBar.setMaxWidth(40);
        searchBar.setMinWidth(20);

        signInButton = new Button("Sign In");
        signOutButton = new Button("Sign Out");
        signOutButton.setVisible(false);

        Button searchButton = new Button("Search");
        Button CartButton = new Button("Cart");

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
            }
        });

        signOutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                loggedInCustomer = null;
                body.getChildren().add(loginPage);
                signOutButton.setVisible(false);
                footBar.setVisible(false);
                cus.setVisible(false);
                Home.setVisible(true);
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
