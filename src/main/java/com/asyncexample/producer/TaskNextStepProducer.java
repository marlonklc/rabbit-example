package com.asyncexample.producer;

import com.asyncexample.config.RabbitConfig;
import com.asyncexample.dto.Task;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TaskNextStepProducer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private RabbitTemplate rabbitTemplate;
    private ObjectMapper objectMapper;

    public TaskNextStepProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @Async
    public void produce(Task task) {
        try {
            Message message = new Message(objectMapper.writeValueAsBytes(task));
            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NEXT_STEP_TASK, "#", message);
        } catch (JsonProcessingException ex) {
            logger.error("Error on convert object to JSON {}", task);
        }
    }
}
