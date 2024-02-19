package com.uchqun.server;

import com.uchqun.server.model.requestDto.UserRequest;
import com.uchqun.server.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.uchqun.server.model.enums.Role.SUPER_TEACHER;

@SpringBootApplication
@RequiredArgsConstructor
public class QuizApplication implements CommandLineRunner {

    private final UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(QuizApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName("admin");
        userRequest.setLastName("admin");
        userRequest.setUsername("admin");
        userRequest.setPassword("12345678");
        userRequest.setRole(SUPER_TEACHER);
        userService.save(userRequest);
    }
}