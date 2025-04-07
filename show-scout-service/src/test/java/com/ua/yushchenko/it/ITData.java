package com.ua.yushchenko.it;

import java.util.UUID;

import com.ua.yushchenko.api.UserApi;

public class ITData {

    public final static UUID USER_ID = UUID.randomUUID();
    public final static Long TELEGRAM_USER_ID = 123L;
    public final static Long CHAT_ID = 123L;
    public final static String USER_NAME = "TEST_USER_NAME";
    public final static String LAST_NAME = "Last Name Test";
    public final static String FIRST_NAME = "First Name Test";
    public final static String TIME_ZONE = "+3";

    public final static UserApi USER_API;

    static {
        USER_API = UserApi.builder()
                          .telegramUserId(TELEGRAM_USER_ID)
                          .userName(USER_NAME)
                          .lastName(LAST_NAME)
                          .firstName(FIRST_NAME)
                          .timeZone(TIME_ZONE)
                          .chatId(CHAT_ID)
                          .build();
    }
}
