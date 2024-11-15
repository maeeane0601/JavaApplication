package com.example.javaapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class HomeController {

    @FXML
    private Button btnLecture;

    @FXML
    private Button btnPRL;

    @FXML
    private Button btnAdmin;

    @FXML
    private Button btnLogout;

    private String userRole; // Stores the role of the logged-in user

    // Method to set the user role and configure access based on it
    public void setUserRole(String role) {
        this.userRole = role;
        configureAccess();
    }

    // Configure access based on the user role
    private void configureAccess() {
        // Enable or disable buttons depending on the role
        switch (userRole) {
            case "admin":
                btnAdmin.setDisable(false);
                btnPRL.setDisable(false);
                btnLecture.setDisable(false);
                break;
            case "prl":
                btnAdmin.setDisable(true);  // Only Admin has full access
                btnPRL.setDisable(false);
                btnLecture.setDisable(true);
                break;
            case "lecture":
                btnAdmin.setDisable(true);
                btnPRL.setDisable(true);
                btnLecture.setDisable(false);
                break;
            default:
                showAlert("Error", "Unauthorized access");
                break;
        }
    }

    // Method to handle Admin button click and open Admin.fxml
    @FXML
    void handleAdmin(ActionEvent event) {
        if ("admin".equals(userRole)) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Admin.fxml"));
                Scene scene = new Scene(fxmlLoader.load());

                Stage stage = (Stage) btnAdmin.getScene().getWindow();  // Get current stage
                stage.setScene(scene);
                stage.setTitle("Admin Dashboard");
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Unable to open Admin interface.");
            }
        } else {
            showAlert("Access Denied", "You do not have admin privileges.");
        }
    }
    @FXML
    void handleLecture(ActionEvent event) {
        if ("lecture".equals(userRole) || "admin".equals(userRole)) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Work.fxml"));
                Scene scene = new Scene(fxmlLoader.load());

                Stage stage = (Stage) btnLecture.getScene().getWindow();  // Get current stage
                stage.setScene(scene);
                stage.setTitle("Lecture's Work");
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Unable to open Lecture's Work interface.");
            }
        } else {
            showAlert("Access Denied", "You do not have lecturer privileges.");
        }
    }


    @FXML
    void handlePRL(ActionEvent event) {
        if ("prl".equals(userRole) || "admin".equals(userRole)) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PRL_Report.fxml"));
                Scene scene = new Scene(fxmlLoader.load());

                // Optionally, set up the PRL_Report controller if you need to pass data to it
                PRLReportController prlReportController = fxmlLoader.getController();
                // If needed, you can call a method in PRL_ReportController to pass data
                // prlReportController.setSomeData(someData);

                Stage stage = (Stage) btnPRL.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("PRL Report");
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Unable to open PRL Report interface.");
            }
        } else {
            showAlert("Access Denied", "You do not have PRL privileges.");
        }
    }
    @FXML
    void handleLogout(ActionEvent event) {
        try {
            // Load the login FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            Scene loginScene = new Scene(fxmlLoader.load());

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) btnLogout.getScene().getWindow();
            stage.setScene(loginScene);
            stage.setTitle("Login");

            showAlert("Logout", "You have been logged out.");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to return to the login screen.");
        }
    }


    // Utility method to display an alert message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
