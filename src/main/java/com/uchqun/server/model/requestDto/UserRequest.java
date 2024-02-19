package com.uchqun.server.model.requestDto;

import com.uchqun.server.model.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    @NotBlank
    private String firstName;
    private String lastName;

    private String username;

    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
