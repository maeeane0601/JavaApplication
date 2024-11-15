package com.example.javaapplication;

import javafx.event.ActionEvent;
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
import java.sql.SQLException;

public class SignUp {

    @FXML
    private Button btnEnter;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtRole;

    @FXML
    void handleEnter(ActionEvent event) {
        String username = txtUserName.getText();
        String password = txtPassword.getText();
        String role = "Admin"; // Fixed to "Admin" since this form is for admin signup

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter a username and password.");
        } else {
            if (registerAdmin(username, password, role)) {
                showAlert("Success", "Admin registered successfully.");
                navigateToLogin();  // Navigate to the login screen after successful registration
            } else {
                showAlert("Error", "Registration failed.");
            }
        }
    }

    private boolean registerAdmin(String username, String password, String role) {
        Connection connection = Database.getConnection();
        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, role);

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void navigateToLogin() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) btnEnter.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Login");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load the login screen.");
        }
    }
}
