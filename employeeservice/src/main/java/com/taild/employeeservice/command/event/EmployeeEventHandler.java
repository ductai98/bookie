package com.taild.employeeservice.command.event;


import com.taild.commonservice.utils.StringUtils;
import com.taild.employeeservice.command.data.Employee;
import com.taild.employeeservice.command.data.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.DisallowReplay;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
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

    @EventHandler
    public void on(EmployeeUpdatedEvent event) {
        Employee employee = employeeRepository.findById(event.getId())
               .orElseThrow(() -> new RuntimeException("Employee not found id = " + event.getId()));
        if (!StringUtils.isNullOrEmpty(event.getFirstName())) {
            employee.setFirstName(event.getFirstName());
        }

        if (!StringUtils.isNullOrEmpty(event.getLastName())) {
            employee.setLastName(event.getLastName());
        }

        if (!StringUtils.isNullOrEmpty(event.getKin())) {
            employee.setKin(event.getKin());
        }

        if (event.getHasDisciplined() != null) {
            employee.setHasDisciplined(event.getHasDisciplined());
        }

        employeeRepository.save(employee);
    }

    @EventHandler
    @DisallowReplay
    public void on(EmployeeDeletedEvent event) {

        try {
            employeeRepository.findById(event.getId()).orElseThrow(
                    () -> new RuntimeException("Employee not found id = " + event.getId())
            );
        } catch (RuntimeException e) {
             log.error(e.getMessage());        }


        employeeRepository.deleteById(event.getId());
    }
}
