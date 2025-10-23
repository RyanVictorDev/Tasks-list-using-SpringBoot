package com.back.tasks.api.controller;

import com.back.tasks.api.io.project.ProjectRequest;
import com.back.tasks.api.io.project.ProjectResponse;
import com.back.tasks.api.io.project.ProjectUpdateRequest;
import com.back.tasks.domain.service.project.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/project", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectController {

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
    public ResponseEntity<List<ProjectResponse>> getProjects() {
        List<ProjectResponse> response = projectService.getProjects();
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
}
