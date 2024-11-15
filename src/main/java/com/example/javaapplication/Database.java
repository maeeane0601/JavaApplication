package com.example.javaapplication;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    private static final Logger LOGGER = Logger.getLogger(Database.class.getName());
    private static final String URL = "jdbc:mysql://localhost:3306/academic_reporting_system";
    private static final String USER = "root";
    private static final String PASSWORD = "57414480";

    static {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        LOGGER.addHandler(handler);
        LOGGER.setLevel(Level.ALL);
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            LOGGER.info("Database connected successfully!");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database connection failed!", e);
        }
        return connection;
    }
}
