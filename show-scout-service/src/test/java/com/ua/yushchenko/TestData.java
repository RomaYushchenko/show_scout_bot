package com.ua.yushchenko;

import com.ua.yushchenko.api.UserApi;
import com.ua.yushchenko.model.domain.User;
import com.ua.yushchenko.model.persistence.UserDb;

public class TestData {

    public final static Long USER_ID = 123L;
    public final static Long CHAT_ID = 123L;
    public final static User USER;
    public final static UserDb USER_DB;
    public final static UserApi USER_API;

    static {

        USER = User.builder()
                   .userID(USER_ID)
                   .userName("TestUserName")
                   .firstName("TestName")
                   .lastName("TestLastName")
                   .chatId(CHAT_ID)
                   .timeZone("GMT+0000")
                   .build();

        USER_DB = UserDb.builder()
                        .userID(USER_ID)
                        .userName("TestUserName")
                        .firstName("TestName")
                        .lastName("TestLastName")
                        .chatId(CHAT_ID)
                        .timeZone("GMT+0000")
                        .build();

        USER_API = UserApi.builder()
                          .userID(USER_ID)
                          .userName("TestUserName")
                          .firstName("TestName")
                          .lastName("TestLastName")
                          .chatId(CHAT_ID)
                          .timeZone("GMT+0000")
                          .build();
    }

}
