package com.back.tasks.api.open_api.controller;

import com.back.tasks.api.io.task.TaskRequest;
import com.back.tasks.api.io.task.TaskResponse;
import com.back.tasks.api.io.user.UserCreateRequest;
import com.back.tasks.api.io.user.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Tasks", description = "Operations related to task management")
public interface TaskControllerOpenApi {

    @Operation(
            summary = "Create a task",
            description = "Creates a new task in the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Task created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid data",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
                    @ApiResponse(responseCode = "500", description = "Internal error",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
            }
    )
    ResponseEntity<TaskResponse> createTask(
            @Parameter(description = "Representation of a new task", required = true)
            TaskRequest task
    );

    @Operation(
            summary = "Get all tasks",
            description = "Get all tasks in the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid data",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
                    @ApiResponse(responseCode = "500", description = "Internal error",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
            }
    )
    ResponseEntity<List<TaskResponse>> getTasks(
//            @Parameter(description = "Representation of a task", required = true)
//            TaskRequest task
    );
}
