package com.back.todo.domain.service.user;

import com.back.todo.api.io.user.UserCreateRequest;
import com.back.todo.api.io.user.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse create(UserCreateRequest request);

    List<UserResponse> getAll();
}
