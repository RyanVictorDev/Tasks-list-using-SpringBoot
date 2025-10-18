package com.back.todo.api.controller;

import com.back.todo.api.io.user.UserCreateRequest;
import com.back.todo.api.io.user.UserResponse;
import com.back.todo.api.open_api.controller.UserControllerOpenApi;
import com.back.todo.domain.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController implements UserControllerOpenApi {

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Override
    public ResponseEntity<UserResponse> createUser(@RequestBody UserCreateRequest request) {
        UserResponse response = userService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Override
    public ResponseEntity<List<UserResponse>> getAll() {
        List<UserResponse> response = userService.getAll();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
