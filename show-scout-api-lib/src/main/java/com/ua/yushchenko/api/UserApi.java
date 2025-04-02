package com.ua.yushchenko.api;

import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class that represented API User object from Telegram
 *
 * @author romanyushchenko
 * @version 0.1
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserApi {

    private UUID userId;
    private Long telegramUserId;
    private String userName;
    private String firstName;
    private String lastName;
    private String timeZone;
    private Long chatId;

    @JsonIgnore
    public String getFullName() {
        return Objects.nonNull(getUserName())
                ? getUserName()
                : getFirstName() + " " + getLastName();
    }
}
