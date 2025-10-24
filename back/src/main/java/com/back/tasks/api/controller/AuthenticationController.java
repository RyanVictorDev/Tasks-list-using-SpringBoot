package com.back.tasks.api.controller;

import com.back.tasks.api.io.authentication.AuthenticationRequest;
import com.back.tasks.api.io.authentication.AuthenticationResponse;
import com.back.tasks.api.open_api.controller.AuthenticationControllerOpenApi;
import com.back.tasks.api.open_api.controller.UserControllerOpenApi;
import com.back.tasks.domain.service.authentication.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController implements AuthenticationControllerOpenApi {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @Override
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        String token = authenticationService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

}
