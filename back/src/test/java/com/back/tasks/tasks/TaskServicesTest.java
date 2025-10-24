package com.back.tasks.tasks;

import com.back.tasks.api.io.task.*;
import com.back.tasks.api.io.user.UserResponse;
import com.back.tasks.domain.entity.project.ProjectEntity;
import com.back.tasks.domain.entity.task.TaskEntity;
import com.back.tasks.domain.entity.user.UserEntity;
import com.back.tasks.domain.exception.IllegalValueException;
import com.back.tasks.domain.io.enums.TaskStatus;
import com.back.tasks.domain.repository.project.ProjectRepository;
import com.back.tasks.domain.repository.task.TaskRepository;
import com.back.tasks.domain.repository.user.UserRepository;
import com.back.tasks.domain.service.authentication.AuthenticationService;
import com.back.tasks.domain.service.task.impl.TaskServiceImpl;
import com.back.tasks.domain.service.task.impl.assembler.TaskAssembler;
import com.back.tasks.domain.validation.task.TaskValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServicesTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskAssembler taskAssembler;
    @Mock
    private AuthenticationService authenticationService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TaskValidation taskValidation;
    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private UserResponse userResponse;
    private UserEntity userEntity;
    private ProjectEntity projectEntity;
    private TaskEntity taskEntity;

    @BeforeEach
    void setUp() {
        userResponse = new UserResponse();
        userResponse.setId(1L);

        userEntity = new UserEntity();
        userEntity.setId(1L);

        projectEntity = new ProjectEntity();
        projectEntity.setId(100L);

        taskEntity = new TaskEntity();
        taskEntity.setId(10L);
        taskEntity.setTitle("Test Task");
        taskEntity.setDescription("Test Description");
        taskEntity.setStatus(TaskStatus.WAITING);
        taskEntity.setResponsible(userEntity);
        taskEntity.setProject(projectEntity);
        taskEntity.setDeleted(false);
    }

    @Test
    void shouldCreateTaskSuccessfully() {
        TaskRequest request = new TaskRequest();
        request.setTitle("New Task");
        request.setDescription("Some description");
        request.setProjectId(100L);

        when(authenticationService.getLoggedUser()).thenReturn(userResponse);
        when(userRepository.findById(1L)).thenReturn(userEntity);
        when(projectRepository.findById(100L)).thenReturn(Optional.of(projectEntity));
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(taskEntity);
        when(taskAssembler.taskEntityToResponse(any(TaskEntity.class))).thenReturn(new TaskResponse());

        TaskResponse response = taskService.createTask(request);

        verify(taskValidation, times(1)).validateForCreation(request);
        verify(taskRepository, times(1)).save(any(TaskEntity.class));
        assertNotNull(response);
    }

    @Test
    void shouldThrowExceptionWhenValidationFailsOnCreate() {
        TaskRequest request = new TaskRequest();
        request.setTitle("");
        request.setDescription("Desc");
        request.setProjectId(100L);

        doThrow(new IllegalValueException("Title cannot be empty"))
                .when(taskValidation).validateForCreation(request);

        assertThrows(IllegalValueException.class, () -> taskService.createTask(request));
    }

    @Test
    void shouldUpdateTaskSuccessfully() {
        TaskUpdateRequest updateRequest = new TaskUpdateRequest();
        updateRequest.setTitle("Updated Title");
        updateRequest.setDescription("Updated Description");
        updateRequest.setStatus(TaskStatus.IN_PROGRESS);
        updateRequest.setResponsibleId(1L);

        when(taskRepository.findById(10L)).thenReturn(Optional.of(taskEntity));
        when(userRepository.findById(1L)).thenReturn(userEntity);
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(taskEntity);
        when(taskAssembler.taskEntityToResponse(any(TaskEntity.class))).thenReturn(new TaskResponse());

        TaskResponse response = taskService.updateTask(10L, updateRequest);

        verify(taskValidation, times(1)).validateForUpdate(updateRequest, 10L);
        verify(taskRepository, times(1)).save(any(TaskEntity.class));
        assertNotNull(response);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonexistentTask() {
        TaskUpdateRequest updateRequest = new TaskUpdateRequest();
        updateRequest.setTitle("Title");

        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalValueException.class, () -> taskService.updateTask(999L, updateRequest));
    }

    @Test
    void shouldThrowExceptionWhenValidationFailsOnUpdate() {
        TaskUpdateRequest updateRequest = new TaskUpdateRequest();
        updateRequest.setTitle("");

        doThrow(new IllegalValueException("Invalid update"))
                .when(taskValidation).validateForUpdate(updateRequest, 10L);

        assertThrows(IllegalValueException.class, () -> taskService.updateTask(10L, updateRequest));
    }

    @Test
    void shouldDeleteTaskSuccessfully() {
        when(taskRepository.findById(10L)).thenReturn(Optional.of(taskEntity));
        when(authenticationService.getLoggedUser()).thenReturn(userResponse);

        String result = taskService.deleteTask(10L);

        verify(taskRepository, times(1)).save(any(TaskEntity.class));
        assertEquals("Task has been deleted", result);
        assertTrue(taskEntity.getDeleted());
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonexistentTask() {
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalValueException.class, () -> taskService.deleteTask(999L));
    }

    @Test
    void shouldThrowExceptionWhenDeletingTaskNotOwnedByUser() {
        UserEntity anotherUser = new UserEntity();
        anotherUser.setId(2L);
        taskEntity.setResponsible(anotherUser);

        when(taskRepository.findById(10L)).thenReturn(Optional.of(taskEntity));
        when(authenticationService.getLoggedUser()).thenReturn(userResponse);

        IllegalValueException ex = assertThrows(IllegalValueException.class, () -> taskService.deleteTask(10L));
        assertEquals("You are not allowed to edit this task", ex.getMessage());
    }
}
