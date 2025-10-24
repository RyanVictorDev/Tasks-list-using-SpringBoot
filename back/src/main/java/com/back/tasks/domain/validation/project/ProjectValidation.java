package com.back.tasks.domain.validation.project;

import com.back.tasks.api.io.project.ProjectRequest;
import com.back.tasks.api.io.project.ProjectUpdateRequest;
import com.back.tasks.api.io.project.UpdateProjectUsersRequest;

public interface ProjectValidation {
    void validateForCreation(ProjectRequest request);
    void validateForUpdate(ProjectUpdateRequest request, Long projectId);
    void validateForCreateProjectRelations(UpdateProjectUsersRequest request, Long projectId);
    void validateForDeleteProjectRelations(UpdateProjectUsersRequest request, Long projectId);
    void validateForDeleteProject(Long projectId);
}
