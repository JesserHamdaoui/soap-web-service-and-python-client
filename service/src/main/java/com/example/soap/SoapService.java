package com.example.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface SoapService {
    @WebMethod
    User addUser(User user);

    @WebMethod
    User getUser(int id);

    @WebMethod
    List<User> getAllUsers();

    @WebMethod
    User updateUser(User user);

    @WebMethod
    void deleteUser(int id);
}
