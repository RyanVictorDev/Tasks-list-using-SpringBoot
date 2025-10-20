package com.back.tasks.domain.validation.user.impl;

import com.back.tasks.api.io.user.UserCreateRequest;
import com.back.tasks.domain.exception.IllegalValueException;
import com.back.tasks.domain.validation.user.UserValidation;
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
        if (request.getName().trim().isEmpty()) throw new IllegalValueException("Username cannot be empty");
    }

    private void validateEmail(UserCreateRequest request) {
        if (request.getEmail().trim().isEmpty()) throw new IllegalValueException("Email cannot be empty");
    }

    private void validatePassword(UserCreateRequest request) {
        if (request.getPassword().trim().isEmpty()) throw new IllegalValueException("Password cannot be empty");
    }
}
