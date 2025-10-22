package com.back.tasks.domain.validation.user;

import com.back.tasks.api.io.user.UserCreateRequest;

public interface UserValidation {
    void validateCreation(UserCreateRequest request);
}
