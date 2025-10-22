package com.back.tasks.api.io.authentication;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequest {
    String email;
    String password;
}
