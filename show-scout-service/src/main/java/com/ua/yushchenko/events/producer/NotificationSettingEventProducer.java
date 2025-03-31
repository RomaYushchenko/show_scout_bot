package com.ua.yushchenko.events.producer;

import com.ua.yushchenko.api.NotificationSettingsApi;
import com.ua.yushchenko.common.kafka.events.KafkaProducer;
import com.ua.yushchenko.common.kafka.events.api.notification.settings.AbstractNotificationSettingEvent;
import com.ua.yushchenko.common.kafka.events.api.notification.settings.NotificationSettingCreatedEvent;
import com.ua.yushchenko.common.kafka.events.api.notification.settings.NotificationSettingDeletedEvent;
import com.ua.yushchenko.common.kafka.events.api.notification.settings.NotificationSettingUpdatedEvent;
import com.ua.yushchenko.model.domain.NotificationSettings;
import com.ua.yushchenko.model.mapper.NotificationSettingsMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class that provide logic for Kafka event producing for {@link NotificationSettingsApi} entity
 *
 * @author romanyushchenko
 * @version 0.1
 */
@Component
@RequiredArgsConstructor
public class NotificationSettingEventProducer {

    @Value("${ss.core.notification-setting.topic}")
    private String kafkaTopic;

    @NonNull
    private final NotificationSettingsMapper notificationSettingsMapper;

    @NonNull
    private final KafkaProducer kafkaProducer;

    /**
     * Send created Kafka event of {@link NotificationSettingsApi} entity
     *
     * @param notificationSettings instance of entity
     */
    public void sendCreatedEvent(@NonNull final NotificationSettings notificationSettings) {
        final var notificationSettingsApi = notificationSettingsMapper.toNotificationSettingsApi(notificationSettings);
        final var createdEvent = NotificationSettingCreatedEvent.builder()
                                                                .notificationSettings(notificationSettingsApi)
                                                                .build();

        send(createdEvent);
    }

    /**
     * Send updated Kafka event of {@link NotificationSettingsApi} entity
     *
     * @param notificationSettings instance of entity
     */
    public void sendUpdatedEvent(@NonNull final NotificationSettings notificationSettings) {
        final var notificationSettingsApi = notificationSettingsMapper.toNotificationSettingsApi(notificationSettings);
        final var createdEvent = NotificationSettingUpdatedEvent.builder()
                                                                .notificationSettings(notificationSettingsApi)
                                                                .build();

        send(createdEvent);
    }

    /**
     * Send deleted Kafka event of {@link NotificationSettingsApi} entity
     *
     * @param notificationSettings instance of entity
     */
    public void sendDeletedEvent(@NonNull final NotificationSettings notificationSettings) {
        final var notificationSettingsApi = notificationSettingsMapper.toNotificationSettingsApi(notificationSettings);
        final var createdEvent = NotificationSettingDeletedEvent.builder()
                                                                .notificationSettings(notificationSettingsApi)
                                                                .build();

        send(createdEvent);
    }

    private void send(final AbstractNotificationSettingEvent notificationSettings) {
        kafkaProducer.send(kafkaTopic, notificationSettings);
    }
}
