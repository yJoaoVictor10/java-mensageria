package com.example.validatorservice.command;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class CommandProducer {

    private final RabbitTemplate rabbitTemplate;

    public CommandProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String command) {

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.COMMAND_QUEUE,
                command
        );

        System.out.println(
                "Command sent to RabbitMQ: " + command
        );
    }
    }