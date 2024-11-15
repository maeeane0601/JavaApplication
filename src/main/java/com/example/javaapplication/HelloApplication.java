package com.example.javaapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        var fxml = HelloApplication.class.getResource("login.fxml");
        System.out.println("Loaded FXML File: "+ fxml);
        FXMLLoader fxmlLoader = new FXMLLoader(fxml); // Adjust the FXML file name and path
        Scene scene = new Scene(fxmlLoader.load(), 600, 400); // Set the size of your window
        stage.setTitle("Login Application");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        // Test database connection
        Database.getConnection(); // <-- This line will print the database connection status
        launch();
    }
}
