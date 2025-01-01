package com.kafka.consumer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.consumer.model.Employee;
import com.kafka.consumer.model.ExceptionEmailNotSent;
import com.kafka.consumer.repo.EmailNotSentRepo;
import com.kafka.consumer.repo.EmployeeRepo;
import com.rinkul.avro.schema.EmployeeRecord;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    @Autowired
    private EmailService emailService;
    @Autowired
    private EmailNotSentRepo emailNotSentRepo;
    @Autowired
    private EmployeeRepo employeeRepo;
    @Value("${dbservice.dockerEmployeeServiceUrl}")
    private String dockerEmployeeServiceUrl;
    @Value("${dbservice.clusterEmployeeServiceUrl}")
    private String clusterEmployeeServiceUrl;
    @Value("${USE_DOCKER_COMPOSE:false}")
    private boolean useDockerCompose;

    @Override
    @Transactional
    public Optional<Employee> findByEmail(String email) {

        return employeeRepo.findByEmail(email);
    }

    @Override
    @Transactional
    public Employee saveEmployeeRecord(Employee employee) {

        Optional.ofNullable(employee.getProfessionalDetails())
                .ifPresent(professionalDetails -> professionalDetails.setEmployee(employee));

        Optional.ofNullable(employee.getPastEmployments())
                .ifPresent(pastEmployments ->
                        pastEmployments.forEach(pastEmployment -> pastEmployment.setEmployee(employee)));

        Optional.ofNullable(employee.getKids())
                .ifPresent(kids ->
                        kids.forEach(kid -> kid.setEmployee(employee)));

        Optional.ofNullable(employee.getSpouse())
                .ifPresent(spouse -> spouse.setEmployee(employee));
        var retVal = employeeRepo.save(employee);
        sendToEmployeeManagementToolService(retVal);
        return retVal;
    }

    @Override
    @Transactional
    public boolean checkEmployeeRecordExist(String email) {

        return findByEmail(email).isPresent();
    }

    @Override
    @Transactional
    public void sendNotificationToEmployee(EmployeeRecord employee) throws MessagingException, IOException {  // need to add exception table as well TODO
        if (!checkEmployeeRecordExist(employee.getEmail().toString())) {
            ExceptionEmailNotSent exceptionEmailNotSent = ExceptionEmailNotSent.builder().build();
            exceptionEmailNotSent.setEmpId(employee.getEmpId());
            exceptionEmailNotSent.setFirstName(employee.getFirstName().toString());
            exceptionEmailNotSent.setLastName(employee.getLastName().toString());
            exceptionEmailNotSent.setEmail(employee.getEmail().toString());
            exceptionEmailNotSent.setExceptionReceived(true);
            emailNotSentRepo.save(exceptionEmailNotSent);
            emailService.sendEmailToEmployee(employee, "Urgent Data Update Request");
            exceptionEmailNotSent.setEmailSent(true);
            emailNotSentRepo.save(exceptionEmailNotSent);
        }


    }

    private void sendToEmployeeManagementToolService(Employee retVal) {
        try {
            var empToolUrl = useDockerCompose ? dockerEmployeeServiceUrl : clusterEmployeeServiceUrl;
            LOGGER.info("-----------------------emptool url is below ---------------------", empToolUrl);
            var jsonEmployeePayload = objectMapper.writeValueAsString(retVal);
            // Build the HTTP request
            var request = HttpRequest.newBuilder()
                    .uri(URI.create(empToolUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonEmployeePayload))
                    .build();

            // Send the request and handle the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                LOGGER.info("Successfully sent data to dbService: {}", response.body());
            } else {
                throw new RuntimeException("Service is down");
            }
        } catch (Exception e) {
            throw new RuntimeException("Service is down");
        }
    }

}
