package com.ua.yushchenko.common.kafka.events;

import static org.springframework.kafka.support.KafkaHeaders.KEY;
import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

import java.util.Objects;

import com.ua.yushchenko.common.kafka.events.api.AbstractKafkaEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * Base kafka producer to send message
 *
 * @author romanyushchenko
 * @version 0.1
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class KafkaProducer {

    @NonNull
    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Sends event to the kafka topic
     *
     * @param kafkaTopic   - the Kafka topic an event send to
     * @param kafkaEvent   - message send to Kafka topic
     */
    public void send(final String kafkaTopic, final AbstractKafkaEvent kafkaEvent) {
        Objects.requireNonNull(kafkaTopic, "Kafka Topic is supposed to be exists for sending Kafka messages");

        Message<AbstractKafkaEvent> message = MessageBuilder.withPayload(kafkaEvent)
                                                            .setHeader(TOPIC, kafkaTopic)
                                                            .setHeader(KEY, kafkaEvent.getMessageKey())
                                                            .setHeader("eventAction", kafkaEvent.getEventAction())
                                                            .build();

        log.info("Sending message {} to Kafka topic {}", message, kafkaTopic);
        kafkaTemplate.send(message);
    }
}
