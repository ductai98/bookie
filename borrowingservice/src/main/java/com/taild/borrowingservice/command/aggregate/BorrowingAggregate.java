package com.taild.borrowingservice.command.aggregate;


import com.taild.borrowingservice.command.commands.CreateBorrowingCommand;
import com.taild.borrowingservice.command.commands.DeleteBorrowingCommand;
import com.taild.borrowingservice.command.event.BorrowingCreatedEvent;
import com.taild.borrowingservice.command.event.BorrowingDeletedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;

@Aggregate
@NoArgsConstructor
public class BorrowingAggregate {

    @AggregateIdentifier
    private String id;

    private String bookId;
    private String employeeId;
    private Date borrowDate;
    private Date returnDate;


    @CommandHandler
    public BorrowingAggregate(CreateBorrowingCommand command) {
        AggregateLifecycle.apply(
                new BorrowingCreatedEvent(command.getId(),
                        command.getBookId(),
                        command.getEmployeeId(),
                        command.getBorrowDate()));
    }


    @CommandHandler
    public void handle(DeleteBorrowingCommand command) {
        AggregateLifecycle.apply(new BorrowingDeletedEvent(command.getId()));
    }



    @EventSourcingHandler
    public void on(BorrowingCreatedEvent event) {
        this.id = event.getId();
        this.bookId = event.getBookId();
        this.employeeId = event.getEmployeeId();
        this.borrowDate = event.getBorrowDate();
    }

    @EventSourcingHandler
    public void on(BorrowingDeletedEvent event) {
        this.id = event.getId();
        this.bookId = null;
        this.employeeId = null;
        this.borrowDate = null;
        this.returnDate = null;
    }
}
