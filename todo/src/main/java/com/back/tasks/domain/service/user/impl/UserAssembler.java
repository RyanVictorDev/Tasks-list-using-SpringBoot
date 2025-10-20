package com.back.tasks.domain.service.user.impl;

import com.back.tasks.api.io.user.UserResponse;
import com.back.tasks.domain.entity.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserAssembler {

    public List<UserResponse> parseUserEntityToResponse(List<UserEntity> userEntityList) {
        List<UserResponse> userResponseList = new ArrayList<>();

        for (UserEntity userEntity : userEntityList) {
            userResponseList.add(
                UserResponse.builder()
                        .name(userEntity.getName())
                        .email(userEntity.getEmail())
                        .build()
            );
        }
        return userResponseList;
    }
}
