package com.back.tasks.domain.service.project_relations.impl.assembler;

import com.back.tasks.api.io.project_relations.ProjectRelationsResponse;
import com.back.tasks.domain.entity.project_relations.ProjectRelationsEntity;
import com.back.tasks.domain.service.project.impl.assembler.ProjectAssembler;
import com.back.tasks.domain.service.user.impl.assembler.UserAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectRelationsAssembler {

    private final ProjectAssembler projectAssembler;
    private final UserAssembler userAssembler;

    public ProjectRelationsResponse parseProjectRelationsEntityToResponse(ProjectRelationsEntity projectRelationsEntity) {
        return ProjectRelationsResponse.builder()
                .project(projectAssembler.parseProjectEntityToResponse(projectRelationsEntity.getProject()))
                .user(userAssembler.parseUserEntityToResponse(projectRelationsEntity.getUser()))
                .build();
    }

    public List<ProjectRelationsResponse> parseProjectRelationsEntityToResponse(List<ProjectRelationsEntity> projectRelationsEntities) {
        List<ProjectRelationsResponse> responses = new ArrayList<>();

        projectRelationsEntities.forEach(projectRelationsEntity -> {
            responses.add(parseProjectRelationsEntityToResponse(projectRelationsEntity));
        });

        return responses;
    }
}
