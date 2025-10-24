package com.back.tasks.projects;

import com.back.tasks.api.io.project.*;
import com.back.tasks.api.io.user.UserResponse;
import com.back.tasks.domain.entity.project.ProjectEntity;
import com.back.tasks.domain.entity.project_relations.ProjectRelationsEntity;
import com.back.tasks.domain.entity.task.TaskEntity;
import com.back.tasks.domain.entity.user.UserEntity;
import com.back.tasks.domain.exception.IllegalValueException;
import com.back.tasks.domain.io.enums.UserRole;
import com.back.tasks.domain.repository.project.ProjectRepository;
import com.back.tasks.domain.repository.project_relations.ProjectRelationsRepository;
import com.back.tasks.domain.repository.task.TaskRepository;
import com.back.tasks.domain.repository.user.UserRepository;
import com.back.tasks.domain.service.authentication.AuthenticationService;
import com.back.tasks.domain.service.project.impl.ProjectServiceImpl;
import com.back.tasks.domain.service.project.impl.assembler.ProjectAssembler;
import com.back.tasks.domain.validation.project.ProjectValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServicesTest {

    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private AuthenticationService authenticationService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProjectAssembler projectAssembler;
    @Mock
    private ProjectRelationsRepository projectRelationsRepository;
    @Mock
    private ProjectValidation projectValidation;
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    private UserResponse adminUser;
    private UserResponse normalUser;
    private UserEntity managerEntity;
    private ProjectEntity projectEntity;

    @BeforeEach
    void setUp() {
        adminUser = new UserResponse(1L, "Admin", "admin@email.com", UserRole.ADMIN);
        normalUser = new UserResponse(2L, "User", "user@email.com", UserRole.USER);
        managerEntity = new UserEntity();
        managerEntity.setId(1L);
        projectEntity = new ProjectEntity();
        projectEntity.setId(10L);
        projectEntity.setManager(managerEntity);
        projectEntity.setName("Project Test");
        projectEntity.setDescription("Description Test");
        projectEntity.setDeleted(false);
    }

    @Test
    void shouldCreateProjectSuccessfully() {
        ProjectRequest request = new ProjectRequest();
        request.setProjectName("New Project");
        request.setProjectDescription("Description");
        request.setUserIds(List.of(2L));

        UserEntity userEntity = new UserEntity();
        userEntity.setId(2L);

        when(authenticationService.getLoggedUser()).thenReturn(adminUser);
        when(userRepository.findById(adminUser.getId())).thenReturn(managerEntity);
        when(userRepository.findById(2L)).thenReturn(userEntity);
        when(projectAssembler.parseProjectEntityToResponse(any(ProjectEntity.class)))
                .thenReturn(ProjectResponse.builder().id(1L).projectName("New Project").build());

        ProjectResponse response = projectService.create(request);

        verify(projectRepository, times(1)).save(any(ProjectEntity.class));
        verify(projectRelationsRepository, times(1)).save(any(ProjectRelationsEntity.class));
        assertEquals("New Project", response.getProjectName());
    }

    @Test
    void shouldThrowExceptionWhenValidationFailsOnCreate() {
        ProjectRequest request = new ProjectRequest();
        request.setProjectName("");
        request.setProjectDescription("Desc");

        UserResponse userResponse = new UserResponse();
        userResponse.setId(1L);
        when(authenticationService.getLoggedUser()).thenReturn(userResponse);

        doThrow(new IllegalValueException("Project name is required"))
                .when(projectValidation).validateForCreation(request);

        assertThrows(IllegalValueException.class, () -> projectService.create(request));
    }

    @Test
    void shouldUpdateProjectSuccessfully() {
        ProjectUpdateRequest request = new ProjectUpdateRequest();
        request.setProjectName("Updated Project");
        request.setProjectDescription("Updated Desc");

        when(projectRepository.findById(10L)).thenReturn(Optional.of(projectEntity));
        when(projectRepository.save(any(ProjectEntity.class))).thenReturn(projectEntity);
        when(projectAssembler.parseProjectEntityToResponse(any(ProjectEntity.class)))
                .thenReturn(ProjectResponse.builder().id(10L).projectName("Updated Project").build());

        ProjectResponse response = projectService.update(request, 10L);

        verify(projectRepository, times(1)).save(any(ProjectEntity.class));
        assertEquals("Updated Project", response.getProjectName());
    }

    @Test
    void shouldThrowExceptionWhenProjectNotFoundOnUpdate() {
        ProjectUpdateRequest request = new ProjectUpdateRequest();
        request.setProjectName("New");
        request.setProjectDescription("Desc");

        when(projectRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalValueException.class, () -> projectService.update(request, 999L));
    }

    @Test
    void shouldAddUsersToProjectSuccessfully() {
        UpdateProjectUsersRequest req = new UpdateProjectUsersRequest();
        req.setUserIds(List.of(2L));

        when(projectRepository.findById(10L)).thenReturn(Optional.of(projectEntity));
        when(userRepository.findById(2L)).thenReturn(new UserEntity());
        when(projectAssembler.parseProjectEntityToResponse(projectEntity))
                .thenReturn(ProjectResponse.builder().id(10L).build());

        ProjectResponse response = projectService.addUsers(req, 10L);

        verify(projectRelationsRepository, times(1)).save(any(ProjectRelationsEntity.class));
        assertEquals(10L, response.getId());
    }

    @Test
    void shouldDeleteProjectSuccessfully() {
        when(projectRepository.findById(10L)).thenReturn(Optional.of(projectEntity));
        when(projectRelationsRepository.findAll((Specification<ProjectRelationsEntity>) any()))
                .thenReturn(List.of(new ProjectRelationsEntity()));

        String result = projectService.deleteProject(10L);

        verify(projectRelationsRepository, times(1)).save(any());
        verify(projectRepository, times(1)).findById(10L);
        assertEquals("Success when deleting project", result);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonexistentProject() {
        when(projectRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalValueException.class, () -> projectService.deleteProject(999L));
    }

}
