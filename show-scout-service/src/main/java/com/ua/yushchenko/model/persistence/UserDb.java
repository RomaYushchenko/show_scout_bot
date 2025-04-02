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

@Table(name = "show_scout_user")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDb {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", nullable = false)
    UUID userId;

    @Column(name = "telegram_user_id", nullable = false)
    Long telegramUserId;

    @Column(name = "user_name")
    String userName;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "time_zone", nullable = false)
    String timeZone;

    @Column(name = "chat_id", nullable = false)
    Long chatId;
}
