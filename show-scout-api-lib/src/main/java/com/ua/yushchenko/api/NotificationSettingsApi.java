package com.ua.yushchenko.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Class that represents the API object for a NotificationSettings.
 *
 * This class is used to transfer show data between the API layer and other application layers.
 *
 * @author ivanshalaiev
 * @version 0.1
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class NotificationSettingsApi {
    private UUID notificationSettingsId;
    private List<Integer> notificationSettingsIntervals;
    private boolean enabled;
}