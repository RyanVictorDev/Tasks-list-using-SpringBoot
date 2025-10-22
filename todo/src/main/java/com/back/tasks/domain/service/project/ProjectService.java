package com.back.tasks.domain.service.project;

import com.back.tasks.api.io.project.ProjectRequest;
import com.back.tasks.api.io.project.ProjectResponse;
import com.back.tasks.api.io.project.ProjectUpdateRequest;
import com.back.tasks.api.io.project.UpdateProjectUsersRequest;

import java.util.List;

public interface ProjectService {
    ProjectResponse create(ProjectRequest projectRequest);
    List<ProjectResponse> getProjects();
    ProjectResponse update(ProjectUpdateRequest projectUpdateRequest, Long projectId);
    ProjectResponse addUsers(UpdateProjectUsersRequest updateProjectUsersRequest, Long projectId);
    ProjectResponse deleteUsers(UpdateProjectUsersRequest updateProjectUsersRequest, Long projectId);
}
