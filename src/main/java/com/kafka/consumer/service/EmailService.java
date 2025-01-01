package com.kafka.consumer.service;

import com.rinkul.avro.schema.EmployeeRecord;
import jakarta.mail.MessagingException;

import java.io.IOException;

public interface EmailService {


    public void sendEmailToEmployee(EmployeeRecord employee, String subject) throws MessagingException, IOException;

}
