package com.back.tasks.api.io.user;

import com.back.tasks.domain.io.enums.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private UserRole role;
}
