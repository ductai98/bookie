package com.taild.employeeservice.command.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteEmployeeCommand {

    @TargetAggregateIdentifier
    private String id;
}
