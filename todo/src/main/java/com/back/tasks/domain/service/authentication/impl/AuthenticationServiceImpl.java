package com.back.tasks.domain.service.authentication.impl;

import com.back.tasks.api.io.user.UserResponse;
import com.back.tasks.core.util.JWTUtil;
import com.back.tasks.domain.entity.user.UserEntity;
import com.back.tasks.domain.exception.IllegalValueException;
import com.back.tasks.domain.repository.user.UserRepository;
import com.back.tasks.domain.service.authentication.AuthenticationService;
import com.back.tasks.domain.service.user.impl.assembler.UserAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserAssembler userAssembler;

    @Override
    public String login(String email, String password) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalValueException("Error logging in."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalValueException("Error logging in.");
        }

        return jwtUtil.generateToken(user.getEmail());
    }

    @Override
    public UserResponse getLoggedUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserEntity loggedUser;
        if (principal instanceof UserEntity) {
            loggedUser = (UserEntity) principal;
        } else {
            throw new IllegalValueException("Error to get logged user.");
        }

        return userAssembler.parseUserEntityToResponse(loggedUser);
    }
}
