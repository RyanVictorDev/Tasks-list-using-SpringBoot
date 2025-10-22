package com.back.tasks.domain.service.authentication;

import com.back.tasks.api.io.user.UserResponse;

public interface AuthenticationService {
    String login(String email, String password);

    UserResponse getLoggedUser();
}
