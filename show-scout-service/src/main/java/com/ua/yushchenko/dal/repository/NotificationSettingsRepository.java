package com.ua.yushchenko.dal.repository;

import com.ua.yushchenko.dal.dao.NotificationSettingsDao;
import com.ua.yushchenko.model.domain.NotificationSettings;
import com.ua.yushchenko.model.domain.Subscription;
import com.ua.yushchenko.model.domain.User;
import com.ua.yushchenko.model.mapper.NotificationSettingsMapper;
import com.ua.yushchenko.model.persistence.NotificationSettingsDb;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

/**
 * Repository to work with {@link NotificationSettingsDb}
 *
 * @author ivanshalaev
 * @version 0.1
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class NotificationSettingsRepository {

    @NonNull
    private final NotificationSettingsDao notificationSettingsDao;
    @NonNull
    private final NotificationSettingsMapper notificationSettingsMapper;

    /**
     * Insert {@link NotificationSettings}
     *
     * @param notificationSettings instance of {@link NotificationSettings} to insert
     * @return inserted {@link NotificationSettings}
     */
    public NotificationSettings insertNotificationSettings(final NotificationSettings notificationSettings) {
        log.trace("insertNotificationSettings.E: Inserting notificationSettings {}", notificationSettings);

        final var notificationSettingsDbToInsert = notificationSettingsMapper.toNotificationSettingsDb(notificationSettings);
        final var insertedNotificationSettingsDb = notificationSettingsDao.save(notificationSettingsDbToInsert);

        log.trace("insertNotificationSettings.E: Inserted notificationSettings {}", insertedNotificationSettingsDb);
        return notificationSettingsMapper.toNotificationSettings(insertedNotificationSettingsDb);
    }

    /**
     * Update {@link NotificationSettings}
     *
     * @param notificationSettings instance of {@link NotificationSettings} to update
     * @return updated {@link NotificationSettings}
     */
    public NotificationSettings updateNotificationSettings(final NotificationSettings notificationSettings) {
        log.trace("updateNotificationSettings.E: Updating notificationSettings {}", notificationSettings);

        final var notificationSettingsDbToCreate = notificationSettingsMapper.toNotificationSettingsDb(notificationSettings);
        final var updatedNotificationSettingsDb = notificationSettingsDao.save(notificationSettingsDbToCreate);

        log.trace("updateNotificationSettings.E: Updated notificationSettings {}", updatedNotificationSettingsDb);
        return notificationSettingsMapper.toNotificationSettings(updatedNotificationSettingsDb);
    }

    /**
     * Select {@link NotificationSettings} by ID
     *
     * @param notificationSettingsId ID of {@link NotificationSettings}
     * @return {@link NotificationSettings}
     */
    public NotificationSettings selectNotificationSettings(final UUID notificationSettingsId) {
        log.debug("selectNotificationSettings.E: Select NotificationSettings with id: {}", notificationSettingsId);

        final var notificationSettings = notificationSettingsDao.findById(notificationSettingsId)
                .map(notificationSettingsMapper::toNotificationSettings)
                .orElse(null);

        log.debug("selectNotificationSettings.E: Return NotificationSettings: {} with id: {}"
                , notificationSettings, notificationSettingsId);
        return notificationSettings;
    }

    /**
     * Select List of {@link NotificationSettings} by user ID
     *
     * @param userId ID of {@link User}
     * @return List of {@link NotificationSettings} for the user
     */
    public List<NotificationSettings> selectNotificationSettingsListByUserId(final Long userId) {
        log.trace("selectNotificationSettingsListByUserId.E: Select List of NotificationSettings for user {}", userId);

        final var notificationSettings = notificationSettingsDao.findAllNotificationSettingsByUserId(userId)
                .stream()
                .map(notificationSettingsMapper::toNotificationSettings)
                .toList();

        log.trace("selectNotificationSettingsListByUserId.X: Returned {} NotificationSettings for user {}",
                notificationSettings.size(), userId);
        return notificationSettings;
    }

    /**
     * Select all {@link NotificationSettings}
     *
     * @return {@link List} of {@link NotificationSettings}
     */
    public List<NotificationSettings> selectNotificationSettingsList() {
        log.trace("selectNotificationSettingsList.E: Selecting all NotificationSettings");

        final var notificationSettings = StreamSupport.stream(notificationSettingsDao.findAll().spliterator(), false)
                .map(notificationSettingsMapper::toNotificationSettings)
                .toList();

        log.trace("selectNotificationSettingsList.X: Returned all {} NotificationSettings"
                , notificationSettings.size());
        return notificationSettings;
    }

    /**
     * Select {@link NotificationSettings} by subscription ID
     *
     * @param subscriptionId ID of {@link Subscription}
     * @return {@link NotificationSettings}
     */
    public NotificationSettings selectNotificationSettingsBySubscriptionId(final UUID subscriptionId) {
        log.trace("selectNotificationSettingsBySubscriptionId.E: Select NotificationSettings by subscriptionId: {}"
                , subscriptionId);

        final var notificationSettings = notificationSettingsDao.findNotificationSettingsBySubscriptionId(subscriptionId)
                .map(notificationSettingsMapper::toNotificationSettings)
                .orElse(null);

        log.trace("selectNotificationSettingsBySubscriptionId.X: Returned NotificationSettings: {} for subscriptionId: {}"
                , notificationSettings, subscriptionId);
        return notificationSettings;
    }

    /**
     * Delete {@link NotificationSettings}
     *
     * @param notificationSettingsId ID of {@link NotificationSettings}
     */
    public void deleteNotificationSettings(final UUID notificationSettingsId) {
        log.trace("deleteNotificationSettings.E: Deleting notificationSettings with id: {}", notificationSettingsId);

        notificationSettingsDao.deleteById(notificationSettingsId);

        log.trace("deleteNotificationSettings.X: Deleted notificationSettings with id: {}", notificationSettingsId);
    }
}
