package com.example.validatorservice.command;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ValidatorService {

    private static final Set<String> VALID_COMMANDS = Set.of(
            "RIGHT",
            "LEFT",
            "FRONT",
            "BACK",
            "OPEN",
            "CLOSE"
    );

    private static final int MAX_RETRIES = 5;
    private static final long INITIAL_BACKOFF = 500;

    private final CommandProducer commandProducer;

    public ValidatorService(CommandProducer commandProducer) {
        this.commandProducer = commandProducer;
    }

    public void validate(CommandRequest request) {

        String command = request.getCommand().toUpperCase();

        if (!VALID_COMMANDS.contains(command)) {
            throw new IllegalArgumentException(
                    "Invalid command: " + command
            );
        }

        validateWithRetry(command);
    }

    private void validateWithRetry(String command) {

        long backoff = INITIAL_BACKOFF;

        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {

            try {

                simulateFailure();

                System.out.println(
                        "Command validated: " + command
                );

                commandProducer.send(command);

                return;

            } catch (RuntimeException exception) {

                System.out.println(
                        "Validation failed - attempt "
                                + attempt
                                + "/"
                                + MAX_RETRIES
                );

                if (attempt == MAX_RETRIES) {
                    throw exception;
                }

                try {
                    Thread.sleep(backoff);
                } catch (InterruptedException e) {

                    Thread.currentThread().interrupt();

                    throw new RuntimeException(
                            "Retry interrupted",
                            e
                    );
                }

                backoff *= 2;
            }
        }
    }

    private void simulateFailure() {

        if (ThreadLocalRandom.current().nextBoolean()) {
            throw new RuntimeException(
                    "Simulated validation failure"
            );
        }
    }
}