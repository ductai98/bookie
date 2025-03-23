package com.taild.employeeservice.command.aggregate;

import com.taild.commonservice.utils.StringUtils;
import com.taild.employeeservice.command.commands.CreateEmployeeCommand;
import com.taild.employeeservice.command.commands.UpdateEmployeeCommand;
import com.taild.employeeservice.command.event.EmployeeCreatedEvent;
import com.taild.employeeservice.command.event.EmployeeUpdatedEvent;
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

    @CommandHandler
    public void handle(UpdateEmployeeCommand command) {
        EmployeeUpdatedEvent event = new EmployeeUpdatedEvent(
                command.getId(),
                command.getFirstName(),
                command.getLastName(),
                command.getKin(),
                command.getHasDisciplined()
        );

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void handle(EmployeeUpdatedEvent event) {
        if (StringUtils.isNullOrEmpty(event.getFirstName())) {
            this.firstName = event.getFirstName();
        }
        if (StringUtils.isNullOrEmpty(event.getLastName())) {
            this.lastName = event.getLastName();
        }
        if (StringUtils.isNullOrEmpty(event.getKin())) {
            this.kin = event.getKin();
        }
        if (this.hasDisciplined != null) {
            this.hasDisciplined = event.getHasDisciplined();
        }
    }
}
