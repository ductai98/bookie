package com.taild.employeeservice.command.aggregate;

import com.taild.employeeservice.command.commands.CreateEmployeeCommand;
import com.taild.employeeservice.command.event.EmployeeCreatedEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
@Getter
@Setter
public class EmployeeAggregate {

    @AggregateIdentifier
    private String id;

    private String firstName;

    private String lastName;

    private String kin;

    private Boolean hasDisciplined;

    @CommandHandler
    public EmployeeAggregate(CreateEmployeeCommand command) {
        EmployeeCreatedEvent event = new EmployeeCreatedEvent(
                command.getId(),
                command.getFirstName(),
                command.getLastName(),
                command.getKin(),
                command.getHasDisciplined());

        AggregateLifecycle.apply(event);
    }


    @EventSourcingHandler
    public void handle(EmployeeCreatedEvent event) {
        this.id = event.getId();
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        this.kin = event.getKin();
        this.hasDisciplined = event.getHasDisciplined();
    }
}
