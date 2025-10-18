package com.back.todo.api.open_api.controller;

import com.back.todo.api.io.user.UserCreateRequest;
import com.back.todo.api.io.user.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Users", description = "Operations related to user management")
public interface UserControllerOpenApi {

    @Operation(
            summary = "Create a user",
            description = "Creates a new user in the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid data",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
                    @ApiResponse(responseCode = "500", description = "Internal error",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
            }
    )
    ResponseEntity<UserResponse> createUser(
            @Parameter(description = "Representation of a new user", required = true)
            UserCreateRequest user
    );

    @Operation(
            summary = "Get all users",
            description = "Returns a list of all users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid data",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
                    @ApiResponse(responseCode = "500", description = "Internal error",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
            }
    )
    ResponseEntity<List<UserResponse>> getAll();
}
