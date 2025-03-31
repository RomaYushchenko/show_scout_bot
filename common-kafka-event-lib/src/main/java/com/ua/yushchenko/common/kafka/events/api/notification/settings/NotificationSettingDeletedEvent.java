package com.ua.yushchenko.common.kafka.events.api.notification.settings;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ua.yushchenko.api.NotificationSettingsApi;
import com.ua.yushchenko.common.kafka.events.api.EventAction;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

/**
 * Class that provide model of deleted Kafka event for {@link NotificationSettingsApi} entity
 *
 * @author romanyushchenko
 * @version 0.1
 */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class NotificationSettingDeletedEvent extends AbstractNotificationSettingEvent{

    @Builder
    @JsonCreator
    public NotificationSettingDeletedEvent(final @JsonProperty("notificationSettings") NotificationSettingsApi notificationSettings) {
        super(EventAction.DELETE, notificationSettings);
    }
}
