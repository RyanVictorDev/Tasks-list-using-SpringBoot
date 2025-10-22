package com.back.tasks.domain.service.project.impl;

import com.back.tasks.api.io.project.ProjectRequest;
import com.back.tasks.api.io.project.ProjectResponse;
import com.back.tasks.api.io.project.ProjectUpdateRequest;
import com.back.tasks.api.io.project.UpdateProjectUsersRequest;
import com.back.tasks.api.io.user.UserResponse;
import com.back.tasks.domain.entity.project.ProjectEntity;
import com.back.tasks.domain.entity.project_relations.ProjectRelationsEntity;
import com.back.tasks.domain.entity.user.UserEntity;
import com.back.tasks.domain.exception.IllegalValueException;
import com.back.tasks.domain.io.enums.UserRole;
import com.back.tasks.domain.repository.project.ProjectRepository;
import com.back.tasks.domain.repository.project_relations.ProjectRelationsRepository;
import com.back.tasks.domain.repository.user.UserRepository;
import com.back.tasks.domain.service.authentication.AuthenticationService;
import com.back.tasks.domain.service.project.ProjectService;
import com.back.tasks.domain.service.project.impl.assembler.ProjectAssembler;
import com.back.tasks.domain.service.project.impl.specification.ProjectJPASpecification;
import com.back.tasks.domain.service.project_relations.impl.specification.ProjectRelationsJPASpecification;
import com.back.tasks.domain.validation.project.ProjectValidation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final ProjectAssembler projectAssembler;
    private final ProjectRelationsRepository projectRelationsRepository;
    private final ProjectValidation projectValidation;

    @Override
    @Transactional
    public ProjectResponse create(ProjectRequest request) {
        UserResponse loggedUser = authenticationService.getLoggedUser();
        UserEntity user = userRepository.findById(loggedUser.getId());

        projectValidation.validateForCreation(request);

        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setName(request.getProjectName());
        projectEntity.setDescription(request.getProjectDescription());
        projectEntity.setManager(user);
        projectEntity.setDeleted(false);

        projectRepository.save(projectEntity);

        if (request.getUserIds() != null && !request.getUserIds().isEmpty()) {
            request.getUserIds().forEach(userId -> {

                ProjectRelationsEntity projectRelationsEntity = new ProjectRelationsEntity();

                projectRelationsEntity.setProject(projectEntity);
                projectRelationsEntity.setUser(user);
                projectRelationsEntity.setDeleted(false);

                projectRelationsRepository.save(projectRelationsEntity);
            });
        }

        return projectAssembler.parseProjectEntityToResponse(projectEntity);
    }

    @Override
    public List<ProjectResponse> getProjects() {
        UserResponse user = authenticationService.getLoggedUser();

        List<ProjectEntity> projectEntities;

        if (user.getRole() == UserRole.ADMIN) {
            projectEntities = projectRepository.findAll();
        } else {
            Specification<ProjectEntity> specification = (ProjectJPASpecification.withManagerIdEquals(String.valueOf(user.getId())));
            projectEntities = projectRepository.findAll(specification);
        }

        return projectAssembler.parseProjectEntityToResponse(projectEntities);
    }

    @Override
    @Transactional
    public ProjectResponse update(ProjectUpdateRequest request, Long projectId) {
        projectValidation.validateForUpdate(request, projectId);

        ProjectEntity project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalValueException("Project with id " + projectId + " does not exist"));

        project.setName(request.getProjectName());
        project.setDescription(request.getProjectDescription());

        return projectAssembler.parseProjectEntityToResponse(projectRepository.save(project));
    }

    @Override
    @Transactional
    public ProjectResponse addUsers(UpdateProjectUsersRequest updateProjectUsersRequest, Long projectId) {
        createProjectRelations(updateProjectUsersRequest, projectId);
        return projectAssembler.parseProjectEntityToResponse(projectRepository.findById(projectId).get());
    }

    @Override
    @Transactional
    public ProjectResponse deleteUsers(UpdateProjectUsersRequest updateProjectUsersRequest, Long projectId) {
        deleteProjectRelations(updateProjectUsersRequest, projectId);
        return projectAssembler.parseProjectEntityToResponse(projectRepository.findById(projectId).get());
    }

    private void createProjectRelations(UpdateProjectUsersRequest userIds, Long projectId) {
        projectValidation.validateForCreateProjectRelations(userIds, projectId);

        ProjectEntity project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalValueException("Project with id " + projectId + " does not exist"));
        userIds.getUserIds().forEach(userId -> {
            UserEntity user = userRepository.findById(userId);

            ProjectRelationsEntity projectRelationsEntity = new ProjectRelationsEntity();
            projectRelationsEntity.setProject(project);
            projectRelationsEntity.setUser(user);
            projectRelationsEntity.setDeleted(false);

            projectRelationsRepository.save(projectRelationsEntity);
        });
    }

    private void deleteProjectRelations(UpdateProjectUsersRequest userIds, Long projectId) {

        projectValidation.validateForDeleteProjectRelations(userIds, projectId);

        userIds.getUserIds().forEach(userId -> {
            UserEntity user = userRepository.findById(userId);

            Specification<ProjectRelationsEntity> specification = (ProjectRelationsJPASpecification.withUserIdEquals(userId))
                    .and(ProjectRelationsJPASpecification.withProjectIdEquals(projectId));

            ProjectRelationsEntity projectRelationsEntity = projectRelationsRepository.findAll(specification).get(0);

            projectRelationsEntity.setDeleted(true);

            projectRelationsRepository.save(projectRelationsEntity);
        });
    }
}
