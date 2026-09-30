package com.example.miningservice;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class CommandConsumer {

    private final CommandCounterRepository repository;

    public CommandConsumer(CommandCounterRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(
            queues = RabbitMQConfig.COMMAND_QUEUE,
            concurrency = "1"
    )
    public void consume(String command) {

        System.out.println(
                "Command received from RabbitMQ: " + command
        );

        executeCommand(command);

        incrementCounter(command);
    }

    private void executeCommand(String command) {

        System.out.println(
                "Executing command on robot: " + command
        );
    }

    private void incrementCounter(String command) {

        CommandCounter counter = repository
                .findByCommand(command)
                .orElseGet(() ->
                        new CommandCounter(null, command, 0L)
                );

        counter.setCount(counter.getCount() + 1);

        repository.save(counter);

        System.out.println(
                "Command count: "
                        + command
                        + " = "
                        + counter.getCount()
        );
    }
}
