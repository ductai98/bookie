package com.taild.commonservice.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookStatusRollBackedEvent {

    private String bookId;
    private String employeeId;
    private String borrowId;
    private Boolean isAvailable;
}
