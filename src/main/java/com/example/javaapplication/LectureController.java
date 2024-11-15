package com.example.javaapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LectureController {

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtRole;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtSemester;

    @FXML
    private TextField txtModule_name;

    @FXML
    private TextField txtAcademic_year;

    @FXML
    private void handleEnter(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String role = txtRole.getText();
        String email = txtEmail.getText();
        String semester = txtSemester.getText();
        String moduleName = txtModule_name.getText();
        String academicYear = txtAcademic_year.getText();

        if (role.equalsIgnoreCase("Lecture") || role.equalsIgnoreCase("PRL")) {
            addUserToDatabase(username, password, role, email, semester, moduleName, academicYear);
        } else {
            showAlert("Error", "Invalid role! Please enter 'Lecture' or 'PRL'.");
        }
    }

    private void addUserToDatabase(String username, String password, String role, String email, String semester, String moduleName, String academicYear) {
        String url = "jdbc:mysql://localhost:3306/academic_reporting_system";
        String dbUsername = "root";
        String dbPassword = "57414480";

        String sql = "INSERT INTO users (username, password, role, email, semester, module_name, academic_year) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
            stmt.setString(4, email);
            stmt.setString(5, semester);
            stmt.setString(6, moduleName);
            stmt.setString(7, academicYear);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Success", "New lecture added successfully!");
                loadAdmin();
            } else {
                showAlert("Error", "Failed to add the lecture.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Database error: " + e.getMessage());
        }
    }

    private void loadAdmin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
            Scene adminScene = new Scene(loader.load());

            Stage stage = (Stage) txtUsername.getScene().getWindow();
            stage.setScene(adminScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the Admin interface.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
