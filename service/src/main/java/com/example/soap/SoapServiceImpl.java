package com.example.soap;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@WebService(endpointInterface = "com.example.soap.SoapService")
public class SoapServiceImpl implements SoapService {

    private static List<User> users = new ArrayList<>();

    static {
        users.add(new User(1, "John Doe", List.of(new Book(101, "Java Programming"))));
    }

    @Override
    public User addUser(User user) {
        users.add(user);
        return user;
    }

    @Override
    public User getUser(int id) {
        return users.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public User updateUser(User user) {
        users.removeIf(u -> u.getId() == user.getId());
        users.add(user);
        return user;
    }

    @Override
    public void deleteUser(int id) {
        users.removeIf(user -> user.getId() == id);
    }
}
