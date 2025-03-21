package com.taild.bookservice.command.aggregate;


import com.taild.bookservice.command.commands.CreateBookCommand;
import com.taild.bookservice.command.event.BookCreateEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
public class BookAggregate {

    @AggregateIdentifier
    private String id;

    private String name;

    private String author;

    private Boolean isAvailable;

    @CommandHandler
    public void handle(CreateBookCommand command) {
        BookCreateEvent event = new BookCreateEvent(
                command.getId(),
                command.getName(),
                command.getAuthor(),
                command.getIsAvailable());

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BookCreateEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.author = event.getAuthor();
        this.isAvailable = event.getIsAvailable();
    }
}
