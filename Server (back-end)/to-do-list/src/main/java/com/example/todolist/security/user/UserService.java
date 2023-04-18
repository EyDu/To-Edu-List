package com.example.todolist.security.user;

import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        User admin = userRepository.findByUsername("admin").orElse(null);
        if (admin == null) {
            admin = new User();
            admin.setFirstname("Admin");
            admin.setLastname("User");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("EduNote"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        }

        User regularUser = userRepository.findByUsername("user").orElse(null);
        if (regularUser == null) {
            regularUser = new User();
            regularUser.setFirstname("Regular");
            regularUser.setLastname("User");
            regularUser.setUsername("user");
            regularUser.setPassword(passwordEncoder.encode("EduNote"));
            regularUser.setRole(Role.USER);
            userRepository.save(regularUser);
        }
    }

}

