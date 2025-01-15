package com.ua.yushchenko.model.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity class representing a notification in the database.
 * This class maps to the "show_scout_notification" table and
 * contains information about notifications for users related to shows.
 * <p>
 * Fields include:
 * <ul>
 *   <li><b>notificationId</b>: Unique identifier for the notification.</li>
 *   <li><b>userId</b>: Unique identifier of the user who will receive the notification.</li>
 *   <li><b>showId</b>: Unique identifier of the show associated with the notification.</li>
 *   <li><b>notificationTime</b>: The timestamp when the notification is scheduled to be sent.</li>
 * </ul>
 *
 * @author ivanshalaev
 * @version 1.0
 */
@Table(name = "show_scout_notification")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDb {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "notification_id", nullable = false)
    private UUID notificationId;
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @Column(name = "show_id", nullable = false)
    private UUID showId;
    @Column(name = "notification_time", nullable = false)
    private LocalDateTime notificationTime;
}
