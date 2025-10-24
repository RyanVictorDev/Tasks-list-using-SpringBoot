package com.back.tasks.api.io.project_relations;

import com.back.tasks.api.io.project.ProjectResponse;
import com.back.tasks.api.io.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProjectRelationsResponse {
    private UserResponse user;
    private ProjectResponse project;
}
