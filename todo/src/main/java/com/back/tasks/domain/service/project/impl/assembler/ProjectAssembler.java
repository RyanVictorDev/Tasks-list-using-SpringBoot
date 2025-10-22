package com.back.tasks.domain.service.project.impl.assembler;

import com.back.tasks.api.io.project.ProjectResponse;
import com.back.tasks.api.io.user.UserResponse;
import com.back.tasks.domain.entity.project.ProjectEntity;
import com.back.tasks.domain.entity.project_relations.ProjectRelationsEntity;
import com.back.tasks.domain.repository.project_relations.ProjectRelationsRepository;
import com.back.tasks.domain.service.project_relations.impl.specification.ProjectRelationsJPASpecification;
import com.back.tasks.domain.service.user.impl.assembler.UserAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectAssembler {

    private final ProjectRelationsRepository projectRelationsRepository;
    private final UserAssembler userAssembler;

    public ProjectResponse parseProjectEntityToResponse(ProjectEntity projectEntity) {
        Specification<ProjectRelationsEntity> specification = (ProjectRelationsJPASpecification.withProjectIdEquals(projectEntity.getId()));
        List<ProjectRelationsEntity> projectRelationsEntities = projectRelationsRepository.findAll(specification);

        List<UserResponse> userResponses = new ArrayList<>();
        for (ProjectRelationsEntity projectRelationsEntity : projectRelationsEntities) {
            userResponses.add(userAssembler.parseUserEntityToResponse(projectRelationsEntity.getUser()));
        }

        return ProjectResponse.builder()
                .id(projectEntity.getId())
                .projectName(projectEntity.getName())
                .projectDescription(projectEntity.getDescription())
                .manager(userAssembler.parseUserEntityToResponse(projectEntity.getManager()))
                .users(userResponses)
                .build();
    }

    public List<ProjectResponse> parseProjectEntityToResponse(List<ProjectEntity> projectResponses) {
        List<ProjectResponse> response = new ArrayList<>();

        projectResponses.forEach(projectResponse -> {
            response.add(parseProjectEntityToResponse(projectResponse));
        });

        return response;
    }
}
