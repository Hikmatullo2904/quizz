package com.uchqun.server.service.interfaces;

import com.uchqun.server.model.requestDto.LoginRequest;
import com.uchqun.server.model.requestDto.UserRequest;
import com.uchqun.server.model.responseDto.JwtResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    JwtResponse authenticate(LoginRequest loginRequest);
    JwtResponse generateAccessToken(HttpServletRequest request);

}
