package com.back.tasks.domain.service.task.impl;

import com.back.tasks.api.io.task.TaskRequest;
import com.back.tasks.api.io.task.TaskResponse;
import com.back.tasks.api.io.user.UserResponse;
import com.back.tasks.domain.entity.task.TaskEntity;
import com.back.tasks.domain.entity.user.UserEntity;
import com.back.tasks.domain.io.enums.TaskStatus;
import com.back.tasks.domain.repository.task.TaskRepository;
import com.back.tasks.domain.repository.user.UserRepository;
import com.back.tasks.domain.service.authentication.AuthenticationService;
import com.back.tasks.domain.service.authentication.impl.AuthenticationServiceImpl;
import com.back.tasks.domain.service.task.TaskService;
import com.back.tasks.domain.service.user.UserService;
import com.back.tasks.domain.validation.task.TaskValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        taskRepository.save(taskEntity);

        return taskAssembler.taskEntityToResponse(taskEntity);
    }

    @Override
    public List<TaskResponse> getTasks() {
        return taskAssembler.taskEntityToResponse(taskRepository.findAll());
    }
}
