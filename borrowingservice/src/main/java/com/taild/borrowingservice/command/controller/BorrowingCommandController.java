package com.taild.borrowingservice.command.controller;


import com.taild.borrowingservice.command.commands.CreateBorrowingCommand;
import com.taild.borrowingservice.command.model.BorrowingRequestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/borrowing")
public class BorrowingCommandController {

    @Autowired
    private CommandGateway commandGateway;


    @PostMapping
    public String createBorrowing(@RequestBody BorrowingRequestModel requestModel) {
        CreateBorrowingCommand command = new CreateBorrowingCommand(
                UUID.randomUUID().toString(),
                requestModel.getBookId(),
                requestModel.getEmployeeId(),
                new Date()
        );

        return commandGateway.sendAndWait(command);
    }

}
