package com.ua.yushchenko.model.mapper;

import com.ua.yushchenko.api.NotificationApi;
import com.ua.yushchenko.config.MapperConfiguration;
import com.ua.yushchenko.model.domain.Notification;
import com.ua.yushchenko.model.persistence.NotificationDb;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;

/**
 * Class to represent mapping Notification entity.
 *
 * @author ivanshalaev
 * @version 0.1
 */
@Mapper(config = MapperConfiguration.class)
public interface NotificationMapper {
    NotificationApi toNotificationApi(final Notification notification);

    Notification toNotification(final NotificationApi notificationApi);

    Notification toNotification(final NotificationDb notificationDb);

    NotificationDb toNotificationDb(final Notification notification);

    @ObjectFactory
    default Notification.NotificationBuilder createDomainBuilder() {
        return Notification.builder();
    }
}
