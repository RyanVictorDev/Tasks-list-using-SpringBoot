package com.back.tasks.domain.service.project;

import com.back.tasks.api.io.project.ProjectRequest;
import com.back.tasks.api.io.project.ProjectResponse;

import java.util.List;

public interface ProjectService {
    ProjectResponse create(ProjectRequest projectRequest);
    List<ProjectResponse> getProjects();
}
