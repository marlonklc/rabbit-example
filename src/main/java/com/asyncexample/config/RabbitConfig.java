package com.asyncexample.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
@EnableRabbit
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RabbitConfig {

    public static final String EXCHANGE_NEXT_STEP_TASK = "next-step-exchange";
    public static final String QUEUE_NEXT_STEP_TASK = "next-step-queue";

    @Bean
    public Exchange nextStepTaskExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_NEXT_STEP_TASK).build();
    }

    @Bean
    public Queue nextStepTaskQueue() {
        return new Queue(QUEUE_NEXT_STEP_TASK, true);
    }
}
