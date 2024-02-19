package com.uchqun.server.service.service_imp;

import com.uchqun.server.config.service.JwtService;
import com.uchqun.server.model.entity.User;
import com.uchqun.server.model.requestDto.LoginRequest;
import com.uchqun.server.model.requestDto.UserRequest;
import com.uchqun.server.model.responseDto.JwtResponse;
import com.uchqun.server.model.responseDto.UserResponse;
import com.uchqun.server.service.interfaces.AuthService;
import com.uchqun.server.service.interfaces.UserService;
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

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {
    private final JwtService jwtService;
    private final AuthenticationProvider authenticationProvider;
    private final UserService userService;
    private final UserDetailsService userDetailsService;

    @Override
    public JwtResponse register(UserRequest userRequest) {
        userService.save(userRequest);

        return authenticate(new LoginRequest(userRequest.getUsername(), userRequest.getPassword()));
    }

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

    private  String convertImageToBase64(String imagePath) {
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] imageBytes = baos.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            return null;
        }
    }

    private  void saveBase64ImageToFile(String base64Image, String outputFilePath) {
        try {
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            FileOutputStream fos = new FileOutputStream(outputFilePath);
            fos.write(imageBytes);
            fos.close();
            System.out.println("Image saved as file: " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
