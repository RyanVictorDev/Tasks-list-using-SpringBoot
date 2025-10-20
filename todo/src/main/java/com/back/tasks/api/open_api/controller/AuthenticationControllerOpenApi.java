package com.back.tasks.api.open_api.controller;

import com.back.tasks.api.io.authentication.AuthenticationRequest;
import com.back.tasks.api.io.authentication.AuthenticationResponse;
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

@Tag(name = "Auth", description = "Operations related to user management")
public interface AuthenticationControllerOpenApi {

    @Operation(
            summary = "Login",
            description = "Login in the system",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid data",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
                    @ApiResponse(responseCode = "500", description = "Internal error",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
            }
    )
    ResponseEntity<AuthenticationResponse> login(
            @Parameter(description = "Representation of a new user", required = true)
            AuthenticationRequest request
    );

}
