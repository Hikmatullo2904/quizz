package com.uchqun.server.service.interfaces;

import com.uchqun.server.model.requestDto.LoginRequest;
import com.uchqun.server.model.requestDto.UserRequest;
import com.uchqun.server.model.responseDto.JwtResponse;

public interface AuthService {
    JwtResponse authenticate(LoginRequest loginRequest);

    JwtResponse register(UserRequest userRequest);
}
