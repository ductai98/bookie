package com.taild.employeeservice.command.event;


import com.taild.employeeservice.command.data.Employee;
import com.taild.employeeservice.command.data.EmployeeRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeEventHandler {

    @Autowired
    private EmployeeRepository employeeRepository;

    @EventHandler
    public void on(EmployeeCreatedEvent event) {
        Employee employee = new Employee(
                event.getId(),
                event.getFirstName(),
                event.getLastName(),
                event.getKin(),
                event.getHasDisciplined());

        employeeRepository.save(employee);
    }
}
