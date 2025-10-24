package com.back.tasks.api.open_api.controller;

import com.back.tasks.api.io.project.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Projects", description = "Operations related to projects management")
public interface ProjectControllerOpenApi {
    @Operation(
            summary = "Create a project",
            description = "Creates a new project in the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Project created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid data",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
                    @ApiResponse(responseCode = "500", description = "Internal error",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
            }
    )
    ResponseEntity<ProjectResponse> createProject(
            @Parameter(description = "Representation of a new project", required = true)
            ProjectRequest request
    );

    @Operation(
            summary = "Get a project",
            description = "Get projects in the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid data",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
                    @ApiResponse(responseCode = "500", description = "Internal error",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
            }
    )
    ResponseEntity<List<ProjectResponse>> getProjects(
            @Parameter(description = "Representation of a project", required = true)
            ProjectFilterRequest filterRequest
    );

    @Operation(
            summary = "Update a project",
            description = "Update a project in the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Project updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid data",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
                    @ApiResponse(responseCode = "500", description = "Internal error",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
            }
    )
    ResponseEntity<ProjectResponse> updateProject(
            @Parameter(description = "Id of the project", required = true)
            Long id,

            @Parameter(description = "Representation of a update project", required = true)
            ProjectUpdateRequest request
    );

    @Operation(
            summary = "Add users into a project",
            description = "Add users into a project in the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Users add successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid data",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
                    @ApiResponse(responseCode = "500", description = "Internal error",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
            }
    )
    ResponseEntity<ProjectResponse> addUsers(
            @Parameter(description = "Id of the project", required = true)
            Long id,

            @Parameter(description = "Users list", required = true)
            UpdateProjectUsersRequest request
    );

    @Operation(
            summary = "Remove users into a project",
            description = "Remove users into a project in the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Users removed successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid data",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
                    @ApiResponse(responseCode = "500", description = "Internal error",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
            }
    )
    ResponseEntity<ProjectResponse> removeUsers(
            @Parameter(description = "Id of the project", required = true)
            Long id,

            @Parameter(description = "Users list", required = true)
            UpdateProjectUsersRequest request
    );

    @Operation(
            summary = "Delete project",
            description = "Delete project in the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Project deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid data",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
                    @ApiResponse(responseCode = "500", description = "Internal error",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
            }
    )
    ResponseEntity<String> deleteProject(
            @Parameter(description = "Id of the project", required = true)
            Long id
    );
}
