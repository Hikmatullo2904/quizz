package com.uchqun.server.service.interfaces;

import com.uchqun.server.model.requestDto.UserRequest;
import com.uchqun.server.model.responseDto.UserResponse;
import com.uchqun.server.payload.ApiResponse;

import java.util.List;

public interface UserService {
    ApiResponse save(UserRequest user);
    List<UserResponse> findAll(); // <T>
    UserResponse findOne(Long id);

    ApiResponse update(Long id, UserRequest user);

    ApiResponse delete(Long id);
}
