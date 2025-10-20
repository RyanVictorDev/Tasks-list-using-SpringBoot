package com.back.tasks.api.io.authentication;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequest {
    @Email
    String email;

    String password;
}
