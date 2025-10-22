package com.back.tasks.api.io.task;

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
    Long id;
    String title;
    String description;
    TaskStatus status;
    UserResponse responsible;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}
