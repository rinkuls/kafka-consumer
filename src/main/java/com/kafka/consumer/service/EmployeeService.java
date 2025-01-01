package com.kafka.consumer.service;

import com.kafka.consumer.model.Employee;
import com.rinkul.avro.schema.EmployeeRecord;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.Optional;

public interface EmployeeService {
    public Optional<Employee> findByEmail(String email);

    public Employee saveEmployeeRecord(Employee employee);

    public boolean checkEmployeeRecordExist(String email);

    public void sendNotificationToEmployee(EmployeeRecord employee) throws MessagingException, IOException;
}
