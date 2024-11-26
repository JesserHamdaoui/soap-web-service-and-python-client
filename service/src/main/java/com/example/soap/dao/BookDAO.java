package com.example.soap.dao;

import com.example.soap.model.Book;
import com.example.soap.PersistenceUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookDAO {

    // Create a new book in the database
    public String createBook(Book book) {
        String response = "Book creation failed";
        try (Connection conn = PersistenceUtil.getConnection()) {
            String sql = "INSERT INTO books (title, author, genre) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, book.getTitle());
                stmt.setString(2, book.getAuthor());
                stmt.setString(3, book.getGenre());
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            book.setId(generatedKeys.getInt(1)); // Set auto-generated ID
                        }
                    }
                    response = "Book created successfully with ID: " + book.getId();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response = "Database error: " + e.getMessage();
        }
        return response;
    }

    // Get a book by ID
    public Book getBookById(int id) {
        Book book = null;
        try (Connection conn = PersistenceUtil.getConnection()) {
            String sql = "SELECT * FROM books WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        book = new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"), rs.getString("genre"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }
}
