package com.back.tasks.api.controller;

import com.back.tasks.api.io.task.TaskRequest;
import com.back.tasks.api.io.task.TaskResponse;
import com.back.tasks.api.open_api.controller.TaskControllerOpenApi;
import com.back.tasks.domain.service.task.TaskService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest) {
        TaskResponse response = taskService.createTask(taskRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<TaskResponse>> getTasks() {
        List<TaskResponse> response = taskService.getTasks();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
