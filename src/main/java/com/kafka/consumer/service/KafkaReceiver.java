package com.kafka.consumer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rinkul.avro.schema.StudentRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class KafkaReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaReceiver.class);

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Value("${dbservice.url}")
    private String dbServiceUrl;

    public KafkaReceiver() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    @KafkaListener(topics = "${avro.topic.name}", groupId = "${spring.kafka.group.id}", containerFactory = "kafkaListenerContainerFactory")
    public void read(ConsumerRecord<String, StudentRecord> record,
                     @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                     @Header(KafkaHeaders.OFFSET) long offset) {
        LOGGER.info("Received data - {}", record.value());
        LOGGER.info("Received from topic: {}", topic);
        LOGGER.info("Offset: {}", offset);
        LOGGER.info("Key: {}", record.key());
        LOGGER.info("dbServiceUrl++++++++++++++++++++++++++++++++++++++++++++++++", dbServiceUrl);

        // Modify the StudentRecord object as required
        StudentRecord modifiedRecord = modifyStudentRecord(record.value());

        // Send the modified record to dbService
        sendToDbService(modifiedRecord);
    }

    private StudentRecord modifyStudentRecord(StudentRecord original) {

        LOGGER.info("Modified StudentRecord: {}", original);
        return original;
    }

    private void sendToDbService(StudentRecord studentRecord) {
        try {
            // Convert the StudentRecord object to JSON
            String jsonPayload = objectMapper.writeValueAsString(studentRecord);

            // Build the HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(dbServiceUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            // Send the request and handle the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                LOGGER.info("Successfully sent data to dbService: {}", response.body());
            } else {
                LOGGER.error("Failed to send data to dbService. HTTP Status: {}. Response: {}",
                        response.statusCode(), response.body());
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred while sending data to dbService: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = "${avro.dlt.name}", groupId = "${avro.dlt.group}", containerFactory = "dltKafkaListenerContainerFactory")
    public void listenDLT(ConsumerRecord<String, String> record,
                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                          @Header(KafkaHeaders.OFFSET) long offset) {
        LOGGER.error("DLT Processing - Data: {} sent to DLT due to error in processing", record.value());
        LOGGER.error("DLT topic: {}", topic);
        LOGGER.error("DLT offset: {}", offset);
    }
}
