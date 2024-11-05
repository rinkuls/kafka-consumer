package com.kafka.consumer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
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
    public void read(ConsumerRecord<String, StudentRecord> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) long offset) throws JsonProcessingException {

        LOGGER.info(" hey i got something and in receiver received");
        StudentRecord studentRecord = record.value();

        LOGGER.info("Data - " + studentRecord + " received");
        LOGGER.info("topic - " + topic + " received");
        LOGGER.info("offset - " + offset + " received");
        LOGGER.info("key - " + record.key() + " received");


    }

    @DltHandler
    public void listenDLT(ConsumerRecord<String, StudentRecord> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) long offset) {
        LOGGER.info("Data - " + record + " sent to DLT TOPIC Please check what is wrong");
        LOGGER.info("topic - " + topic + " sent to DLT TOPIC Please check what is wrong");
        LOGGER.info("offset - " + offset + " sent to DLT TOPIC Please check what is wrong");
    }

}
