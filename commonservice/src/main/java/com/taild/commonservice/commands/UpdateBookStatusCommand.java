package com.taild.commonservice.commands;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookStatusCommand {

    @TargetAggregateIdentifier
    private String bookId;
    private boolean isAvailable;
    private String employeeId;
    private String borrowId;
}
