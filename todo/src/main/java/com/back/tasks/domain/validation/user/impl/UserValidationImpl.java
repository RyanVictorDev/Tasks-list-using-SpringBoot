package com.back.tasks.domain.validation.user.impl;

import com.back.tasks.api.io.user.UserCreateRequest;
import com.back.tasks.domain.exception.IllegalValueException;
import com.back.tasks.domain.repository.user.UserRepository;
import com.back.tasks.domain.validation.user.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UserValidationImpl implements UserValidation {
    private final UserRepository userRepository;

    @Override
    public void validateCreation(UserCreateRequest request) {
        validateName(request);
        validatePassword(request);
        validateEmail(request);
    }

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
            Pattern.CASE_INSENSITIVE
    );

    private void validateName(UserCreateRequest request) {
        if (request.getName().trim().isEmpty()) throw new IllegalValueException("Username cannot be empty");
    }

    private void validateEmail(UserCreateRequest request) {
        String email = request.getEmail();

        if (email == null || email.trim().isEmpty()) throw new IllegalValueException("Email cannot be empty");

        if (!EMAIL_PATTERN.matcher(email).matches()) throw new IllegalValueException("Invalid email format");

        if (userRepository.findByEmail(email).isPresent()) throw new IllegalValueException("Email already exists");
    }

    private void validatePassword(UserCreateRequest request) {
        if (request.getPassword().trim().isEmpty()) throw new IllegalValueException("Password cannot be empty");
    }
}
