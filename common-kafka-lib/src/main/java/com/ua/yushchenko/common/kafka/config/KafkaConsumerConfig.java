package com.ua.yushchenko.common.kafka.config;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ua.yushchenko.common.kafka.events.api.AbstractKafkaEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

/**
 * Class that represent configuration for base Kafka Consumer
 *
 * @author romanyushchenko
 * @version 0.1
 */
@Configuration
@PropertySource("classpath:common-kafka-lib.properties")
public class KafkaConsumerConfig {

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${kafka.consumer-group-id}")
    private String consumerGroupId;

    @Bean
    public ConsumerFactory<String, AbstractKafkaEvent> consumerFactory(final ObjectMapper objectMapper) {
        final Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "com.ua.yushchenko.*");

        final var jsonDeserializer = new JsonDeserializer<AbstractKafkaEvent>(AbstractKafkaEvent.class, objectMapper);
        jsonDeserializer.setUseTypeHeaders(false);

        return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), jsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AbstractKafkaEvent> kafkaListenerContainerFactory(
            final ConsumerFactory<String, AbstractKafkaEvent> consumerFactory) {

        final var factory = new ConcurrentKafkaListenerContainerFactory<String, AbstractKafkaEvent>();
        factory.setConsumerFactory(consumerFactory);

        return factory;
    }
}
