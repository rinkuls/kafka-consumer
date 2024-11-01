package com.kafka.consumer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.kafka.consumer.model.Student;
import com.rinkul.avro.schema.StudentRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class KafkaReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaReceiver.class);
    @Autowired
    private RestTemplate restTemplate;


    // @RetryableTopic(attempts = "2")
    // @RetryableTopic(attempts = "2",backoff = @Backoff(delay = 5000L,multiplier = 1.2,maxDelay = 15000L),exclude = {NullPointerException.class})

    @KafkaListener(topics = "${avro.topic.name}", groupId = "${spring.kafka.group.id}", containerFactory = "kafkaListenerContainerFactory")
    public void read(ConsumerRecord<String, StudentRecord> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) long offset) throws JsonProcessingException {

        LOGGER.info(" hey i got something and in reciver received");
        var createPersonUrl = "http://192.168.178.24:8080/student/add";
        String key = record.key();
        StudentRecord studentRecord = record.value();

        LOGGER.info("Data - " + studentRecord + " received");
        LOGGER.info("topic - " + topic + " received");
        LOGGER.info("offset - " + offset + " received");
        var headers = new HttpHeaders();


       // headers.setContentType(MediaType.APPLICATION_JSON);

        //one approach

        //ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        //String jsonObjectFromJava = ow.writeValueAsString(Student.builder().age(studentRecord.getAge()).empId(studentRecord.getEmpId()).firstName(studentRecord.getFirstName().toString()).lastName(studentRecord.getLastName().toString()).build());
        //HttpEntity<String> request =
          //      new HttpEntity<>(jsonObjectFromJava, headers);
        //ResponseEntity<String> responseEntityStr = restTemplate.
          //      postForEntity(createPersonUrl, request, String.class);
       }

    @DltHandler
    public void listenDLT(ConsumerRecord<String, StudentRecord> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) long offset) {
        LOGGER.info("Data - " + record + " sent to DLT TOPIC Please check what is wrong");
        LOGGER.info("topic - " + topic + " sent to DLT TOPIC Please check what is wrong");
        LOGGER.info("offset - " + offset + " sent to DLT TOPIC Please check what is wrong");
    }

}
