package com.back.tasks.api.io.task;

import com.back.tasks.domain.io.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskFilterRequest {
    String searchText;
    TaskStatus taskStatus;
}
