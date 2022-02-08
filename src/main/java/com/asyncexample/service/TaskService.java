package com.asyncexample.service;

import com.asyncexample.api.request.TaskRequest;
import com.asyncexample.dto.Task;
import com.asyncexample.exception.TaskNotFoundException;
import com.asyncexample.producer.TaskNextStepProducer;
import com.asyncexample.repository.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TaskService {

    private final ObjectMapper objectMapper;
    private final TaskNextStepProducer taskNextStepProducer;
    private final TaskRepository repository;

    public TaskService(ObjectMapper objectMapper, TaskNextStepProducer taskNextStepProducer, TaskRepository repository) {
        this.objectMapper = objectMapper;
        this.taskNextStepProducer = taskNextStepProducer;
        this.repository = repository;
    }

    public Task create(TaskRequest request) {
        Task task = objectMapper.convertValue(request, Task.class);
        task.setId(UUID.randomUUID().toString());

        save(task);

        return task;
    }

    public Task findById(String id) {
        return repository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public void nextStep(String id) {
        Task task = findById(id);

        taskNextStepProducer.produce(task);
    }

    public Task save(Task task) {
        return repository.save(task);
    }
}
