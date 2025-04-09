package com.ua.yushchenko.dal.dao;

import com.ua.yushchenko.model.domain.Subscription;
import com.ua.yushchenko.model.domain.User;
import com.ua.yushchenko.model.persistence.NotificationSettingsDb;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository to work with {@link NotificationSettingsDb} table
 *
 * @author ivanshalaev
 * @version v.0.1
 */
public interface NotificationSettingsDao extends ListCrudRepository<NotificationSettingsDb, UUID> {

    /**
     * Find all {@link NotificationSettingsDb} by user ID
     *
     * @param userId ID of the {@link User}
     * @return List of {@link NotificationSettingsDb} associated with the user
     */
    @Query(value = "SELECT ns.* FROM show_scout_notification_settings ns " +
            "JOIN show_scout_subscription s ON s.notification_settings_id = ns.notification_settings_id " +
            "WHERE s.user_id = :userId",
            nativeQuery = true)
    List<NotificationSettingsDb> findAllNotificationSettingsByUserId(@Param("userId") UUID userId);

    /**
     * Find {@link NotificationSettingsDb} by  subscription ID
     *
     * @param subscriptionId ID of the {@link Subscription}
     * @return List of {@link NotificationSettingsDb} associated with the subscription
     */
    @Query(value = "SELECT ns.* FROM show_scout_notification_settings ns " +
            "JOIN show_scout_subscription s ON s.notification_settings_id = ns.notification_settings_id " +
            "WHERE s.subscription_id = :subscriptionId",
            nativeQuery = true)
    Optional<NotificationSettingsDb> findNotificationSettingsBySubscriptionId(@Param("subscriptionId") UUID subscriptionId);
}
