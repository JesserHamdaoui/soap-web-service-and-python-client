package com.example.soap.model;

import com.example.soap.model.User;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import javax.jws.WebMethod;
import javax.jws.WebService;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String author;
    private String genre;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Book(int id, String title, String author, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Book() {

    }

    // Getters and setters
    @WebMethod
    public int getId() {
        return id;
    }

    @WebMethod
    public void setId(int id) {
        this.id = id;
    }

    @WebMethod
    public String getTitle() {
        return title;
    }

    @WebMethod
    public void setTitle(String title) {
        this.title = title;
    }

    @WebMethod
    public String getAuthor() {
        return author;
    }

    @WebMethod
    public void setAuthor(String author) {
        this.author = author;
    }

    @WebMethod
    public String getGenre() {
        return genre;
    }

    @WebMethod
    public void setGenre(String genre) {
        this.genre = genre;
    }

    @WebMethod
    public User getUser() {
        return user;
    }

    @WebMethod
    public void setUser(User user) {
        this.user = user;
    }
}
