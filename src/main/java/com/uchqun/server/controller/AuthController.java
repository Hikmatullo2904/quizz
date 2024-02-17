package com.uchqun.server.controller;

import com.uchqun.server.model.requestDto.LoginRequest;
import com.uchqun.server.model.requestDto.UserRequest;
import com.uchqun.server.model.responseDto.JwtResponse;
import com.uchqun.server.service.interfaces.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/login")
    public JwtResponse login(LoginRequest loginRequest) {
        return authService.authenticate(loginRequest);
    }



    @PostMapping("/register")
    public JwtResponse register(@Valid @RequestBody UserRequest userRequest) {
        return authService.register(userRequest);
    }


}
