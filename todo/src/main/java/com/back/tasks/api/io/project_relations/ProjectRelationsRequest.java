package com.back.tasks.api.io.project_relations;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProjectRelationsRequest {
    private List<Long> userIds;
    private Long projectId;
}
