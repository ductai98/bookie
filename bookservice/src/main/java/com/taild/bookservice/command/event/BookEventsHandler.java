package com.taild.bookservice.command.event;


import com.taild.bookservice.command.data.Book;
import com.taild.bookservice.command.data.BookRepository;
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
}
