package com.taild.bookservice.command.event;


import com.taild.bookservice.command.data.Book;
import com.taild.bookservice.command.data.BookRepository;
import com.taild.commonservice.event.BookStatusUpdatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookEventsHandler {

    @Autowired
    private BookRepository bookRepository;

    @EventHandler
    public void on(BookCreateEvent event) {
        // Handle the BookCreateEvent
        Book book = new Book(event.getId(), event.getName(), event.getAuthor(), event.getIsAvailable());
        bookRepository.save(book);
    }

    @EventHandler
    public void on(BookUpdatedEvent event) {
        // Handle the BookUpdatedEvent
        Book book = bookRepository.findById(event.getId())
                .orElseThrow(() -> new RuntimeException("Book not found id = " + event.getId()));
        if (event.getName()!= null && !event.getName().isEmpty() && !event.getName().equals(book.getName())) {
            book.setName(event.getName());
        }

        if (event.getAuthor()!= null && !event.getAuthor().isEmpty() && !event.getAuthor().equals(book.getAuthor())) {
            book.setAuthor(event.getAuthor());
        }

        if (event.getIsAvailable()!= null) {
            book.setIsAvailable(event.getIsAvailable());
        }

        bookRepository.save(book);
    }

    @EventHandler
    public void on(BookDeletedEvent event) {
        // Handle the BookDeletedEvent
        bookRepository.deleteById(event.getId());
    }

    @EventHandler
    public void on(BookStatusUpdatedEvent event) {
        Book book = bookRepository.findById(event.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found id = " + event.getBookId()));

        book.setIsAvailable(event.isAvailable());

        bookRepository.save(book);
    }

}
