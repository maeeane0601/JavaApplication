module com.example.javaapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // Add this line to allow access to java.sql

    opens com.example.javaapplication to javafx.fxml;
    exports com.example.javaapplication;
}
