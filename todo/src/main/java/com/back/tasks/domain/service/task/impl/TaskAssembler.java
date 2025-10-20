package com.back.tasks.domain.service.task.impl;

import com.back.tasks.api.io.task.TaskResponse;
import com.back.tasks.domain.entity.task.TaskEntity;
import com.back.tasks.domain.service.user.impl.UserAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskAssembler {

    private final UserAssembler userAssembler;

    public TaskResponse taskEntityToResponse(TaskEntity taskEntity) {
        return TaskResponse.builder()
                .id(taskEntity.getId())
                .title(taskEntity.getTitle())
                .description(taskEntity.getDescription())
                .status(taskEntity.getStatus())
                .responsible(userAssembler.parseUserEntityToResponse(taskEntity.getResponsible()))
                .createdAt(taskEntity.getCreatedAt())
                .updatedAt(taskEntity.getUpdatedAt())
                .build();
    }

    public List<TaskResponse> taskEntityToResponse(List<TaskEntity> taskEntities) {
        List<TaskResponse> taskResponses = new ArrayList<>();

        taskEntities.forEach(taskEntity -> taskResponses.add(taskEntityToResponse(taskEntity)));

        return taskResponses;
    }
}
