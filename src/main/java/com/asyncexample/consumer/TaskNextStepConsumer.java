package com.asyncexample.consumer;

import com.asyncexample.config.RabbitConfig;
import com.asyncexample.dto.Task;
import com.asyncexample.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TaskNextStepConsumer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ObjectMapper objectMapper;
    private TaskService taskService;

    public TaskNextStepConsumer(ObjectMapper objectMapper, TaskService taskService) {
        this.objectMapper = objectMapper;
        this.taskService = taskService;
    }

    @RabbitListener(bindings = {
        @QueueBinding(
            value = @Queue(value = RabbitConfig.QUEUE_NEXT_STEP_TASK, durable = "true"),
            exchange = @Exchange(value = RabbitConfig.EXCHANGE_NEXT_STEP_TASK, type = "topic"),
            key = "#"
        )
    })
    public void consume(Message message) {
        try {
            Task task = objectMapper.readValue(message.getBody(), Task.class);
            task.nextStep();

            // here, simulates a heavy process that sometimes takes a few seconds
            Thread.sleep(10000l);

            // finally update task to next step
            taskService.save(task);

            logger.info("save task to next step on db {}", task);
        } catch (Exception ex) {
            logger.error("Error on send task to next step: {}", message);
        }
    }
}
