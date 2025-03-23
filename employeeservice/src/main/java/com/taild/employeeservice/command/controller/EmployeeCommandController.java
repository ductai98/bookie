package com.taild.employeeservice.command.controller;


import com.taild.employeeservice.command.commands.CreateEmployeeCommand;
import com.taild.employeeservice.command.model.EmployeeRequestModel;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public void createEmployee(@Valid @RequestBody EmployeeRequestModel model) {
        CreateEmployeeCommand command = new CreateEmployeeCommand(
                UUID.randomUUID().toString(),
                model.getFirstName(),
                model.getLastName(),
                model.getKin(),
                false);

        commandGateway.sendAndWait(command);
    }


}
