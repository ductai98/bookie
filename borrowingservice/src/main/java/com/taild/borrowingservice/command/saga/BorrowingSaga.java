package com.taild.borrowingservice.command.saga;


import com.taild.borrowingservice.command.commands.DeleteBorrowingCommand;
import com.taild.borrowingservice.command.event.BorrowingCreatedEvent;
import com.taild.borrowingservice.command.event.BorrowingDeletedEvent;
import com.taild.commonservice.commands.RollbackBookStatusCommand;
import com.taild.commonservice.commands.UpdateBookStatusCommand;
import com.taild.commonservice.event.BookStatusRollBackedEvent;
import com.taild.commonservice.event.BookStatusUpdatedEvent;
import com.taild.commonservice.model.BookResponseCommonModel;
import com.taild.commonservice.model.EmployeeResponseCommonModel;
import com.taild.commonservice.queries.GetBookDetailsQuery;
import com.taild.commonservice.queries.GetEmployeeDetailsQuery;
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
                UpdateBookStatusCommand command = new UpdateBookStatusCommand(
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


    @SagaEventHandler(associationProperty = "bookId")
    private void handle(BookStatusUpdatedEvent command) {
        log.info("Saga updating book status, bookId = {}, isAvailable = {}", command.getBookId(), command.isAvailable());

        try {
            GetEmployeeDetailsQuery query = new GetEmployeeDetailsQuery(command.getEmployeeId());
            EmployeeResponseCommonModel model = queryGateway.query(query, EmployeeResponseCommonModel.class).join();
            if (model.isHasDisciplined()) {
                throw new Exception("Employee has disciplined, rolling back borrowing");
            } else {
                log.info("Book borrowing successful");
                SagaLifecycle.end();
            }
        } catch (Exception e) {
            log.error("Error during saga execution", e);
            rollBackBookStatus(command.getBookId(), command.getEmployeeId(), command.getBorrowId());

        }
    }

    private void rollbackBorrowing(String id) {
        DeleteBorrowingCommand command = new DeleteBorrowingCommand(id);
        commandGateway.sendAndWait(command);
        SagaLifecycle.end();
    }

    private void rollBackBookStatus(String bookId, String employeeId, String borrowingId) {
        SagaLifecycle.associateWith("bookId", bookId);
        RollbackBookStatusCommand command = new RollbackBookStatusCommand(bookId, employeeId, borrowingId, true);
        commandGateway.sendAndWait(command);
    }

    @SagaEventHandler(associationProperty = "bookId")
    private void handle(BookStatusRollBackedEvent event) {
        log.info("Rolling back book status, bookId = {}", event.getBookId());
        rollbackBorrowing(event.getBorrowId());
    }

    @SagaEventHandler(associationProperty = "id")
    public void handle(BorrowingDeletedEvent event) {
        log.info("Deleting borrowing, id = {}", event.getId());
        SagaLifecycle.end();
    }

}
