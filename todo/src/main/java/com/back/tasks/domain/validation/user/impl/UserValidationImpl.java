package com.back.tasks.domain.validation.user.impl;

import com.back.tasks.api.io.user.UserCreateRequest;
import com.back.tasks.api.io.user.UserResponse;
import com.back.tasks.domain.entity.user.UserEntity;
import com.back.tasks.domain.exception.IllegalValueException;
import com.back.tasks.domain.io.enums.UserRole;
import com.back.tasks.domain.repository.user.UserRepository;
import com.back.tasks.domain.service.authentication.AuthenticationService;
import com.back.tasks.domain.service.user.impl.assembler.UserAssembler;
import com.back.tasks.domain.validation.user.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UserValidationImpl implements UserValidation {
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final UserAssembler userAssembler;

    @Override
    public void validateCreation(UserCreateRequest request) {
        validateName(request);
        validatePassword(request);
        validateEmail(request);
        validateRole(request);
    }

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
            Pattern.CASE_INSENSITIVE
    );

    private void validateName(UserCreateRequest request) {
        if (request.getName() == null || request.getName().trim().isEmpty()) throw new IllegalValueException("Name is required");
        if (request.getName().length() < 3) throw new IllegalValueException("Name is too short");
        if (request.getName().length() > 255) throw new IllegalValueException("Name length exceeds 255");
    }

    private void validateEmail(UserCreateRequest request) {
        String email = request.getEmail();

        if (email == null || email.trim().isEmpty()) throw new IllegalValueException("Email cannot be empty");
        if (!EMAIL_PATTERN.matcher(email).matches()) throw new IllegalValueException("Invalid email format");
        if (userRepository.findByEmail(email).isPresent()) throw new IllegalValueException("Email already exists");
        if (request.getEmail().length() > 255) throw new IllegalValueException("Email length exceeds 255");
    }

    private void validatePassword(UserCreateRequest request) {
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) throw new IllegalValueException("Password cannot be empty");
        if (request.getPassword().trim().length() < 4) throw new IllegalValueException("Password must be at least 4 characters");
        if (request.getPassword().length() > 255) throw new IllegalValueException("Password length exceeds 255");
    }

    private void validateRole(UserCreateRequest request) {
        if (request.getRole() == UserRole.ADMIN) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserResponse user = null;
            UserEntity loggedUser;

            if (principal instanceof UserEntity) {
                loggedUser = (UserEntity) principal;
                user = userAssembler.parseUserEntityToResponse(loggedUser);;
            }

            if (user == null || (user != null && user.getRole() == UserRole.USER)) {
                throw new IllegalValueException("Only an admin can create an admin user");
            }
        }
    }
}
