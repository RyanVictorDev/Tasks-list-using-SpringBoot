package com.back.tasks.api.io.project;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectUpdateRequest {
    private String projectName;
    private String projectDescription;
}
