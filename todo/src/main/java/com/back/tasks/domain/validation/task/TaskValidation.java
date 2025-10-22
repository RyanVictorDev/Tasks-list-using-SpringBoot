package com.back.tasks.domain.validation.task;

import com.back.tasks.api.io.task.TaskRequest;
import com.back.tasks.api.io.task.TaskUpdateRequest;

public interface TaskValidation {
    void validateForCreation(TaskRequest request);
    void validateForUpdate(TaskUpdateRequest request, Long id);
}
