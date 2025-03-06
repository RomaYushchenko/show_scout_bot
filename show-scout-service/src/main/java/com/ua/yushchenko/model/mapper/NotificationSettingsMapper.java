package com.ua.yushchenko.model.mapper;

import com.ua.yushchenko.api.NotificationSettingsApi;
import com.ua.yushchenko.config.MapperConfiguration;
import com.ua.yushchenko.model.domain.NotificationSettings;
import com.ua.yushchenko.model.persistence.NotificationSettingsDb;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;

import java.util.List;

/**
 * Class to represent mapping NotificationSettings entity.
 *
 * @author ivanshalaev
 * @version 0.1
 */
@Mapper(config = MapperConfiguration.class)
public interface NotificationSettingsMapper {
    NotificationSettingsApi toNotificationSettingsApi(final NotificationSettings notificationSettings);

    NotificationSettings toNotificationSettings(final NotificationSettingsApi notificationSettingsApi);

    NotificationSettings toNotificationSettings(final NotificationSettingsDb notificationSettingsDb);

    NotificationSettingsDb toNotificationSettingsDb(final NotificationSettings notificationSettings);

    List<NotificationSettingsApi> toNotificationSettingsApis(final List<NotificationSettings> notificationSettingsList);

    @ObjectFactory
    default NotificationSettings.NotificationSettingsBuilder createDomainBuilder() {
        return NotificationSettings.builder();
    }
}
