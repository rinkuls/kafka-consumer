package com.kafka.consumer.config;

import com.rinkul.avro.schema.StudentRecord;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.DeserializationException;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerConfig.class);
    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;
    @Value("${spring.kafka.consumer.properties.schema.registry.url}")
    private String schemaRegistryUrl;

    @Bean
    public ConsumerFactory<String, StudentRecord> consumerFactory() {
        Map<String, Object> config = new HashMap<>();

        // Set up Kafka server and group ID
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);

        // Use ErrorHandlingDeserializer with delegate deserializers
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);

        // Configure delegate deserializers
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, KafkaAvroDeserializer.class.getName());
        config.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, KafkaAvroDeserializer.class.getName());

        // Specific Avro configuration
        config.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        config.put("schema.registry.url", schemaRegistryUrl);

        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, StudentRecord>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, StudentRecord> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        // Configure error handler
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
                (consumerRecord, exception) -> {
                    if (exception instanceof DeserializationException) {
                        // Handle deserialization exceptions, such as logging them
                        LOGGER.info(" hey i got some error DeserializationException");
                        System.err.println("Deserialization error for record: " + consumerRecord + ", exception: " + exception);
                    } else {
                        // Handle other exceptions
                        LOGGER.info(" hey i got some other exceptions error");
                        System.err.println("General error for record: " + consumerRecord + ", exception: " + exception);
                    }
                }
        );

        factory.setCommonErrorHandler(errorHandler);

        return factory;
    }
}
