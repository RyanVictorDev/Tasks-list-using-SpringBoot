package com.back.tasks.api.io.project;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectFilterRequest {
    private String searchText;
    private Long projectId;
    private Long managerId;
}
