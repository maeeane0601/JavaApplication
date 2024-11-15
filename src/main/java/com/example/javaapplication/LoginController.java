package com.example.javaapplication;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSignup;

    // Method to handle login button click
    @FXML
    private void handleLogin() {
        String username = txtUserName.getText();
        String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter both username and password.");
        } else {
            String userRole = validateLogin(username, password);
            if (userRole != null) {
                showAlert("Success", "Login successful!");
                openHomeInterface(userRole);
            } else {
                showAlert("Error", "Invalid username or password.");
            }
        }
    }

    // Method to handle signup button click
    @FXML
    private void handleSignup() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Signup.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) btnSignup.getScene().getWindow();  // Get the current stage
            stage.setScene(scene);
            stage.setTitle("Sign Up");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to open signup interface.");
        }
    }

    // Redirects to the Home interface and passes the role
    private void openHomeInterface(String userRole) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Homepage.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            // Get the HomeController and set the role
            HomeController homeController = fxmlLoader.getController();
            homeController.setUserRole(userRole);

            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Home - User Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to open home interface.");
        }
    }

    // In LoginController
    private String validateLogin(String username, String password) {
        Connection connection = Database.getConnection();
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // Get the role directly from the database
                return resultSet.getString("role").toLowerCase();  // Convert role to lowercase
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Unable to validate login.");
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null; // Return null if login fails
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
