package com.back.tasks.domain.validation.task.impl;

import com.back.tasks.api.io.task.TaskRequest;
import com.back.tasks.api.io.user.UserCreateRequest;
import com.back.tasks.domain.exception.IllegalValueException;
import com.back.tasks.domain.validation.task.TaskValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskValidationImpl implements TaskValidation {
    @Override
    public void validateCreation(TaskRequest request) {
        validateTitle(request);
        validateDescription(request);
    }

    private void validateTitle(TaskRequest request) {
        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) throw new IllegalValueException("Name cannot be empty");

        if (request.getTitle().length() < 3) throw new IllegalValueException("Title cannot be less than 3");

        if (request.getTitle().length() > 255) throw new IllegalValueException("Title cannot be more than 255");
    }

    private void validateDescription(TaskRequest request) {
        if (request.getDescription() == null || request.getDescription().trim().isEmpty()) throw new IllegalValueException("Description cannot be empty");

        if (request.getDescription().length() < 3) throw new IllegalValueException("Description cannot be less than 3");

        if (request.getDescription().length() > 255) throw new IllegalValueException("Description cannot be more than 255");
    }
}
