package com.kafka.consumer.config;

import com.rinkul.avro.schema.EmployeeRecord;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerConfig.class);

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    @Value("${spring.kafka.bootstrap-servers-compose}")
    private String kafkaServerForDockerCompose;

    @Value("${spring.kafka.consumer.properties.schema.registry.url}")
    private String schemaRegistryUrl;

    @Value("${spring.kafka.consumer.properties.DockerComposeSchemaUrl}")
    private String dockerComposeSchemaRegistryUrl;

    @Value("${retry.attempts}")
    private int retryAttempts;

    @Value("${USE_DOCKER_COMPOSE:false}")
    private boolean useDockerCompose;
    @Value("${retry.backoff.initialInterval}")
    private long initialBackOffInterval;

    @Value("${retry.backoff.multiplier}")
    private double backOffMultiplier;

    @Value("${avro.dlt.name}")
    private String topicForWrongSchema;

    // Main Consumer Factory for Avro-serialized main topic
    @Bean
    public ConsumerFactory<String, EmployeeRecord> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        String finalSchemaRegistryUrl = useDockerCompose ? dockerComposeSchemaRegistryUrl : schemaRegistryUrl;
        String finalKafkaServer = useDockerCompose ? kafkaServerForDockerCompose : kafkaServer;


        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.20.0.1:9092"); //"172.20.0.1:9092"
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, KafkaAvroDeserializer.class);
        config.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        config.put("schema.registry.url", "http://localhost:8081"); //"http://schema-registry:8081"
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EmployeeRecord> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, EmployeeRecord> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        ExponentialBackOffWithMaxRetries backOff = new ExponentialBackOffWithMaxRetries(retryAttempts);
        backOff.setInitialInterval(initialBackOffInterval);
        backOff.setMultiplier(backOffMultiplier);

        DefaultErrorHandler errorHandler = new DefaultErrorHandler(backOff);
        errorHandler.setRetryListeners((record, ex, deliveryAttempt) -> {
            LOGGER.info("Retry attempt {} for record: {}", deliveryAttempt, record);
        });

        factory.setCommonErrorHandler(errorHandler);

        return factory;
    }

    // DLT Consumer Factory with StringDeserializer for generic object handling
    @Bean
    public ConsumerFactory<String, String> dltConsumerFactory() {
        Map<String, Object> config = new HashMap<>();

        String finalSchemaRegistryUrl = useDockerCompose ? dockerComposeSchemaRegistryUrl : schemaRegistryUrl;
        String finalKafkaServer = useDockerCompose ? kafkaServerForDockerCompose : kafkaServer;


        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.20.0.1:9092");//"172.20.0.1:9092"
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, KafkaAvroDeserializer.class);
        config.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        config.put("schema.registry.url", "http://localhost:8081"); //"http://schema-registry:8081"
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> dltKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(dltConsumerFactory());

        DefaultErrorHandler errorHandler = new DefaultErrorHandler();
        factory.setCommonErrorHandler(errorHandler);

        return factory;
    }

    // DLT Producer Factory
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        String finalSchemaRegistryUrl = useDockerCompose ? dockerComposeSchemaRegistryUrl : schemaRegistryUrl;
        String finalKafkaServer = useDockerCompose ? kafkaServerForDockerCompose : kafkaServer;

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.20.0.1:9092"); //"172.20.0.1:9092"
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        config.put("schema.registry.url", "http://localhost:8081"); //"http://schema-registry:8081"

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public DeadLetterPublishingRecoverer deadLetterPublishingRecoverer() {
        return new DeadLetterPublishingRecoverer(kafkaTemplate(), (record, ex) -> {
            LOGGER.error("Publishing to DLT due to exception: {}", ex.getMessage());
            return new org.apache.kafka.common.TopicPartition(topicForWrongSchema, record.partition());
        });
    }


}
