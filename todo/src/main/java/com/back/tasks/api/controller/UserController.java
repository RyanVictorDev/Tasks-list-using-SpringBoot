package com.back.tasks.api.controller;

import com.back.tasks.api.io.user.UserCreateRequest;
import com.back.tasks.api.io.user.UserResponse;
import com.back.tasks.api.open_api.controller.UserControllerOpenApi;
import com.back.tasks.domain.repository.user.UserRepository;
import com.back.tasks.domain.service.user.UserService;
import com.back.tasks.domain.service.user.impl.UserAssembler;
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
    private final UserRepository userRepository;
    private final UserAssembler userAssembler;

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
    public List<UserResponse> getAll() {
        return userAssembler.parseUserEntityToResponse(userRepository.findAll());
    }
}
