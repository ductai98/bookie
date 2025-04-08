package com.taild.commonservice.commands;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RollbackBookStatusCommand {

    @TargetAggregateIdentifier
    private String bookId;
    private String employeeId;
    private String borrowId;
    private Boolean isAvailable;
}
