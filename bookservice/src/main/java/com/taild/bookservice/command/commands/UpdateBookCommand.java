package com.taild.bookservice.command.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateBookCommand {

    @TargetAggregateIdentifier
    private String id;

    private String name;

    private String author;

    private Boolean isAvailable;
}
