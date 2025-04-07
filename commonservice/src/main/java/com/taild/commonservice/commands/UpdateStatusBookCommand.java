package com.taild.commonservice.commands;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusBookCommand {

    private String bookId;
    private boolean isAvailable;
    private String employeeId;
    private String borrowId;
}
