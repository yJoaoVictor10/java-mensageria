package com.example.comandservice.command;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/command")
public class CommandController {
    private final CommandService commandService;

    public CommandController(CommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping
    public ResponseEntity<Void> sendCommand(@RequestBody CommandRequest request){
        commandService.sendCommand(request);

        return ResponseEntity.ok().build();
    }
}
