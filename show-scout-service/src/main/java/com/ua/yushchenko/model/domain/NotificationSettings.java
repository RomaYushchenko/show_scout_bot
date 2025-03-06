package com.ua.yushchenko.model.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * Class that represents a domain object for notifications in the application.
 * It is designed to track notifications sent to users about specific TV shows,
 * including details about the notification's timing and its associations with
 * users and shows.
 * <p>
 * Fields include:
 * <ul>
 *   <li>Unique identifier for the notification</li>
 *   <li>Identifier of the user receiving the notification</li>
 *   <li>Identifier of the associated TV show</li>
 *   <li>Date and time when the notification is scheduled or sent</li>
 * </ul>
 * <p>
 * This class is built to support future extensions, such as notification types
 * or delivery status tracking.
 *
 * @author ivanshalaev
 * @version 0.1
 */
@Data
@Builder(toBuilder = true)
public class NotificationSettings {
    UUID notificationSettingsId;
    List<Integer> notificationSettingsIntervals;
    boolean enabled;
}
