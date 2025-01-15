package com.ua.yushchenko.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Class that represents the API object for a Notification.
 * <p>
 * This class is used to transfer show data between the API layer and other application layers.
 *
 * @author ivanshalaiev
 * @version 0.1
 */

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class NotificationApi {
    private UUID notificationId;
    private UUID userId;
    private UUID showId;
    private LocalDateTime notificationTime;
}
