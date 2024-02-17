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

    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
