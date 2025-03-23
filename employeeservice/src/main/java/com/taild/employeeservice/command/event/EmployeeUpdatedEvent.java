package com.taild.employeeservice.command.event;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EmployeeUpdatedEvent {

    private String id;

    private String firstName;

    private String lastName;

    private String kin;

    private Boolean hasDisciplined;
}
