package com.ua.yushchenko;

import java.util.List;
import java.util.UUID;

import com.ua.yushchenko.api.ShowApi;
import com.ua.yushchenko.api.SubscriptionApi;
import com.ua.yushchenko.api.UserApi;
import com.ua.yushchenko.enums.Genre;
import com.ua.yushchenko.enums.ShowStatus;
import com.ua.yushchenko.model.domain.Show;
import com.ua.yushchenko.model.domain.Subscription;
import com.ua.yushchenko.model.domain.User;
import com.ua.yushchenko.model.persistence.ShowDb;
import com.ua.yushchenko.model.persistence.SubscriptionDb;
import com.ua.yushchenko.model.persistence.UserDb;

public class TestData {

    public final static Long USER_ID = 123L;
    public final static Long CHAT_ID = 123L;
    public final static User USER;
    public final static UserDb USER_DB;
    public final static UserApi USER_API;
    public final static UUID SHOW_ID = UUID.randomUUID();
    public final static UUID SHOW_ID_DOES_NOT_EXIST = UUID.randomUUID();
    public final static String SHOW_NAME_DOES_NOT_EXIST = "Show name does not exist";
    public final static Show SHOW;
    public final static ShowDb SHOW_DB;
    public final static ShowApi SHOW_API;
    public final static UUID SUBSCRIPTION_ID = UUID.randomUUID();
    public final static Subscription SUBSCRIPTION;
    public final static SubscriptionDb SUBSCRIPTION_DB;
    public final static SubscriptionApi SUBSCRIPTION_API;

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

        SHOW = Show.builder()
                   .showID(SHOW_ID)
                   .showName("TestShowName")
                   .genres(List.of(Genre.ADVENTURE, Genre.CRIME))
                   .summary("Test summery")
                   .platform("Test platform")
                   .score(123.123f)
                   .showStatus(ShowStatus.RUNNING)
                   .img("Test img")
                   .build();

        SHOW_DB = ShowDb.builder()
                        .showID(SHOW_ID)
                        .showName("TestShowName")
                        .genres(List.of(Genre.ADVENTURE, Genre.CRIME))
                        .summary("Test summery")
                        .platform("Test platform")
                        .score(123.123f)
                        .showStatus(ShowStatus.RUNNING)
                        .img("Test img")
                        .build();

        SHOW_API = ShowApi.builder()
                          .showID(SHOW_ID)
                          .showName("TestShowName")
                          .genres(List.of(Genre.ADVENTURE, Genre.CRIME))
                          .summary("Test summery")
                          .platform("Test platform")
                          .score(123.123f)
                          .showStatus(ShowStatus.RUNNING)
                          .img("Test img")
                          .build();

        SUBSCRIPTION = Subscription.builder()
                                   .subscriptionId(SUBSCRIPTION_ID)
                                   .showId(SHOW_ID)
                                   .userId(USER_ID)
                                   .build();

        SUBSCRIPTION_DB = SubscriptionDb.builder()
                                        .subscriptionId(SUBSCRIPTION_ID)
                                        .showId(SHOW_ID)
                                        .userId(USER_ID)
                                        .build();

        SUBSCRIPTION_API = SubscriptionApi.builder()
                                          .subscriptionId(SUBSCRIPTION_ID)
                                          .showId(SHOW_ID)
                                          .userId(USER_ID)
                                          .build();
    }

}
