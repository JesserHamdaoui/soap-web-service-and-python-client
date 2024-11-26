package com.example.soap;

import com.example.soap.config.AppConfig;
import com.example.soap.model.Book;
import com.example.soap.model.BookDTO;
import com.example.soap.model.User;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@WebService
public class SoapServiceImpl {

    private static final EntityManagerFactory emf;
    private static final EntityManager em;

    static {
        System.setProperty("DB_USER", AppConfig.getDbUser());
        System.setProperty("DB_PASSWORD", AppConfig.getDbPassword());
        System.setProperty("DB_URL", AppConfig.getDbUrl());

        emf = Persistence.createEntityManagerFactory("MySQLPU");
        em = emf.createEntityManager();
    }


    @WebMethod
    public String createUser(
            @WebParam(name = "name") String name,
            @WebParam(name = "email") String email,
            @WebParam(name = "password") String password) {
        try {
            em.getTransaction().begin();
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            em.persist(user);
            em.getTransaction().commit();
            return "User created successfully with ID: " + user.getId();
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return "Error: " + e.getMessage();
        }
    }

    @WebMethod
    public String updateUser(
            @WebParam(name = "userId") int userId,
            @WebParam(name = "name") String name,
            @WebParam(name = "email") String email,
            @WebParam(name = "password") String password) {
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, userId);

            if (user == null) {
                return "User not found";
            }

            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            em.merge(user); // Update the user
            em.getTransaction().commit();
            return "User updated successfully";
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return "Error: " + e.getMessage();
        }
    }

    @WebMethod
    public String deleteUser(@WebParam(name = "userId") int userId) {
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, userId);

            if (user == null) {
                return "User not found";
            }

            em.remove(user); // Delete the user
            em.getTransaction().commit();
            return "User deleted successfully";
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return "Error: " + e.getMessage();
        }
    }

    @WebMethod
    public List<User> getAllUsers() {
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod
    public String createBook(
            @WebParam(name = "title") String title,
            @WebParam(name = "author") String author,
            @WebParam(name = "genre") String genre) {
        try {
            em.getTransaction().begin();
            Book book = new Book();
            book.setTitle(title);
            book.setAuthor(author);
            book.setGenre(genre);
            em.persist(book);
            em.getTransaction().commit();
            return "Book created successfully with ID: " + book.getId();
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return "Error: " + e.getMessage();
        }
    }

    @WebMethod
    public String updateBook(
            @WebParam(name = "bookId") int bookId,
            @WebParam(name = "title") String title,
            @WebParam(name = "author") String author,
            @WebParam(name = "genre") String genre) {
        try {
            em.getTransaction().begin();
            Book book = em.find(Book.class, bookId);

            if (book == null) {
                return "Book not found";
            }

            book.setTitle(title);
            book.setAuthor(author);
            book.setGenre(genre);
            em.merge(book); // Update the book
            em.getTransaction().commit();
            return "Book updated successfully";
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return "Error: " + e.getMessage();
        }
    }

    @WebMethod
    public String deleteBook(@WebParam(name = "bookId") int bookId) {
        try {
            em.getTransaction().begin();
            Book book = em.find(Book.class, bookId);

            if (book == null) {
                return "Book not found";
            }

            em.remove(book); // Delete the book
            em.getTransaction().commit();
            return "Book deleted successfully";
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return "Error: " + e.getMessage();
        }
    }

    @WebMethod
    public List<Book> getAllBooks() {
        try {
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b", Book.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod
    public String assignBookToUser(
            @WebParam(name = "bookId") int bookId,
            @WebParam(name = "userId") int userId) {
        try {
            em.getTransaction().begin();
            Book book = em.find(Book.class, bookId);
            User user = em.find(User.class, userId);

            if (book == null || user == null) {
                em.getTransaction().rollback();
                return "Book or User not found";
            }

            book.setUser(user);
            user.addBook(book);
            em.merge(book); // Ensure the changes are saved
            em.getTransaction().commit();
            return "Book assigned to user successfully";
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return "Error: " + e.getMessage();
        }
    }

    @WebMethod
    public List<BookDTO> getBooksByUser(@WebParam(name = "userId") int userId) {
        try {
            User user = em.find(User.class, userId);
            if (user != null) {
                List<BookDTO> bookDTOs = new ArrayList<>();
                for (BookDTO book : user.getBooks()) {
                    bookDTOs.add(new BookDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getGenre()));
                }
                return bookDTOs;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
