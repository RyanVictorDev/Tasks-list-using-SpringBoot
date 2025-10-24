package com.back.tasks.domain.validation.project.impl;

import com.back.tasks.api.io.project.ProjectRequest;
import com.back.tasks.api.io.project.ProjectResponse;
import com.back.tasks.api.io.project.ProjectUpdateRequest;
import com.back.tasks.api.io.project.UpdateProjectUsersRequest;
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
import com.back.tasks.domain.service.project.impl.assembler.ProjectAssembler;
import com.back.tasks.domain.service.project_relations.impl.specification.ProjectRelationsJPASpecification;
import com.back.tasks.domain.service.task.impl.specification.TaskJPASpecification;
import com.back.tasks.domain.validation.project.ProjectValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ProjectValidationImpl implements ProjectValidation {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final AuthenticationService authenticationService;
    private final ProjectAssembler projectAssembler;
    private final ProjectRelationsRepository projectRelationsRepository;
    private final TaskRepository taskRepository;

    @Override
    public void validateForCreation(ProjectRequest request) {
        validateProjectName(request);
        validateProjectDescription(request);
        validateRepeatedUser(request);
        validateUserExists(request);
        validateUserIdsHaveLoggedUser(request);
    }

    @Override
    public void validateForUpdate(ProjectUpdateRequest request, Long projectId) {
        validateProjectExists(projectId);
        validateProjectName(request);
        validateProjectDescription(request);
        validateUserIsManager(projectId);
    }

    @Override
    public void validateForCreateProjectRelations(UpdateProjectUsersRequest request, Long projectId) {
        validateUserExists(request);
        validateProjectId(projectId);
        validateUserIdsHaveManagerId(request, projectId);
        validateRepeatedUser(request, projectId);
    }

    @Override
    public void validateForDeleteProjectRelations(UpdateProjectUsersRequest request, Long projectId) {
        validateUserExists(request);
        validateProjectId(projectId);
        validateDeleteUserIdsHaveManagerId(request, projectId);
        validateRepeatedUserForDelete(request, projectId);
    }

    @Override
    public void validateForDeleteProject(Long projectId) {
        validateProjectId(projectId);
        validateProjectExists(projectId);
        validateDeleteProjectPermission(projectId);
        validateProjectHaveTasks(projectId);
    }

    private void validateProjectName(ProjectRequest request) {
        if (request.getProjectName() == null || request.getProjectName().isEmpty()) throw new IllegalValueException("Project name is required");
        if (request.getProjectName().length() < 3) throw new IllegalValueException("Project name must have at least 3 characters");
        if (request.getProjectName().length() > 255) throw new IllegalValueException("Project name must have up to 255 characters");
    }

    private void validateProjectDescription(ProjectRequest request) {
        if (request.getProjectDescription() == null || request.getProjectName().isEmpty()) throw new IllegalValueException("Project description is required");
        if (request.getProjectDescription().length() < 3) throw new IllegalValueException("Project description must have at least 3 characters");
        if (request.getProjectDescription().length() > 255) throw new IllegalValueException("Project description must have up to 255 characters");
    }

    private void validateProjectName(ProjectUpdateRequest request) {
        if (request.getProjectName() == null || request.getProjectName().isEmpty()) throw new IllegalValueException("Project name is required");
        if (request.getProjectName().length() < 3) throw new IllegalValueException("Project name must have at least 3 characters");
        if (request.getProjectName().length() > 255) throw new IllegalValueException("Project name must have up to 255 characters");
    }

    private void validateProjectDescription(ProjectUpdateRequest request) {
        if (request.getProjectDescription() == null || request.getProjectName().isEmpty()) throw new IllegalValueException("Project description is required");
        if (request.getProjectDescription().length() < 3) throw new IllegalValueException("Project description must have at least 3 characters");
        if (request.getProjectDescription().length() > 255) throw new IllegalValueException("Project description must have up to 255 characters");
    }

    private void validateProjectExists(Long id) {
        projectRepository.findById(id).orElseThrow(() -> new IllegalValueException("Project with id " + id + " does not exist"));
    }

    private void validateUserExists(ProjectRequest request) {
        if (request.getUserIds() != null && !request.getUserIds().isEmpty()) {
            request.getUserIds().forEach(userId -> {
                UserEntity user = userRepository.findByIdAndDeletedFalse(userId);
                if (user == null) throw new IllegalValueException("User not found with id: " + userId);
            });
        }
    }

    private void validateUserExists(UpdateProjectUsersRequest request) {
        if (request.getUserIds() != null && !request.getUserIds().isEmpty()) {
            request.getUserIds().forEach(userId -> {
                UserEntity user = userRepository.findByIdAndDeletedFalse(userId);
                if (user == null) throw new IllegalValueException("User not found with id: " + userId);
            });
        }
    }

    private void validateRepeatedUser(ProjectRequest request) {
        if (request.getUserIds() != null && !request.getUserIds().isEmpty()) {
            List<Long> userIds = request.getUserIds();

            Set<Long> uniqueIds = new HashSet<>(userIds);
            if (uniqueIds.size() != userIds.size()) {
                throw new IllegalValueException("There can be no duplicate ids.");
            }
        }
    }


    private void validateRepeatedUser(UpdateProjectUsersRequest request, Long projectId) {
        if (request.getUserIds() != null && !request.getUserIds().isEmpty()) {
            ProjectResponse project = projectAssembler.parseProjectEntityToResponse(
                    projectRepository.findById(projectId).orElseThrow(() ->
                            new IllegalValueException("Project with id " + projectId + " does not exist")));

            List<Long> userIds = request.getUserIds();

            Set<Long> uniqueIds = new HashSet<>(userIds);
            if (uniqueIds.size() != userIds.size()) {
                throw new IllegalValueException("There can be no duplicate ids.");
            }

            project.getUsers().forEach(user -> {
                if (uniqueIds.contains(user.getId())) throw new IllegalValueException("User with id " + user.getId() + " already in the project");
            });
        }
    }

    private void validateRepeatedUserForDelete(UpdateProjectUsersRequest request, Long projectId) {
        if (request.getUserIds() != null && !request.getUserIds().isEmpty()) {
            ProjectResponse project = projectAssembler.parseProjectEntityToResponse(
                    projectRepository.findById(projectId).orElseThrow(() ->
                            new IllegalValueException("Project with id " + projectId + " does not exist")));

            List<Long> userIds = request.getUserIds();

            Set<Long> uniqueIds = new HashSet<>(userIds);
            if (uniqueIds.size() != userIds.size()) {
                throw new IllegalValueException("There can be no duplicate ids.");
            }

            for (Long userId : userIds) {
                boolean exists = project.getUsers().stream()
                        .anyMatch(u -> u.getId().equals(userId));

                if (!exists) {
                    throw new IllegalValueException("User with id " + userId + " is not in the project.");
                }
            }
        }
    }


    private void validateUserIdsHaveLoggedUser(ProjectRequest request) {
        UserResponse loggedUser = authenticationService.getLoggedUser();

        request.getUserIds().forEach(userId -> {
            if (userId.equals(loggedUser.getId())) throw new IllegalValueException("You can't add it yourself. you're already the manager. Your id is: " + userId);
        });
    }

    private void validateUserIdsHaveManagerId(UpdateProjectUsersRequest request, Long projectId) {
        ProjectEntity project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalValueException("Project with id " + projectId + " does not exist"));

        request.getUserIds().forEach(userId -> {
            if (userId.equals(project.getManager().getId())) throw new IllegalValueException("You can not add this user, he is already the manager. His id is: " + userId);
        });
    }

    private void validateDeleteUserIdsHaveManagerId(UpdateProjectUsersRequest request, Long projectId) {
        ProjectEntity project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalValueException("Project with id " + projectId + " does not exist"));

        request.getUserIds().forEach(userId -> {
            if (userId.equals(project.getManager().getId())) throw new IllegalValueException("You can not delete this user, he is the manager. His id is: " + userId);
        });
    }

    private void validateProjectId(Long projectId) {
        if (projectId == null || projectId.toString().isEmpty()) throw new IllegalValueException("Project id is required");
    }

    private void validateUserIsManager(Long projectId) {
        UserResponse user = authenticationService.getLoggedUser();

        ProjectEntity project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalValueException("Project with id " + projectId + " does not exist"));

        if (user.getRole() == UserRole.USER) {
            if (project.getManager().getId() !=  user.getId()) throw new IllegalValueException("You are not allowed to edit this project");
        }
    }

    public void validateDeleteProjectPermission(Long projectId) {
        UserResponse user = authenticationService.getLoggedUser();
        ProjectEntity project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalValueException("Project with id " + projectId + " does not exist"));

        if (user.getRole() == UserRole.USER) {
            if (!user.getId().equals(project.getManager().getId())) {
                throw new IllegalValueException("Just admin or manager can delete this project");
            }
        }
    }

    public void validateProjectHaveTasks(Long projectId) {
        Specification<TaskEntity> specification = (TaskJPASpecification.withProjectIdEquals(projectId)).and(TaskJPASpecification.withDeleted(false));
        List<TaskEntity> taskEntities = taskRepository.findAll(specification);

        if (!taskEntities.isEmpty()) {
            throw new IllegalValueException("You cannot delete this project as it has tasks associated with it.");
        }
    }
}
