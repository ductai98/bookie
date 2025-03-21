package com.taild.bookservice.command.controller;


import com.taild.bookservice.command.commands.CreateBookCommand;
import com.taild.bookservice.command.model.BookRequestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
public class BookCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public String addBook(@RequestBody BookRequestModel requestModel) {

        CreateBookCommand command = new CreateBookCommand(
                UUID.randomUUID().toString(),
                requestModel.getName(),
                requestModel.getAuthor(),
                true  // Assume book is initially available
        );


        return commandGateway.sendAndWait(command);

    }
}
