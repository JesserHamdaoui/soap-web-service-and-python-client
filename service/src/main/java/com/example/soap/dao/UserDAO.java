package com.example.soap.dao;

import com.example.soap.model.User;
import com.example.soap.PersistenceUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // Create a new user
    public String createUser(User user) {
        String response = "User creation failed";
        try (Connection conn = PersistenceUtil.getConnection()) {
            String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, user.getName());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getPassword());
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            user.setId(generatedKeys.getInt(1)); // Set auto-generated ID
                        }
                    }
                    response = "User created successfully with ID: " + user.getId();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response = "Database error: " + e.getMessage();
        }
        return response;
    }

    // Get user by ID
    public User getUserById(int id) {
        User user = null;
        try (Connection conn = PersistenceUtil.getConnection()) {
            String sql = "SELECT * FROM users WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        user = new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
