package com.back.tasks.domain.service.project.impl;

import com.back.tasks.api.io.project.ProjectRequest;
import com.back.tasks.api.io.project.ProjectResponse;
import com.back.tasks.api.io.user.UserResponse;
import com.back.tasks.domain.entity.project.ProjectEntity;
import com.back.tasks.domain.entity.project_relations.ProjectRelationsEntity;
import com.back.tasks.domain.entity.user.UserEntity;
import com.back.tasks.domain.repository.project.ProjectRepository;
import com.back.tasks.domain.repository.project_relations.ProjectRelationsRepository;
import com.back.tasks.domain.repository.user.UserRepository;
import com.back.tasks.domain.service.authentication.AuthenticationService;
import com.back.tasks.domain.service.project.ProjectService;
import com.back.tasks.domain.service.project.impl.assembler.ProjectAssembler;
import com.back.tasks.domain.service.project.impl.specification.ProjectJPASpecification;
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

    @Override
    @Transactional
    public ProjectResponse create(ProjectRequest request) {
        UserResponse loggedUser = authenticationService.getLoggedUser();
        UserEntity user = userRepository.findById(loggedUser.getId());

        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setName(request.getProjectName());
        projectEntity.setDescription(request.getProjectDescription());
        projectEntity.setManager(user);
        projectEntity.setDeleted(false);

        projectRepository.save(projectEntity);

        if (request.getUserIds() != null || !request.getUserIds().isEmpty()) {
            request.getUserIds().forEach(userId -> {

//                TODO: add validation

                ProjectRelationsEntity projectRelationsEntity = new ProjectRelationsEntity();

                projectRelationsEntity.setProject(projectEntity);
                projectRelationsEntity.setUser(user);

                projectRelationsRepository.save(projectRelationsEntity);
            });
        }

        return projectAssembler.parseProjectEntityToResponse(projectEntity);
    }

    @Override
    public List<ProjectResponse> getProjects() {
        UserResponse user = authenticationService.getLoggedUser();

        Specification<ProjectEntity> specification = (ProjectJPASpecification.withManagerIdEquals(String.valueOf(user.getId())));
        List<ProjectEntity> projectEntities = projectRepository.findAll(specification);

        return projectAssembler.parseProjectEntityToResponse(projectEntities);
    }
}
