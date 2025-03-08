package com.ua.yushchenko.model.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Entity class representing notification settings in the database.
 * This class maps to the "show_scout_notification_settings" table and
 * contains configuration for how notifications should be delivered to users.
 * <p>
 * Fields include:
 * <ul>
 *   <li><b>notificationSettingsId</b>: Unique identifier for the notification settings.</li>
 *   <li><b>notificationSettingsIntervals</b>: List of time intervals (in minutes) when notifications should be sent.</li>
 *   <li><b>enabled</b>: Flag indicating whether notifications are enabled for these settings.</li>
 * </ul>
 * <p>
 *
 * @author ivanshalaev
 * @version 1.0
 */
@Table(name = "show_scout_notification_settings")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationSettingsDb {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "notification_settings_id", nullable = false)
    UUID notificationSettingsId;
    @Column(name = "notification_settings_intervals", columnDefinition = "INTEGER[]", nullable = false)
    List<Integer> notificationSettingsIntervals;
    @Column(name = "enabled", nullable = false)
    boolean enabled;
}