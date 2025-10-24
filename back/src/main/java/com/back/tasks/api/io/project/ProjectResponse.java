package com.back.tasks.api.io.project;

import com.back.tasks.api.io.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProjectResponse {
    private Long id;
    private String projectName;
    private String projectDescription;
    private UserResponse manager;

    private List<UserResponse> users;
}
