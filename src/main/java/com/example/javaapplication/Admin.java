package com.example.javaapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin {

    @FXML
    private Pane btnLecture;

    @FXML
    private Button btnView_lecture;

    @FXML
    private Button btnView_PRL;

    @FXML
    private Button btnLectures;

    @FXML
    private Button btnMyProfile;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnPRL_Report;

    @FXML
    private Button btnLecture_work;

    // Assume loggedInUsername holds the current logged-in user's username
    private String loggedInUsername = "user5";  // Replace with actual session tracking logic
    private String loggedInRole = "Admin"; // Replace with actual session tracking logic

    @FXML
    void handleExit(ActionEvent event) {
        try {
            // Load Homepage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Homepage.fxml"));
            Parent homepageRoot = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) btnExit.getScene().getWindow();
            stage.setScene(new Scene(homepageRoot));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void handleLecture_work(ActionEvent event) {
        showAlert("Lecture Work button clicked!");
    }

    @FXML
    void handleLectures(ActionEvent event) {
        navigateToLecturesScreen(event);
    }

    @FXML
    void handleMyProfile(ActionEvent event) {
        fetchAndDisplayUserProfile();
    }

    @FXML
    void handlePRL_Report(ActionEvent event) {
        // Fetch and display PRL Report information from the database
        fetchAndDisplayPRLReportInfo();
    }

    @FXML
    void handleView_PRL(ActionEvent event) {
        // Only show PRL related information if the logged-in user is an Admin (or any other role as required)
        if ("Admin".equals(loggedInRole)) {
            fetchAndDisplayPRLInfo();
        } else {
            showAlert("Access Denied", "Only Admin can view the PRL information.");
        }
    }

    @FXML
    void handleView_lecture(ActionEvent event) {
        if ("Admin".equals(loggedInRole)) {
            fetchAndDisplayAllLecturers();
        } else {
            showAlert("Access Denied", "Only Admin can view the lectures.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Button Clicked");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void navigateToLecturesScreen(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/javaapplication/Lecture.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Lectures");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load the Lectures screen.");
        }
    }

    private void fetchAndDisplayPRLReportInfo() {
        // Query to retrieve all data from the PRL_Report table
        String query = "SELECT * FROM PRL_Report";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            StringBuilder prlReportInfo = new StringBuilder();

            while (rs.next()) {
                // Fetch each column from the PRL_Report table
                String campus = rs.getString("campus");
                String faculty = rs.getString("faculty");
                String stream = rs.getString("stream");
                String prl = rs.getString("prl");
                String week = rs.getString("week");
                String lecturer = rs.getString("lecturer");
                String className = rs.getString("class");
                String courseNameAndClass = rs.getString("course_name_and_class");
                String chapterCovered = rs.getString("chapter_covered");
                String modeOfDelivery = rs.getString("mode_of_delivery");
                int studentsMissed = rs.getInt("students_missed");
                double attendanceLevel = rs.getDouble("attendance_level");
                int studentRegistrations = rs.getInt("student_registrations");
                String challenges = rs.getString("challenges");
                String recommendations = rs.getString("recommendations");
                String assessmentMalpractice = rs.getString("assessment_malpractice");

                // Append each report's information to the string builder
                prlReportInfo.append("Campus: ").append(campus).append("\n")
                        .append("Faculty: ").append(faculty).append("\n")
                        .append("Stream: ").append(stream).append("\n")
                        .append("PRL: ").append(prl).append("\n")
                        .append("Week: ").append(week).append("\n")
                        .append("Lecturer: ").append(lecturer).append("\n")
                        .append("Class: ").append(className).append("\n")
                        .append("Course Name and Class: ").append(courseNameAndClass).append("\n")
                        .append("Chapter Covered: ").append(chapterCovered).append("\n")
                        .append("Mode of Delivery: ").append(modeOfDelivery).append("\n")
                        .append("Students Missed: ").append(studentsMissed).append("\n")
                        .append("Attendance Level: ").append(attendanceLevel).append("\n")
                        .append("Student Registrations: ").append(studentRegistrations).append("\n")
                        .append("Challenges: ").append(challenges).append("\n")
                        .append("Recommendations: ").append(recommendations).append("\n")
                        .append("Assessment Malpractice: ").append(assessmentMalpractice).append("\n\n");
            }

            if (prlReportInfo.length() > 0) {
                showPRLReportInfo(prlReportInfo.toString());
            } else {
                showAlert("No PRL Reports", "No PRL Report found in the system.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to retrieve PRL Report information.");
        }
    }

    private void showPRLReportInfo(String prlReportInfo) {
        // Show PRL report information in an alert box
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("PRL Report Information");
        alert.setHeaderText("All PRL Reports");
        alert.setContentText(prlReportInfo);
        alert.showAndWait();
    }

    private void fetchAndDisplayPRLInfo() {
        String query = "SELECT * FROM users WHERE role = 'PRL'";  // Fetch PRL information from the database
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            StringBuilder prlInfo = new StringBuilder();

            while (rs.next()) {
                String username = rs.getString("username");
                String email = rs.getString("email");
                String role = rs.getString("role");

                // Append each PRL's information to the string builder
                prlInfo.append("Username: ").append(username).append("\n")
                        .append("Email: ").append(email).append("\n")
                        .append("Role: ").append(role).append("\n\n");
            }

            if (prlInfo.length() > 0) {
                showPRLInfo(prlInfo.toString());
            } else {
                showAlert("No PRLs", "No PRL found in the system.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to retrieve PRL information.");
        }
    }
    private void showPRLInfo(String prlInfo) {
        // Show PRL information in an alert box (you could also use a new FXML page for a more detailed display)
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("PRL Information");
        alert.setHeaderText("All PRLs");
        alert.setContentText(prlInfo);
        alert.showAndWait();
    }
    private void fetchAndDisplayUserProfile() {
        String query = "SELECT * FROM users WHERE username = ?";  // Fetch based on logged-in username
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the logged-in username as the parameter
            stmt.setString(1, loggedInUsername);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String username = rs.getString("username");
                String email = rs.getString("email");
                String role = rs.getString("role");

                // Show the user profile (you can also display this in a new scene instead of an alert)
                showUserProfile(username, email, role);
            } else {
                showAlert("Error", "User not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to retrieve user profile.");
        }
    }

    private void showUserProfile(String username, String email, String role) {
        // Show user profile in an alert box (you could also use a new FXML page for a profile)
        String message = "Username: " + username + "\n" +
                "Email: " + email + "\n" +
                "Role: " + role;

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User Profile");
        alert.setHeaderText("Logged-in User Information");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void fetchAndDisplayAllLecturers() {
        String query = "SELECT * FROM users WHERE role = 'Lecture'";  // Fetch all lecturers
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            StringBuilder lecturersInfo = new StringBuilder();

            while (rs.next()) {
                String username = rs.getString("username");
                String email = rs.getString("email");
                String role = rs.getString("role");

                // Append each lecturer's information to the string builder
                lecturersInfo.append("Username: ").append(username).append("\n")
                        .append("Email: ").append(email).append("\n")
                        .append("Role: ").append(role).append("\n\n");
            }

            if (lecturersInfo.length() > 0) {
                showLecturersList(lecturersInfo.toString());
            } else {
                showAlert("No Lecturers", "No lecturers found in the system.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to retrieve lecturers' information.");
        }
    }

    private void showLecturersList(String lecturersInfo) {
        // Show all lecturers in an alert box (you could also use a new FXML page to display this info)
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Lecturers List");
        alert.setHeaderText("All Lecturers");
        alert.setContentText(lecturersInfo);
        alert.showAndWait();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
