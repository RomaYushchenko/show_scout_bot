package com.ua.yushchenko.model.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    @Column(name = "user_id", nullable = false)
    long userID;

    @Column(name = "user_name")
    String userName;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "chat_id", nullable = false)
    long chatId;
}
