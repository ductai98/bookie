package com.taild.commonservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseCommonModel {

    private String id;

    private String firstName;

    private String lastName;

    private String kin;

    private boolean hasDisciplined;
}
