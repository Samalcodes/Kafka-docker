package com.training.employeeservice.service;

import com.training.employeeservice.dto.EmployeeRequest;
import com.training.employeeservice.kafka.EmployeeKafkaProducer;
import com.training.employeeservice.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    private final List<Employee> employeeStore = Collections.synchronizedList(new ArrayList<>());
    private final EmployeeKafkaProducer kafkaProducer;

    public EmployeeService(EmployeeKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    public Employee createEmployee(EmployeeRequest request) {
        Employee employee = new Employee(
                UUID.randomUUID().toString(),
                request.getName().trim(),
                request.getEmail().trim(),
                request.getDepartment().trim()
        );

        employeeStore.add(employee);
        log.info("Saved employee in memory: {}", employee);

        kafkaProducer.sendEmployeeCreatedEvent(employee);
        return employee;
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employeeStore);
    }
}
