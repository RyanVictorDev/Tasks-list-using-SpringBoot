package com.back.tasks.api.controller;

import com.back.tasks.api.io.task.TaskFilterRequest;
import com.back.tasks.api.io.task.TaskRequest;
import com.back.tasks.api.io.task.TaskResponse;
import com.back.tasks.api.io.task.TaskUpdateRequest;
import com.back.tasks.api.open_api.controller.TaskControllerOpenApi;
import com.back.tasks.domain.io.enums.TaskStatus;
import com.back.tasks.domain.service.task.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/task", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController implements TaskControllerOpenApi {

    private final TaskService taskService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @ResponseBody
    @Override
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest) {
        TaskResponse response = taskService.createTask(taskRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @ResponseBody
    @Override
    public ResponseEntity<List<TaskResponse>> getTasks(@ParameterObject TaskFilterRequest filterRequest) {
        List<TaskResponse> response = taskService.getTasks(filterRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    @ResponseBody
    @Override
    public ResponseEntity<List<TaskResponse>> getAllTasks(@ParameterObject TaskFilterRequest filterRequest) {
        List<TaskResponse> response = taskService.getAllTasks(filterRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    @ResponseBody
    @Override
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id, @RequestBody @Valid TaskUpdateRequest request) {
        TaskResponse response = taskService.updateTask(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        String response = taskService.deleteTask(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
