package com.taild.borrowingservice.command.event;


import com.taild.borrowingservice.command.data.Borrowing;
import com.taild.borrowingservice.command.data.BorrowingRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BorrowingEventHandler {

    @Autowired
    private BorrowingRepository borrowingRepository;

    @EventHandler
    public void on(BorrowingCreatedEvent event) {
        Borrowing entity = new Borrowing(event.getId(),
                event.getBookId(),
                event.getEmployeeId(),
                event.getBorrowDate(),
                null);
        borrowingRepository.save(entity);
    }

    @EventHandler
    public void on(BorrowingDeletedEvent event) {
        Optional<Borrowing> borrowing = borrowingRepository.findById(event.getId());
        borrowing.ifPresent(b -> borrowingRepository.delete(b));
    }
}
