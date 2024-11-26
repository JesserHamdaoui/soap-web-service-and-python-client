package com.example.soap.model;

import com.example.soap.model.Book;

import javax.persistence.*;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment strategy
    private int id;
    private String name;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>(); // Initialize the list of books

    // Constructor with parameters
    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.books = new ArrayList<>(); // Ensure books list is initialized
    }

    // Default constructor
    public User() {
        this.books = new ArrayList<>(); // Initialize the list to avoid null pointer
    }

    // Getters and Setters
    @WebMethod
    public int getId() {
        return id;
    }

    @WebMethod
    public void setId(int id) {
        this.id = id;
    }

    @WebMethod
    public String getName() {
        return name;
    }

    @WebMethod
    public void setName(String name) {
        this.name = name;
    }

    @WebMethod
    public String getEmail() {
        return email;
    }

    @WebMethod
    public void setEmail(String email) {
        this.email = email;
    }

    @WebMethod
    public String getPassword() {
        return password;
    }

    @WebMethod
    public void setPassword(String password) {
        this.password = password;
    }

    @WebMethod
    public List<Book> getBooks() {
        return books;
    }

    @WebMethod
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    // Add convenience method to add a book
    @WebMethod
    public void addBook(Book book) {
        books.add(book);
        book.setUser(this);  // Set the user for the book
    }
}
