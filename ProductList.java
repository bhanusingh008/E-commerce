package com.e_commerce.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class ProductList {
     TableView<Product> productTable;

     TableView<Product> cartTable;

    public VBox createTable(ObservableList<Product> data){
     // Columns
        TableColumn id = new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name = new TableColumn("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price = new TableColumn("Price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTable = new TableView<>();
        productTable.getColumns().addAll(id, name,price);
        productTable.setItems(data);
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(productTable);
        vbox.setPadding(new Insets(10));
        return vbox;
    }

    public VBox createCartTable(ObservableList<Product> data){
        TableColumn id = new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name = new TableColumn("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price = new TableColumn("Price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        cartTable = new TableView<>();
        cartTable.getColumns().addAll(id, name,price);
        cartTable.setItems(data);
        cartTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(cartTable);
        vbox.setPadding(new Insets(10));
        return vbox;
    }

    public VBox getAllProducts(){
        ObservableList<Product> data = Product.getAllProduct();
        VBox vBox = createTable(data);
        return vBox;
    }

    public Product getSelectedProduct(){
        return productTable.getSelectionModel().getSelectedItem();
    }

    public VBox getProductsInCart(ObservableList<Product> data){
        return createCartTable(data);
    }
}