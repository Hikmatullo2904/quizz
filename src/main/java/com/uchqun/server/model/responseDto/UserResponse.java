package com.uchqun.server.model.responseDto;

import com.uchqun.server.model.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponse {
    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private Role role;
}
