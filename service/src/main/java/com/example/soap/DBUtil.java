package com.example.soap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;


public class DBUtil {

    private static final Dotenv dotenv = Dotenv.load();

    // Database connection details
    private static final String URL = dotenv.get("DB_URL"); // Fetch from .env file
    private static final String USER = dotenv.get("DB_USER"); // Fetch from .env file
    private static final String PASSWORD = dotenv.get("DB_PASSWORD"); // Fetch from .env file

    // Method to get database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
