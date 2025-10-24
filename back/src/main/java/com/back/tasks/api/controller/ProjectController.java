package com.back.tasks.api.controller;

import com.back.tasks.api.io.project.*;
import com.back.tasks.api.io.task.TaskFilterRequest;
import com.back.tasks.api.open_api.controller.ProjectControllerOpenApi;
import com.back.tasks.api.open_api.controller.TaskControllerOpenApi;
import com.back.tasks.domain.service.project.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/project", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectController implements ProjectControllerOpenApi {

    private final ProjectService projectService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @ResponseBody
    @Override
    public ResponseEntity<ProjectResponse> createProject(@RequestBody ProjectRequest request) {
        ProjectResponse response = projectService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @ResponseBody
    @Override
    public ResponseEntity<List<ProjectResponse>> getProjects(@ParameterObject ProjectFilterRequest filterRequest) {
        List<ProjectResponse> response = projectService.getProjects(filterRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    @ResponseBody
    @Override
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id, @RequestBody ProjectUpdateRequest projectRequest) {
        ProjectResponse response = projectService.update(projectRequest, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/addUsers/{id}")
    @Override
    public ResponseEntity<ProjectResponse> addUsers(@PathVariable Long id, @RequestBody UpdateProjectUsersRequest request) {
        ProjectResponse response = projectService.addUsers(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/removeUsers/{id}")
    @Override
    public ResponseEntity<ProjectResponse> removeUsers(@PathVariable Long id, @RequestBody UpdateProjectUsersRequest request) {
        ProjectResponse response = projectService.deleteUsers(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<String> deleteProject(@PathVariable Long id) {
        String response = projectService.deleteProject(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
