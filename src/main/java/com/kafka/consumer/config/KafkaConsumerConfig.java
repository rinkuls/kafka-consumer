package com.kafka.consumer.config;

import com.rinkul.avro.schema.StudentRecord;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    @Value("${spring.kafka.group.id}")
    private String kafkaGroupId;


    @Bean
    public ConsumerFactory<String, StudentRecord> consumerFactory() {
        // TODO Auto-generated method stub
        Map<String, Object> config = new HashMap<>();
        //config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.178.24:9092"); works for docker compose
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
       // config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "tcp://Kafka-Producer:29092");
        //config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "broker:29092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        config.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
       // config.put("schema.registry.url", "http://192.168.178.24:8081"); for docker compose
        config.put("schema.registry.url", "http://my-schema-registry.default.svc.cluster.local:8081");
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, StudentRecord>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, StudentRecord> factory = new ConcurrentKafkaListenerContainerFactory<String, StudentRecord>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }


}
