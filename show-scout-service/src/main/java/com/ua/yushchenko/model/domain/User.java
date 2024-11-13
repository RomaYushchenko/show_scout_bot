package com.ua.yushchenko.model.domain;

import lombok.Builder;
import lombok.Data;

/**
 * Class that represented domain User object from Telegram.
 *
 * @author romanyushchenko
 * @version 0.1
 */
@Data
@Builder(toBuilder = true)
public class User {

    long userID;
    String userName;
    String firstName;
    String lastName;
    String timeZone;
    long chatId;
}
