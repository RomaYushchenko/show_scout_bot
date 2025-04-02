package com.ua.yushchenko.model.persistence;

import com.ua.yushchenko.model.persistence.pk.UserPk;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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

    @EmbeddedId
    UserPk id;

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
