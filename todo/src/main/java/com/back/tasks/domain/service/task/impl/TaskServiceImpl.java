package com.back.tasks.domain.service.task.impl;

import com.back.tasks.api.io.task.TaskFilterRequest;
import com.back.tasks.api.io.task.TaskRequest;
import com.back.tasks.api.io.task.TaskResponse;
import com.back.tasks.api.io.task.TaskUpdateRequest;
import com.back.tasks.api.io.user.UserResponse;
import com.back.tasks.domain.entity.task.TaskEntity;
import com.back.tasks.domain.entity.user.UserEntity;
import com.back.tasks.domain.exception.IllegalValueException;
import com.back.tasks.domain.io.enums.TaskStatus;
import com.back.tasks.domain.repository.task.TaskRepository;
import com.back.tasks.domain.repository.user.UserRepository;
import com.back.tasks.domain.service.authentication.AuthenticationService;
import com.back.tasks.domain.service.task.TaskService;
import com.back.tasks.domain.service.task.impl.assembler.TaskAssembler;
import com.back.tasks.domain.service.task.impl.specification.TaskJPASpecification;
import com.back.tasks.domain.validation.task.TaskValidation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskAssembler taskAssembler;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final TaskValidation taskValidation;

    @Override
    public TaskResponse createTask(TaskRequest request) {

        taskValidation.validateCreation(request);

        TaskEntity taskEntity = new TaskEntity();
        UserResponse loggedUser = authenticationService.getLoggedUser();
        UserEntity responsibleUser = userRepository.findById(loggedUser.getId());

        taskEntity.setTitle(request.getTitle());
        taskEntity.setDescription(request.getDescription());
        taskEntity.setStatus(TaskStatus.WAITING);
        taskEntity.setResponsible(responsibleUser);
        taskEntity.setDeleted(false);

        taskRepository.save(taskEntity);

        return taskAssembler.taskEntityToResponse(taskEntity);
    }

    @Override
    public List<TaskResponse> getTasks(TaskFilterRequest filterRequest) {
        UserResponse loggedUser = authenticationService.getLoggedUser();
        UserEntity responsibleUser = userRepository.findById(loggedUser.getId());

        Specification<TaskEntity> specification = (TaskJPASpecification.withDeleted(false))
                .and(TaskJPASpecification.withResponsibleUserId(responsibleUser.getId()))
                .and(
                        (TaskJPASpecification.withTitleLike(filterRequest.getSearchText()))
                                .or(TaskJPASpecification.withDescriptionLike(filterRequest.getSearchText()))
                                .or(TaskJPASpecification.withResponsibleUserNameLike(filterRequest.getSearchText()))
                                .or(TaskJPASpecification.withResponsibleUserEmailLike(filterRequest.getSearchText()))
                )
                .and(TaskJPASpecification.withStatus(filterRequest.getTaskStatus()))
                .and(TaskJPASpecification.withDeleted(false));

        return taskAssembler.taskEntityToResponse(taskRepository.findAll(specification));
    }

    @Override
    @Transactional
    public TaskResponse updateTask(Long id, TaskUpdateRequest request) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalValueException("Task not found"));

        UserResponse loggedUser = authenticationService.getLoggedUser();
        if (task.getResponsible().getId() != loggedUser.getId()) {
            throw new IllegalValueException("You are not allowed to edit this task");
        }

        if (request.getTitle() != null && !request.getTitle().trim().isEmpty()) {
            task.setTitle(request.getTitle());
        }

        if (request.getDescription() != null && !request.getDescription().trim().isEmpty()) {
            task.setDescription(request.getDescription());
        }

        if (request.getStatus() != null && !request.getStatus().toString().isEmpty()) {
            task.setStatus(request.getStatus());
        }

        task.setUpdatedAt(OffsetDateTime.now());

        TaskEntity saved = taskRepository.save(task);
        return taskAssembler.taskEntityToResponse(saved);
    }

    @Override
    public String deleteTask(Long id) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalValueException("Task not found"));

        UserResponse loggedUser = authenticationService.getLoggedUser();
        if (task.getResponsible().getId() != loggedUser.getId()) {
            throw new IllegalValueException("You are not allowed to edit this task");
        }

        task.setDeleted(true);
        taskRepository.save(task);

        return "Task has been deleted";
    }
}
