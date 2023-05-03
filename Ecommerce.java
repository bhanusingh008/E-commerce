package com.e_commerce.ecommerce;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Ecommerce extends Application {

    UserInterface userInterface = new UserInterface();

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(userInterface.createContent());
        stage.setTitle("Ecommerce");
        stage.setScene(scene);
        Image icon = new Image("ecom.png");
        stage.getIcons().add(icon);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
