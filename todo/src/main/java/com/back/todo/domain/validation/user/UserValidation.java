package com.back.todo.domain.validation.user;

import com.back.todo.api.io.user.UserCreateRequest;

public interface UserValidation {
    void validateCreation(UserCreateRequest request);
}
