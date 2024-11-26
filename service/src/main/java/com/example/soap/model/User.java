package com.example.soap.model;

import com.example.soap.model.BookDTO;

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
    @XmlTransient
    private List<Book> books = new ArrayList<>();

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.books = new ArrayList<>();
    }

    // Default constructor
    public User() {
        this.books = new ArrayList<>();
    }

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
    public List<BookDTO> getBooks() {
        List<BookDTO> bookDTOs = new ArrayList<>();
        for (Book book : books) {
            bookDTOs.add(new BookDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getGenre()));
        }
        return bookDTOs;
    }

    @WebMethod
    public void setBooks(List<BookDTO> bookDTOs) {
        this.books = new ArrayList<>();
        for (BookDTO dto : bookDTOs) {
            Book book = new Book();
            book.setId(dto.getId());
            book.setTitle(dto.getTitle());
            book.setAuthor(dto.getAuthor());
            book.setGenre(dto.getGenre());
            this.books.add(book);
        }
    }

    @WebMethod
    public void addBook(Book book) {
        books.add(book);
        book.setUser(this);
    }
}
