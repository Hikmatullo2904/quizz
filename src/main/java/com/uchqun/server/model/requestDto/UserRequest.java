package com.uchqun.server.model.requestDto;

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
}
