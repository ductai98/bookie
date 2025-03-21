package com.taild.bookservice.command.controller;


import com.taild.bookservice.command.commands.CreateBookCommand;
import com.taild.bookservice.command.commands.DeleteBookCommand;
import com.taild.bookservice.command.commands.UpdateBookCommand;
import com.taild.bookservice.command.model.BookRequestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{id}")
    public String updateBook(@RequestBody BookRequestModel requestModel, @PathVariable String id) {
        UpdateBookCommand command = new UpdateBookCommand(
                id,
                requestModel.getName(),
                requestModel.getAuthor(),
                requestModel.getIsAvailable()
        );

        return commandGateway.sendAndWait(command);
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable String id) {
        DeleteBookCommand command = new DeleteBookCommand(id);

        return commandGateway.sendAndWait(command);
    }
}
