package com.kafka.consumer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.consumer.model.Student;
import com.kafka.consumer.model.StudentRecordNotSent;
import com.kafka.consumer.repo.StudentRecord;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Transactional
@RequiredArgsConstructor
public class KafkaReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaReceiver.class);
    private final StudentRecord studentRecord;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    @Value("${dbservice.url}")
    private String dbServiceUrl;


    @KafkaListener(topics = "${avro.topic.name}", groupId = "${spring.kafka.group.id}", containerFactory = "kafkaListenerContainerFactory")
    @RetryableTopic(attempts = "4")// 3 topic N-1
    public void read(com.rinkul.avro.schema.StudentRecord record,
                     @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                     @Header(KafkaHeaders.OFFSET) long offset) {
        LOGGER.info("Received data - {}", record);
        sendToDbService(record);
    }


    private Student convertToStudent(com.rinkul.avro.schema.StudentRecord studentRecord) {
        Student student = Student.builder().build();
        student.setEmpId(studentRecord.getEmpId());
        student.setFirstName(studentRecord.getFirstName().toString()); // Use toString() to handle Utf8
        student.setLastName(studentRecord.getLastName().toString());  // Use toString() to handle Utf8
        student.setAge(studentRecord.getAge());
        return student;
    }


    private void sendToDbService(com.rinkul.avro.schema.StudentRecord studentRecord) {
        try {
            // Convert the StudentRecord object to JSON
            String jsonPayload = objectMapper.writeValueAsString(convertToStudent(studentRecord));

            // Build the HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://todo-management-release.my-db.svc.cluster.local:8780/student/add"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
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

    @DltHandler
    public void listenDLT(com.rinkul.avro.schema.StudentRecord record,
                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                          @Header(KafkaHeaders.OFFSET) long offset) {
        LOGGER.info("DLT Received because data was not sent to db service  : {} , from {} , offset {}", record.getFirstName(), topic, offset);
        LOGGER.info("+++++++++++++++++Now saving to temp DB and this data should be processes again by some JOB once DB service is up" +
                "++++++++++++++++++++ ");
        StudentRecordNotSent studentRecordNotSent = StudentRecordNotSent.builder().build();
        studentRecordNotSent.setEmpId(record.getEmpId());
        studentRecordNotSent.setFirstName(record.getFirstName().toString()); // Use toString() to handle Utf8
        studentRecordNotSent.setLastName(record.getLastName().toString());  // Use toString() to handle Utf8
        studentRecordNotSent.setAge(record.getAge());
        studentRecord.save(studentRecordNotSent);
    }

    @KafkaListener(topics = "${avro.dlt.name}", groupId = "${avro.dlt.group}", containerFactory = "dltKafkaListenerContainerFactory")
    public void listenDLT(ConsumerRecord<String, String> record,
                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                          @Header(KafkaHeaders.OFFSET) long offset) {
        LOGGER.error("topicForWrongSchema Processing - Data: {} sent to topicForWrongSchema due to wrong schema of messages in processing", record.value());
        LOGGER.error("topicForWrongSchema topic: {}", topic);
        LOGGER.error("topicForWrongSchema offset: {}", offset);
    }
}
