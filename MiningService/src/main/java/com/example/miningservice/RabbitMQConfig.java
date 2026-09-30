package com.example.miningservice;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String COMMAND_QUEUE = "mining-commands";


    @Bean
    public Queue commandQueue(){
        return new Queue(COMMAND_QUEUE, true);
    }
}
