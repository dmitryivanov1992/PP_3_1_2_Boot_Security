package ru.kata.spring.boot_security.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RolesRepository;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserSecurity implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;


    public UserSecurity(UsersRepository usersRepository,
                        RolesRepository rolesRepository) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> personOptional = usersRepository.findByUsername(username);
        if (personOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return personOptional.get();
    }

    //добавление в базу первого пользователя и ролей
    @Bean
    public void createDefaultUserAndRoles() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");
        if (rolesRepository.findByRoleName(roleAdmin.getRoleName()).isEmpty()) {
            rolesRepository.save(roleAdmin);
        }
        if (rolesRepository.findByRoleName(roleUser.getRoleName()).isEmpty()) {
            rolesRepository.save(roleUser);
        }
        Set<Role> defaultUserRoles = new HashSet<>();

        defaultUserRoles.add(roleAdmin);
        defaultUserRoles.add(roleUser);
        User defaultUser =
                new User("Dmitry", "Ivanov", 31,
                        "dmitryivanov92@gmail.com", defaultUserRoles);
        defaultUser.setUsername("dmitry");
        defaultUser.setPassword(new BCryptPasswordEncoder().encode("123"));
        try {
            loadUserByUsername(defaultUser.getUsername());
        } catch (UsernameNotFoundException e) {
            usersRepository.save(defaultUser);
        }
    }
}
