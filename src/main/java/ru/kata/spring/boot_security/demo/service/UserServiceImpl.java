package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;
import java.util.List;


@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void addOrEditUser(User user) {
        if (user.getId() == 0 || !getUserById(user.getId()).getPassword().equals(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        usersRepository.save(user);
    }

    @Override
    public void deleteUser(int id) {
        if (getUserById(id) == null) {
            throw new NullPointerException();
        }
        usersRepository.deleteById(id);
    }

    @Override
    public List<User> listUsers() {
        return usersRepository.findAll();
    }

    @Override
    public User getUserById(int id) {
        return usersRepository.getById(id);
    }
}
