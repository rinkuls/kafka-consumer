package com.kafka.consumer.service;

import com.rinkul.avro.schema.StudentRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class KafkaReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaReceiver.class);

    @KafkaListener(topics = "${avro.topic.name}", groupId = "${spring.kafka.group.id}", containerFactory = "kafkaListenerContainerFactory")
    public void read(ConsumerRecord<String, StudentRecord> record,
                     @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                     @Header(KafkaHeaders.OFFSET) long offset) {

        LOGGER.info("Received data - {}", record.value());
        LOGGER.info("Received from topic: {}", topic);
        LOGGER.info("Offset: {}", offset);
        LOGGER.info("Key: {}", record.key());

        // Simulating exception for testing DLT functionality
        if (record.value().equals("Error")) {
            throw new RuntimeException("Simulated exception for testing DLT");
        }
    }

    @DltHandler
    public void listenDLT(ConsumerRecord<String, Object> record,
                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                          @Header(KafkaHeaders.OFFSET) long offset) {

        LOGGER.error("Data - {} sent to DLT due to error processing", record.value());
        LOGGER.error("DLT topic: {}", topic);
        LOGGER.error("DLT offset: {}", offset);
    }
}
