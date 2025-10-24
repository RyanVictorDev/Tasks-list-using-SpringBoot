package com.back.tasks.api.io.user;

import com.back.tasks.domain.io.enums.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private UserRole role;
}
