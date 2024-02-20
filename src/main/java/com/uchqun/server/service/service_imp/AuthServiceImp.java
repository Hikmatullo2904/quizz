package com.uchqun.server.service.service_imp;

import com.uchqun.server.config.service.JwtService;
import com.uchqun.server.exception.InvalidTokenException;
import com.uchqun.server.model.entity.User;
import com.uchqun.server.model.requestDto.LoginRequest;
import com.uchqun.server.model.requestDto.UserRequest;
import com.uchqun.server.model.responseDto.JwtResponse;
import com.uchqun.server.model.responseDto.UserResponse;
import com.uchqun.server.service.interfaces.AuthService;
import com.uchqun.server.service.interfaces.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {
    private final JwtService jwtService;
    private final AuthenticationProvider authenticationProvider;
    private final UserService userService;
    private final UserDetailsService userDetailsService;



    @Override
    public JwtResponse authenticate(LoginRequest loginRequest) {
        authenticationProvider.authenticate(new
                UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));


        User userDetails = (User) userDetailsService.loadUserByUsername(loginRequest.getUsername());
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        String accessToken = jwtService.generateAccessToken(userDetails);

        UserResponse user = userService.findOne(userDetails.getId());

        return new JwtResponse(accessToken, refreshToken, user);

    }


    public JwtResponse generateAccessToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("Authorization");
        if (refreshToken == null || !refreshToken.startsWith("Bearer ")) {
            return null;
        }

        refreshToken = refreshToken.substring(7);
        if (!jwtService.isTokenExpired(refreshToken)) {
            String username = jwtService.extractUsername(refreshToken);

            User userDetails = (User) userDetailsService.loadUserByUsername(username);
            String accessToken = jwtService.generateAccessToken(userDetails);

            UserResponse user = userService.findOne(userDetails.getId());

            return new JwtResponse(accessToken, refreshToken, user);
        }
        throw new InvalidTokenException("Refresh token expired");
    }



}
