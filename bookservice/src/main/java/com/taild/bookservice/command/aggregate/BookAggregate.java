package com.taild.bookservice.command.aggregate;


import com.taild.bookservice.command.commands.CreateBookCommand;
import com.taild.bookservice.command.commands.DeleteBookCommand;
import com.taild.bookservice.command.commands.UpdateBookCommand;
import com.taild.bookservice.command.event.BookCreateEvent;
import com.taild.bookservice.command.event.BookDeletedEvent;
import com.taild.bookservice.command.event.BookUpdatedEvent;
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
public class BookAggregate {

    @AggregateIdentifier
    private String id;

    private String name;

    private String author;

    private Boolean isAvailable;

    @CommandHandler
    public BookAggregate(CreateBookCommand command) { // Create a new book
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

    @CommandHandler
    public void handle(UpdateBookCommand command) { // Update an existing book
        BookUpdatedEvent event = new BookUpdatedEvent(
                command.getId(),
                command.getName(),
                command.getAuthor(),
                command.getIsAvailable());

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BookUpdatedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.author = event.getAuthor();
        this.isAvailable = event.getIsAvailable();
    }

    @CommandHandler
    public void handle(DeleteBookCommand command) { // Delete a book
        AggregateLifecycle.apply(new BookDeletedEvent(command.getId()));
    }

    @EventSourcingHandler
    public void on(BookDeletedEvent event) {
        this.id = event.getId();
    }
}
