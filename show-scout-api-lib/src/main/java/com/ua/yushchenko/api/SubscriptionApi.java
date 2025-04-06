package com.ua.yushchenko.api;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class that represented API Subscription object from Telegram
 *
 * @author romanyushchenko
 * @version 0.1
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionApi {

    private UUID subscriptionId;
    private UUID showId;
    private UUID userId;
    private UUID notificationSettingsId;
}
