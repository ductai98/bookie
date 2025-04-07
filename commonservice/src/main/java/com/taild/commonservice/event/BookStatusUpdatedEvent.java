package com.taild.commonservice.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookStatusUpdatedEvent {

    private String bookId;
    private boolean isAvailable;
    private String employeeId;
    private String borrowId;
}
