package com.back.tasks.domain.service.task;

import com.back.tasks.api.io.task.TaskFilterRequest;
import com.back.tasks.api.io.task.TaskRequest;
import com.back.tasks.api.io.task.TaskResponse;
import com.back.tasks.api.io.task.TaskUpdateRequest;

import java.util.List;

public interface TaskService {
    TaskResponse createTask(TaskRequest request);
    List<TaskResponse> getTasks(TaskFilterRequest filterRequest);
    TaskResponse updateTask(Long id, TaskUpdateRequest request);
    String deleteTask(Long id);
}
