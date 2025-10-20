package com.back.tasks.domain.validation.task;

import com.back.tasks.api.io.task.TaskRequest;

public interface TaskValidation {
    void validateCreation(TaskRequest request);
}
