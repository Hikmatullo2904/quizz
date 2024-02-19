package com.uchqun.server.controller;

import com.uchqun.server.model.entity.User;
import com.uchqun.server.model.requestDto.UserRequest;
import com.uchqun.server.model.responseDto.UserResponse;
import com.uchqun.server.payload.ApiResponse;
import com.uchqun.server.service.interfaces.UserService;
import com.uchqun.server.util.annotations.CurrentUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public UserResponse me(@CurrentUser User user) {
        return userService.findOne(user.getId());
    }
    @GetMapping("/{id}")
    public UserResponse findOne(@PathVariable Long id) {
        return userService.findOne(id);
    }

    @GetMapping
    public List<UserResponse> findAll() {
        return userService.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_TEACHER')")
    public ApiResponse save(@Valid  @RequestBody UserRequest userRequest) {
        return userService.save(userRequest);
    }

    @PutMapping("/{id}")
    public ApiResponse update(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        return userService.update(id, userRequest);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_TEACHER')")
    public ApiResponse delete(@PathVariable Long id) {
        return userService.delete(id);
    }



}
