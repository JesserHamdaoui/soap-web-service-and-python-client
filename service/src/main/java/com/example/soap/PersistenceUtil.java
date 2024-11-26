package com.example.soap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;

public class PersistenceUtil {

    private static final Dotenv dotenv = Dotenv.load();

    private static final String URL = dotenv.get("DB_URL"); // Database URL
    private static final String USER = dotenv.get("DB_USER"); // Database username
    private static final String PASSWORD = dotenv.get("DB_PASSWORD"); // Database password

    // Get database connection using JDBC
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
