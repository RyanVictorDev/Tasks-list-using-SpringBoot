package com.back.todo.domain.validation.user.impl;

import com.back.todo.api.io.user.UserCreateRequest;
import com.back.todo.domain.validation.user.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidationImpl implements UserValidation {
    @Override
    public void validateCreation(UserCreateRequest request) {
        validateName(request);
        validatePassword(request);
        validateEmail(request);
    }

    private void validateName(UserCreateRequest request) {
        if (request.getName().trim().isEmpty()) throw new IllegalArgumentException("Username cannot be empty");
    }

    private void validateEmail(UserCreateRequest request) {
        if (request.getEmail().trim().isEmpty()) throw new IllegalArgumentException("Email cannot be empty");
    }

    private void validatePassword(UserCreateRequest request) {
        if (request.getPassword().trim().isEmpty()) throw new IllegalArgumentException("Password cannot be empty");
    }
}
