package ru.kata.spring.boot_security.demo.service;



import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    void addOrEditUser(User user);

    void deleteUser(int id);



    List<User> listUsers();

    User getUserById(int id);
}
