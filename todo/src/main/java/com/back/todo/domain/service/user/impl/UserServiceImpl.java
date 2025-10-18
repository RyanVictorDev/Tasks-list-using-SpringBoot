package com.back.todo.domain.service.user.impl;

import com.back.todo.api.io.user.UserCreateRequest;
import com.back.todo.api.io.user.UserResponse;
import com.back.todo.domain.entity.user.UserEntity;
import com.back.todo.domain.repository.user.UserRepository;
import com.back.todo.domain.service.user.UserService;
import com.back.todo.domain.validation.user.impl.UserValidationImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final UserValidationImpl userValidationImpl;

    private final UserAssembler userAssembler;

    @Override
    public UserResponse create(UserCreateRequest request) {

        userValidationImpl.validateCreation(request);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        UserEntity userEntity = new UserEntity();
        userEntity.setName(request.getName());
        userEntity.setEmail(request.getEmail());
        userEntity.setPassword(encodedPassword);
        userEntity.setDeleted(false);

        userRepository.save(userEntity);

        return UserResponse.builder()
                                .name(request.getName())
                                .email(request.getEmail())
                            .build();
    }

    @Override
    public List<UserResponse> getAll() {
        return userAssembler.parseUserEntityToResponse(userRepository.findAll());
    }
}
