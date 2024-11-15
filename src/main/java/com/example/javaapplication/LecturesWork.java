package com.example.javaapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class LecturesWork {

    @FXML
    private TextField txtClass_date, txtClass_name, txtChapter, txtStudent_present, txtTotal_students;
    @FXML
    private TextArea txtLearning_outcomes;
    @FXML
    private Button btnMy_Profile, btnEnter;

    @FXML
    void handleEnter(ActionEvent event) {
        String classDate = txtClass_date.getText();
        String className = txtClass_name.getText();
        String chapter = txtChapter.getText();
        String learningOutcomes = txtLearning_outcomes.getText();
        String studentPresentText = txtStudent_present.getText();
        String totalStudentsText = txtTotal_students.getText();

        // Input validation
        if (classDate.isEmpty() || className.isEmpty() || chapter.isEmpty() || learningOutcomes.isEmpty() ||
                studentPresentText.isEmpty() || totalStudentsText.isEmpty()) {
            showAlert("Error", "All fields must be filled out.");
            return;
        }

        // Date validation
        Date sqlDate;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false); // Disallow invalid dates
            sqlDate = new Date(sdf.parse(classDate).getTime()); // Parse and convert to SQL Date
        } catch (ParseException e) {
            showAlert("Error", "Please enter a valid date in yyyy-MM-dd format.");
            return;
        }

        // Try parsing the student numbers
        int presentStudents, totalStudents;
        try {
            presentStudents = Integer.parseInt(studentPresentText);
            totalStudents = Integer.parseInt(totalStudentsText);
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter valid numbers for present and total students.");
            return;
        }

        saveLectureWork(sqlDate, className, chapter, learningOutcomes, presentStudents, totalStudents);
    }

    @FXML
    public void handleMy_Profile(ActionEvent event) {
        // Prbofile handling code here
    }

    private void saveLectureWork(Date classDate, String className, String chapter, String learningOutcomes,
                                 int presentStudents, int totalStudents) {
        String query = "INSERT INTO Lectures_Work (lecturer_id, class_date, class_name, chapter, learning_outcomes, " +
                "present_students, total_students) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, 1);  // Replace with actual lecturer_id
            stmt.setDate(2, classDate); // Using validated SQL Date
            stmt.setString(3, className);
            stmt.setString(4, chapter);
            stmt.setString(5, learningOutcomes);
            stmt.setInt(6, presentStudents);
            stmt.setInt(7, totalStudents);

            stmt.executeUpdate();
            showAlert("Success", "Lecture work has been saved successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save lecture work: " + e.getMessage());
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
