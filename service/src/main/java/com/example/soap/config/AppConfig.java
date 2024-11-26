package com.example.soap.config;

import io.github.cdimascio.dotenv.Dotenv;

public class AppConfig {

    private static final Dotenv dotenv;

    static {
        // Load environment variables from the .env file
        dotenv = Dotenv.configure()
                .directory("./") // Specify the directory where the .env file is located
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();
    }

    public static String getDbUser() {
        return dotenv.get("DB_USER");  // Retrieve DB_USER from the .env file
    }

    public static String getDbPassword() {
        return dotenv.get("DB_PASSWORD");  // Retrieve DB_PASSWORD from the .env file
    }

    public static String getDbUrl() {
        return dotenv.get("DB_URL");  // Retrieve DB_URL from the .env file
    }
}
