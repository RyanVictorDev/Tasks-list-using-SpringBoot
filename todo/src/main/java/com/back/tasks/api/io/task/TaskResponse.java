package com.back.tasks.api.io.task;

import com.back.tasks.api.io.project.ProjectResponse;
import com.back.tasks.api.io.user.UserResponse;
import com.back.tasks.domain.io.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private UserResponse responsible;
    private ProjectResponse project;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
