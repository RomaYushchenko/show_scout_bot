package com.ua.yushchenko.controller;

import com.ua.yushchenko.api.NotificationSettingsApi;
import com.ua.yushchenko.model.domain.Subscription;
import com.ua.yushchenko.model.mapper.NotificationSettingsMapper;
import com.ua.yushchenko.service.NotificationSettingsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Endpoint for communication with NotificationSettings
 *
 * @author ivanshalaev
 * @version 0.1
 */
@Log4j2
@RestController
@RequiredArgsConstructor
public class NotificationSettingsController {

    @NonNull
    private final NotificationSettingsMapper notificationSettingsMapper;
    @NonNull
    private final NotificationSettingsService notificationSettingsService;

    /**
     * Get {@link NotificationSettingsApi} by ID
     *
     * @param notificationSettingsId ID of {@link NotificationSettingsApi}
     * @return {@link NotificationSettingsApi}
     */
    @GetMapping("/notificationSettings/{notificationSettingsId}")
    public NotificationSettingsApi getNotificationSettings(@PathVariable final UUID notificationSettingsId) {
        log.debug("getNotificationSettings.E: Get NotificationSettings by ID: {}", notificationSettingsId);

        final var notificationSettings = notificationSettingsService.getNotificationSettings(notificationSettingsId);

        if (Objects.isNull(notificationSettings)) {
            throw new EntityNotFoundException("NotificationSettings by " + notificationSettingsId + " doesn't exist in system");
        }

        final var notificationSettingsApi = notificationSettingsMapper.toNotificationSettingsApi(notificationSettings);

        log.debug("getNotificationSettings.X: Return NotificationSettings: {} "
                , notificationSettingsApi);
        return notificationSettingsApi;
    }

    /**
     * Get {@link List} of {@link NotificationSettingsApi} based on filter by User ID. If request params aren't provide, the
     * method will return all exist {@link NotificationSettingsApi}
     *
     * @param userId ID of User to filtering {@link NotificationSettingsApi}
     * @return {@link List} of {@link NotificationSettingsApi}
     */
    @GetMapping("/notificationSettings")
    public List<NotificationSettingsApi> getNotificationSettingsList(@RequestParam(required = false) final UUID userId) {
        log.info("getNotificationSettingsList.E: Get NotificationSettingsApi by userId: {}", userId);

        final var notificationSettings = notificationSettingsService.getListNotificationSettingsByFilter(userId);
        final var notificationSettingsApi = notificationSettingsMapper.toNotificationSettingsApis(notificationSettings);

        log.info("getNotificationSettingsList.X: Return: {} NotificationSettingsApi for user: {}"
                , notificationSettingsApi.size(), userId);
        return notificationSettingsApi;
    }
    /**
     * Get {@link NotificationSettingsApi} by {@link Subscription} ID
     *
     * @param subscriptionId ID of {@link Subscription}
     * @return {@link NotificationSettingsApi}
     */
    @GetMapping("/notificationSettings/subscription/{subscriptionId}")
    public NotificationSettingsApi getNotificationSettingsBySubscriptionId(@PathVariable final UUID subscriptionId) {
        log.info("getNotificationSettingsBySubscriptionId.E: Get NotificationSettings by subscriptionId: {}", subscriptionId);

        final var notificationSettings = notificationSettingsService.getNotificationSettingsBySubscriptionId(subscriptionId);

        if (Objects.isNull(notificationSettings)){
            throw new EntityNotFoundException("NotificationSettings by subscriptionId " + subscriptionId + " doesn't exist in system");
        }

        final var notificationSettingsApi = notificationSettingsMapper.toNotificationSettingsApi(notificationSettings);

        log.info("getNotificationSettingsBySubscriptionId.X: Return NotificationSettings : {}", notificationSettingsApi);
        return notificationSettingsApi;
    }

    /**
     * Update {@link NotificationSettingsApi}
     *
     * @param notificationSettingsId  ID of NotificationSettings
     * @param notificationSettingsApi instance of {@link NotificationSettingsApi} to update
     * @return updated {@link NotificationSettingsApi}
     */
    @PutMapping("/notificationSettings/{notificationSettingsId}")
    public NotificationSettingsApi updateNotificationSettings(@PathVariable("notificationSettingsId") final UUID notificationSettingsId,
                                                              @RequestBody final NotificationSettingsApi notificationSettingsApi) {
        log.info("updateNotificationSettings.E: Update notificationSettings by ID: {}", notificationSettingsId);

        if (!Objects.equals(notificationSettingsId, notificationSettingsApi.getNotificationSettingsId())) {
            throw new IllegalArgumentException("PathVariable notificationSettingsId: " + notificationSettingsId
                    + " does not match with RequestBody notificationSettingsApi.getNotificationSettingsId(): "
                    + notificationSettingsApi.getNotificationSettingsId());
        }

        final var notificationSettingsToUpdate = notificationSettingsMapper.toNotificationSettings(notificationSettingsApi);
        final var updatedNotificationSettings = notificationSettingsService.updateNotificationSettings(notificationSettingsId, notificationSettingsToUpdate);

        if (Objects.isNull(updatedNotificationSettings)) {
            throw new EntityNotFoundException("NotificationSettings with notificationSettingsId: " + notificationSettingsId + " doesn't exist in system");
        }

        final var updatedNotificationSettingsApi = notificationSettingsMapper.toNotificationSettingsApi(updatedNotificationSettings);

        log.info("updateNotificationSettings.X: Updated notificationSettings: {}", updatedNotificationSettingsApi);
        return updatedNotificationSettingsApi;
    }
}
