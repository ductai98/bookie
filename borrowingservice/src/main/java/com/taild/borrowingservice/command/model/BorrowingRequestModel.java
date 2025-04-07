package com.taild.borrowingservice.command.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowingRequestModel {

    private String id;

    private String bookId;
    private String employeeId;
    private String borrowDate;
    private String returnDate;
}
