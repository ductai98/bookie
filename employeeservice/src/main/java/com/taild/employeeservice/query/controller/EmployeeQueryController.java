package com.taild.employeeservice.query.controller;


import com.taild.employeeservice.query.model.EmployeeResponseModel;
import com.taild.employeeservice.query.queries.GetAllEmployeeQuery;
import com.taild.employeeservice.query.queries.GetDetailEmployeeQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping
    public List<EmployeeResponseModel> getAllEmployees(@RequestParam(required = false) Boolean hasDisciplined) {
        return queryGateway
                .query(new GetAllEmployeeQuery(hasDisciplined),
                        ResponseTypes.multipleInstancesOf(EmployeeResponseModel.class)
                ).join();
    }

    @GetMapping("/{id}")
    public EmployeeResponseModel getEmployeeById(@PathVariable String id) {
        return queryGateway
                .query(new GetDetailEmployeeQuery(id),
                        ResponseTypes.instanceOf(EmployeeResponseModel.class)
                ).join();
    }
}
