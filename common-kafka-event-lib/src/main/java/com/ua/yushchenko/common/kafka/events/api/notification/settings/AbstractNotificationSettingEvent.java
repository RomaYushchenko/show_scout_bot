package com.ua.yushchenko.common.kafka.events.api.notification.settings;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ua.yushchenko.api.NotificationSettingsApi;
import com.ua.yushchenko.common.kafka.events.api.AbstractKafkaEvent;
import com.ua.yushchenko.common.kafka.events.api.EventAction;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Class that provide abstract model of Kafka event for {@link NotificationSettingsApi} entity
 *
 * @author romanyushchenko
 * @version 0.1
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractNotificationSettingEvent extends AbstractKafkaEvent {

    private final NotificationSettingsApi notificationSettings;

    protected AbstractNotificationSettingEvent(final @JsonProperty("eventAction") EventAction eventAction,
                                               final @JsonProperty("notificationSettings") NotificationSettingsApi notificationSettings) {
        super(eventAction);
        this.notificationSettings = notificationSettings;
    }

    @Override
    public String getMessageKey() {
        return notificationSettings.getNotificationSettingsId().toString();
    }
}
