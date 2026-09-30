package com.example.comandservice.command;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CommandService {

    private final RestTemplate restTemplate;
    private final String validatorUrl;

    public CommandService(
            RestTemplate restTemplate,
            @Value("${services.validator.url}") String validatorUrl) {

        this.validatorUrl = validatorUrl;
        this.restTemplate = restTemplate;
    }

    public void sendCommand(CommandRequest request) {

        restTemplate.postForObject(
                validatorUrl,
                request,
                Void.class
        );
    }
}
