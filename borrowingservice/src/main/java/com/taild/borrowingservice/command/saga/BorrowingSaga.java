package com.taild.borrowingservice.command.saga;


import com.taild.borrowingservice.command.event.BorrowingCreatedEvent;
import com.taild.commonservice.commands.UpdateStatusBookCommand;
import com.taild.commonservice.model.BookResponseCommonModel;
import com.taild.commonservice.queries.GetBookDetailsQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
@Slf4j
public class BorrowingSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    private void handle(BorrowingCreatedEvent event) {
        log.info("Starting saga for borrowing, bookId = {}, employeeId = {}", event.getBookId(), event.getEmployeeId());

        try {

            GetBookDetailsQuery query = new GetBookDetailsQuery(event.getBookId());
            BookResponseCommonModel bookDetails = queryGateway.query(query, BookResponseCommonModel.class).join();

            if (bookDetails.getIsAvailable()) {
                SagaLifecycle.associateWith("bookId", event.getBookId());
                UpdateStatusBookCommand command = new UpdateStatusBookCommand(
                        event.getBookId(),
                        false,
                        event.getEmployeeId(),
                        event.getId()
                );
                commandGateway.sendAndWait(command);
            } else {
                throw new Exception("Book is not available");
            }

        } catch (Exception e) {
            rollbackBorrowing(event.getId());
            log.error("Error during saga execution", e);
        }
    }

    private void rollbackBorrowing(String id) {

    }

    @SagaEventHandler(associationProperty = "bookId")
    private void handle(UpdateStatusBookCommand command) {
        log.info("Saga updating book status, bookId = {}, isAvailable = {}", command.getBookId(), command.isAvailable());
        SagaLifecycle.end();
    }

}
