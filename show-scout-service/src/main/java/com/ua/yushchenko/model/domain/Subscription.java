package com.ua.yushchenko.model.domain;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

/**
 *
 */
@Data
@Builder(toBuilder = true)
public class Subscription {

    UUID subscriptionId;
    UUID showId;
    long userId;
    UUID notificationSettingsId;
}
