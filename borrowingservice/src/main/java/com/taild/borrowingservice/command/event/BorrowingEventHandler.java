package com.taild.borrowingservice.command.event;


import com.taild.borrowingservice.command.data.Borrowing;
import com.taild.borrowingservice.command.data.BorrowingRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
