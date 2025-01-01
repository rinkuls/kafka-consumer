package com.kafka.consumer.service;

import com.rinkul.avro.schema.EmployeeRecord;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class KafkaReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaReceiver.class);

    @Autowired
    private EmployeeService employeeService;


    @KafkaListener(topics = "${avro.topic.name}", groupId = "${spring.kafka.group.id}", containerFactory = "kafkaListenerContainerFactory")
    @RetryableTopic(attempts = "4")// 3 topic N-1
    @Transactional
    public void read(EmployeeRecord employeeRecord,
                     @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                     @Header(KafkaHeaders.OFFSET) long offset) {
        LOGGER.info("Received data to raise Exception - {}", employeeRecord);
        try {
            employeeService.sendNotificationToEmployee(employeeRecord);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    @DltHandler
    public void listenDLT(EmployeeRecord record,
                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                          @Header(KafkaHeaders.OFFSET) long offset) {
        LOGGER.info("DLT Received because Email was not sent to Employee  : {} , from {} , offset {}", record.getEmail(), topic, offset);

    }

    @KafkaListener(topics = "${avro.dlt.name}", groupId = "${avro.dlt.group}", containerFactory = "dltKafkaListenerContainerFactory")
    public void listenDLT(ConsumerRecord<String, String> record,
                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                          @Header(KafkaHeaders.OFFSET) long offset) {
        LOGGER.error("topicForWrongSchema Processing - Data: {} sent to topicForWrongSchema due to wrong schema of messages in processing", record.value());

    }
}
