package com.uchqun.server.controller;

import com.uchqun.server.model.requestDto.LoginRequest;
import com.uchqun.server.model.requestDto.UserRequest;
import com.uchqun.server.model.responseDto.JwtResponse;
import com.uchqun.server.service.interfaces.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.authenticate(loginRequest);
    }

    @PostMapping("/refresh")
    public JwtResponse refreshToken(HttpServletRequest request) {
        return authService.generateAccessToken(request);
    }

}
