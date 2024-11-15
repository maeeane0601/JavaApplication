package com.example.javaapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PRLReportController {

    @FXML
    private TextField txtLecture, txtClass, txtChallenges, txtStudent_registrations, txtCampus, txtFaculty, txtStream, txtPRL, txtWeek, txtCourse_name_and_class, txtChapter, txtMode_of_delivery, txtAttendance_level, txtStudents_missed, txtRecommendations, txtAssessment_malpractice;

    @FXML
    private Button btnEnter;

    @FXML
    void handleEnter(ActionEvent event) {
        // Get data from text fields
        String campus = txtCampus.getText();
        String faculty = txtFaculty.getText();
        String stream = txtStream.getText();
        String prl = txtPRL.getText();
        String week = txtWeek.getText();
        String lecturer = txtLecture.getText();
        String className = txtClass.getText();
        String courseNameAndClass = txtCourse_name_and_class.getText();
        String chapter = txtChapter.getText();
        String modeOfDelivery = txtMode_of_delivery.getText();
        int studentsMissed = Integer.parseInt(txtStudents_missed.getText());
        double attendanceLevel = Double.parseDouble(txtAttendance_level.getText());
        int studentRegistrations = Integer.parseInt(txtStudent_registrations.getText());
        String challenges = txtChallenges.getText();
        String recommendations = txtRecommendations.getText();
        String assessmentMalpractice = txtAssessment_malpractice.getText();

        // SQL insert query
        String insertSQL = "INSERT INTO PRL_Report (campus, faculty, stream, prl, week, lecturer, class, course_name_and_class, chapter_covered, mode_of_delivery, students_missed, attendance_level, student_registrations, challenges, recommendations, assessment_malpractice) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            // Set parameters
            pstmt.setString(1, campus);
            pstmt.setString(2, faculty);
            pstmt.setString(3, stream);
            pstmt.setString(4, prl);
            pstmt.setString(5, week);
            pstmt.setString(6, lecturer);
            pstmt.setString(7, className);
            pstmt.setString(8, courseNameAndClass);
            pstmt.setString(9, chapter);
            pstmt.setString(10, modeOfDelivery);
            pstmt.setInt(11, studentsMissed);
            pstmt.setDouble(12, attendanceLevel);
            pstmt.setInt(13, studentRegistrations);
            pstmt.setString(14, challenges);
            pstmt.setString(15, recommendations);
            pstmt.setString(16, assessmentMalpractice);

            // Execute the insert
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                showAlert("Success", "Record inserted successfully.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to insert record: " + e.getMessage());
        }
    }

    // Helper method to display alert messages
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
