package com.taild.employeeservice.query.projection;


import com.taild.employeeservice.command.data.Employee;
import com.taild.employeeservice.command.data.EmployeeRepository;
import com.taild.employeeservice.query.model.EmployeeResponseModel;
import com.taild.employeeservice.query.queries.GetAllEmployeeQuery;
import com.taild.employeeservice.query.queries.GetDetailEmployeeQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class EmployeeProjection {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @QueryHandler
    public List<EmployeeResponseModel> handle(GetAllEmployeeQuery query) {
        List<Employee> list;
        if (query.getHasDisciplined() == null) {
            list = employeeRepository.findAll();
        } else {
            list = employeeRepository.findAllByHasDisciplined(query.getHasDisciplined());
        }

        return list.stream().map(
                employee -> modelMapper.map(employee, EmployeeResponseModel.class)
        ).toList();
    }

    @QueryHandler
    public EmployeeResponseModel handle(GetDetailEmployeeQuery query) {
        try {
            Employee employee = employeeRepository.findById(query.getId())
                    .orElseThrow(() -> new RuntimeException("Employee not found id = " + query.getId()));

            return modelMapper.map(employee, EmployeeResponseModel.class);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
