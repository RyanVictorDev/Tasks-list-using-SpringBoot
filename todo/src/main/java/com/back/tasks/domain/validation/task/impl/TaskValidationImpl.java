package com.back.tasks.domain.validation.task.impl;

import com.back.tasks.api.io.task.TaskRequest;
import com.back.tasks.api.io.task.TaskUpdateRequest;
import com.back.tasks.api.io.user.UserCreateRequest;
import com.back.tasks.api.io.user.UserResponse;
import com.back.tasks.domain.entity.task.TaskEntity;
import com.back.tasks.domain.entity.user.UserEntity;
import com.back.tasks.domain.exception.IllegalValueException;
import com.back.tasks.domain.repository.task.TaskRepository;
import com.back.tasks.domain.repository.user.UserRepository;
import com.back.tasks.domain.service.authentication.AuthenticationService;
import com.back.tasks.domain.service.user.UserService;
import com.back.tasks.domain.validation.task.TaskValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskValidationImpl implements TaskValidation {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    @Override
    public void validateForCreation(TaskRequest request) {
        validateTitle(request);
        validateDescription(request);
    }

    @Override
    public void validateForUpdate(TaskUpdateRequest request, Long id) {
        validateTaskExists(id);
        validateUserPermissionToEdit(id);
        validateUserExists(request);
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

    private void validateTaskExists(Long id) {
        TaskEntity task = taskRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new IllegalValueException("Task not found"));
    }

    private void validateUserPermissionToEdit(Long id) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalValueException("Task not found"));

        UserResponse loggedUser = authenticationService.getLoggedUser();
        if (task.getResponsible().getId() != loggedUser.getId()) {
            throw new IllegalValueException("You are not allowed to edit this task");
        }
    }

    private void validateUserExists(TaskUpdateRequest request) {
        if (request.getResponsibleId() != null) {
            UserEntity user = userRepository.findById(request.getResponsibleId());

            if (user == null) throw new IllegalValueException("User not found");
        }
    }
}
