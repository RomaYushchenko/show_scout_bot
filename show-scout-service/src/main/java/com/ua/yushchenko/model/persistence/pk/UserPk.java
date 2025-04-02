package com.ua.yushchenko.model.persistence.pk;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class UserPk implements Serializable {

    @Column(name = "user_id", nullable = false)
    UUID userId;

    @Column(name = "telegram_user_id", nullable = false)
    long telegramUserId;
}
