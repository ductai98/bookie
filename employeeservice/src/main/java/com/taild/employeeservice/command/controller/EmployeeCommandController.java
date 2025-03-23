package com.taild.employeeservice.command.controller;


import com.taild.employeeservice.command.commands.CreateEmployeeCommand;
import com.taild.employeeservice.command.commands.UpdateEmployeeCommand;
import com.taild.employeeservice.command.model.EmployeeCreateModel;
import com.taild.employeeservice.command.model.EmployeeUpdateModel;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public void createEmployee(@Valid @RequestBody EmployeeCreateModel model) {
        CreateEmployeeCommand command = new CreateEmployeeCommand(
                UUID.randomUUID().toString(),
                model.getFirstName(),
                model.getLastName(),
                model.getKin(),
                false);

        commandGateway.sendAndWait(command);
    }

    @PutMapping("/{id}")
    public void updateEmployee(@PathVariable String id, @Valid @RequestBody EmployeeUpdateModel model) {
        UpdateEmployeeCommand command = new UpdateEmployeeCommand(
                id,
                model.getFirstName(),
                model.getLastName(),
                model.getKin(),
                model.getHasDisciplined()
        );

        commandGateway.sendAndWait(command);
    }
}
