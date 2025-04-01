package com.ua.yushchenko.service;

import com.ua.yushchenko.dal.repository.NotificationSettingsRepository;
import com.ua.yushchenko.events.producer.NotificationSettingEventProducer;
import com.ua.yushchenko.model.domain.NotificationSettings;
import com.ua.yushchenko.model.domain.Subscription;
import com.ua.yushchenko.model.domain.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Service that exposes the base functionality for interacting with {@link NotificationSettings} data
 *
 * @author ivanshalaev
 * @version 0.1
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class NotificationSettingsService {

    @NonNull
    private final NotificationSettingsRepository notificationSettingsRepository;
    @NonNull
    private final NotificationSettingEventProducer notificationSettingEventProducer;

    private final Integer DEFAULT_NOTIFICATION_INTERVAL = 1;

    /**
     * Create {@link NotificationSettings}
     *
     * @return Created {@link NotificationSettings}
     */
    public NotificationSettings createNotificationSettings() {
        log.debug("createNotificationSettings.E: Create NotificationSettings");

        final var notificationSettings = NotificationSettings.builder()
                .notificationSettingsId(UUID.randomUUID())
                .notificationSettingsIntervals(List.of(DEFAULT_NOTIFICATION_INTERVAL))
                .enabled(true)
                .build();

        final var notificationSettingsToCreate = notificationSettingsRepository.insertNotificationSettings(notificationSettings);

        log.debug("createNotificationSettings.X: Created NotificationSettings:{}", notificationSettingsToCreate);
        return notificationSettingsToCreate;
    }

    /**
     * Update {@link NotificationSettings}
     *
     * @param notificationSettingsToUpdate instance of {@link NotificationSettings}
     * @return updated {@link NotificationSettings}
     */
    public NotificationSettings updateNotificationSettings(final UUID notificationSettingsId, final NotificationSettings notificationSettingsToUpdate) {
        log.debug("updateNotificationSettings.E: Update NotificationSettings by ID: {}", notificationSettingsId);

        final var notificationSettings = getNotificationSettings(notificationSettingsId);

        if (Objects.isNull(notificationSettings)) {
            log.warn("updateNotificationSettings.X: NotificationSettings with ID: {} doesn't find in system", notificationSettingsId);
            return null;
        }

        if (Objects.equals(notificationSettings, notificationSettingsToUpdate)) {
            log.debug("updateNotificationSettings.X: NotificationSettings wasn't updated due to sam object");
            return notificationSettings;
        }

        final var updatedNotificationSettings = notificationSettingsRepository.updateNotificationSettings(notificationSettingsToUpdate);

        notificationSettingEventProducer.sendUpdatedEvent(updatedNotificationSettings);

        log.debug("updateNotificationSettings.X: Updated NotificationSettings: {}", updatedNotificationSettings);
        return updatedNotificationSettings;
    }

    /**
     * Get {@link NotificationSettings} by ID
     *
     * @param notificationSettingsId ID of {@link NotificationSettings}
     * @return {@link NotificationSettings}
     */
    public NotificationSettings getNotificationSettings(final UUID notificationSettingsId) {
        log.debug("getNotificationSettings.E: Get NotificationSettings by id: {}", notificationSettingsId);

        final var notificationSettings = notificationSettingsRepository.selectNotificationSettings(notificationSettingsId);

        log.debug("getNotificationSettings.X: Return NotificationSettings: {} with id: {}"
                , notificationSettings, notificationSettingsId);
        return notificationSettings;
    }

    /**
     * Select all {@link NotificationSettings} for user
     * if user ID was not provided returned all {@link NotificationSettings}
     *
     * @param userId ID of {@link User}
     * @return {@link List} of {@link NotificationSettings}
     */
    public List<NotificationSettings> getListNotificationSettingsByFilter(final Long userId) {
        log.debug("getListNotificationSettingsByFilter.E: Get all NotificationSettings for user: {}", userId);

        final var notificationSettings = Objects.isNull(userId)
                ? notificationSettingsRepository.selectNotificationSettingsList()
                : notificationSettingsRepository.selectNotificationSettingsListByUserId(userId);

        log.debug("getListNotificationSettingsByFilter.X: Return: {} NotificationSettings for user: {}"
                , notificationSettings.size(), userId);
        return notificationSettings;
    }


    /**
     * Select {@link NotificationSettings} by subscription ID
     *
     * @param subscriptionId ID of {@link Subscription}
     * @return {@link NotificationSettings}
     */
    public NotificationSettings getNotificationSettingsBySubscriptionId(final UUID subscriptionId) {
        log.debug("getNotificationSettingsBySubscriptionId.E: Select NotificationSettings by subscriptionId: {}"
                , subscriptionId);

        final var notificationSettings = notificationSettingsRepository.selectNotificationSettingsBySubscriptionId(subscriptionId);

        log.debug("getNotificationSettingsBySubscriptionId.X: Returned NotificationSettings: {} for subscriptionId: {}"
                , notificationSettings, subscriptionId);
        return notificationSettings;
    }

    /**
     * Delete {@link NotificationSettings}
     *
     * @param notificationSettingsId ID of {@link NotificationSettings}
     * @return Deleted {@link NotificationSettings}
     */
    public NotificationSettings deleteNotificationSettings(final UUID notificationSettingsId) {
        log.debug("deleteNotificationSettings.E: Delete NotificationSettings by id: {}", notificationSettingsId);

        final var notificationSettings = getNotificationSettings(notificationSettingsId);

        if (Objects.isNull(notificationSettings)) {
            throw new EntityNotFoundException("NotificationSettings [ID= " + notificationSettingsId + "] doesn't exist in system");
        }

        notificationSettingsRepository.deleteNotificationSettings(notificationSettingsId);

        log.debug("deleteNotificationSettings.X: Deleted NotificationSettings by id: {}", notificationSettingsId);
        return notificationSettings;
    }
}
