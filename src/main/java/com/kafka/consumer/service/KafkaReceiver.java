package com.kafka.consumer.service;

import com.rinkul.avro.schema.StudentRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
