package com.back.tasks.domain.service.project;

import com.back.tasks.api.io.project.*;

import java.util.List;

public interface ProjectService {
    ProjectResponse create(ProjectRequest projectRequest);
    List<ProjectResponse> getProjects(ProjectFilterRequest request);
    ProjectResponse update(ProjectUpdateRequest projectUpdateRequest, Long projectId);
    ProjectResponse addUsers(UpdateProjectUsersRequest updateProjectUsersRequest, Long projectId);
    ProjectResponse deleteUsers(UpdateProjectUsersRequest updateProjectUsersRequest, Long projectId);
    String deleteProject(Long projectId);
}
