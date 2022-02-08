package com.asyncexample.api;

import com.asyncexample.api.request.TaskRequest;
import com.asyncexample.api.response.TaskResponse;
import com.asyncexample.dto.Task;
import com.asyncexample.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskApi {

    private final TaskService service;
    private final ObjectMapper objectMapper;

    public TaskApi(TaskService service, ObjectMapper objectMapper) {
        this.service = service;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public TaskResponse create(@RequestBody TaskRequest request) {
        Task task = service.create(request);

        TaskResponse response = objectMapper.convertValue(task, TaskResponse.class);

        return response;
    }

    @GetMapping("/{id}")
    public TaskResponse get(@PathVariable String id) {
        Task task = service.findById(id);

        TaskResponse response = objectMapper.convertValue(task, TaskResponse.class);

        return response;
    }

    @PutMapping("/{id}/next-step")
    public void goToNextStep(@PathVariable String id) {
        service.nextStep(id);
    }
}
