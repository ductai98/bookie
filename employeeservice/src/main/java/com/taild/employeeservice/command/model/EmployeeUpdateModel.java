package com.taild.employeeservice.command.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeUpdateModel {

    private String firstName;

    private String lastName;

    private String kin;

    private Boolean hasDisciplined;
}
