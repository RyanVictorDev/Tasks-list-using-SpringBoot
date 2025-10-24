package com.back.tasks.api.io.project;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateProjectUsersRequest {
    private List<Long> userIds;
}
