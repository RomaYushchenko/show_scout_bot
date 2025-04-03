package com.ua.yushchenko.model.persistence;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "show_scout_subscription")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionDb {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "subscription_id", nullable = false)
    UUID subscriptionId;
    @Column(name = "show_id", nullable = false)
    UUID showId;
    @Column(name = "user_id", nullable = false)
    UUID userId;
    @Column(name = "notification_settings_id", nullable = false)
    UUID notificationSettingsId;
}
