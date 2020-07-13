package com.sfl.scma;

import com.sfl.scma.domain.User;
import com.sfl.scma.enums.Role;
import com.sfl.scma.properties.AdminProperties;
import com.sfl.scma.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties(AdminProperties.class)
public class App implements CommandLineRunner {

    private final UserService userService;
    private final AdminProperties adminProperties;

    public App(UserService userService, AdminProperties adminProperties) {
        this.userService = userService;
        this.adminProperties = adminProperties;
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) {
        Optional<User> userById = userService.getUserById(1L);
        if (!userById.isPresent()) {
            User user = new User(
                    adminProperties.getFirstName(),
                    adminProperties.getLastName(),
                    adminProperties.getEmail(),
                    adminProperties.getUsername(),
                    adminProperties.getPassword(),
                    Role.ROLE_ADMIN
            );
            userService.createUser(user);
        }
    }
}