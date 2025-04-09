package com.ua.yushchenko.it.user;

import static com.ua.yushchenko.it.ITData.CHAT_ID;
import static com.ua.yushchenko.it.ITData.TELEGRAM_USER_ID;
import static com.ua.yushchenko.it.ITData.USER_API;
import static com.ua.yushchenko.it.ITData.USER_ID;
import static com.ua.yushchenko.it.ITData.USER_NAME;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ua.yushchenko.api.UserApi;
import com.ua.yushchenko.it.config.BaseIntegrationTest;
import com.ua.yushchenko.it.provider.UserServiceClientProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.HttpStatus;

/**
 * Integration test for testing using functionality
 *
 * @author romanyushchenko
 * @version 0.1
 */
@Tag("integration")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ITUserTest extends BaseIntegrationTest {

    private UserServiceClientProvider userServiceClientProvider;

    @BeforeEach
    void cleanUp() {
        userServiceClientProvider = new UserServiceClientProvider(restTemplate, objectMapper, port);

        userRepository.selectAllUsers()
                      .forEach(user -> userRepository.deleteUserById(user.getUserId()));
    }

    @Nested
    @DisplayName("UC-001: Registration new user")
    class RegistrationNewUserTests {

        @Test
        @DisplayName("Verify that new user was created")
        void registrationNewUser() {
            final var response = userServiceClientProvider.postUserRequest(USER_API);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).extracting(UserApi::getTelegramUserId,
                                                      UserApi::getChatId,
                                                      UserApi::getUserName)
                                          .containsExactly(TELEGRAM_USER_ID,
                                                           CHAT_ID,
                                                           USER_NAME);
        }

        @Test
        @DisplayName("Verify that new user has already existed")
        void registrationNewUserAlreadyExisted() {
            userServiceClientProvider.postUserRequest(USER_API);

            final var response = userServiceClientProvider.postUserRequest(USER_API);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }
    }

    @Nested
    @DisplayName("UC-002: Get information about User")
    class GetInformationAboutUserTests {

        @Test
        @DisplayName("Verify returning user by ID")
        void getInformationAboutUser() throws JsonProcessingException {
            final var createdUser = userServiceClientProvider.postUserRequest(USER_API).getBody();

            final var userResponse = userServiceClientProvider.getUserRequest(createdUser.getUserId());

            assertThat(userResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(userResponse.getBody()).extracting(UserApi::getTelegramUserId,
                                                          UserApi::getChatId,
                                                          UserApi::getUserName)
                                              .containsExactly(TELEGRAM_USER_ID,
                                                               CHAT_ID,
                                                               USER_NAME);
        }

        @Test
        @DisplayName("Verify that user doesn't exist in system")
        void userDoesNotExist() {
            final var userResponse = userServiceClientProvider.getUserRequest(USER_ID);

            assertThat(userResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }

    @Nested
    @DisplayName("UC-003: Update information about User")
    class UpdateInformationAboutUserTests {

        @Test
        @DisplayName("Verify that user was updated")
        void updateInformationAboutUser() {
            final var createdUserResponse = userServiceClientProvider.postUserRequest(USER_API);

            assertThat(createdUserResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

            final var userToUpdate = createdUserResponse.getBody()
                                                        .toBuilder()
                                                        .timeZone("+6")
                                                        .build();

            final var updatedUserResponse = userServiceClientProvider.putUserRequest(userToUpdate);

            assertThat(updatedUserResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(updatedUserResponse.getBody()).extracting(UserApi::getTelegramUserId,
                                                                 UserApi::getChatId,
                                                                 UserApi::getTimeZone)
                                                     .containsExactly(TELEGRAM_USER_ID,
                                                                      CHAT_ID,
                                                                      "+6");
        }

        @Test
        @DisplayName("Verify that user was not updated")
        void userDoesNotExist() {
            UserApi invalidUser = USER_API.toBuilder()
                                          .userId(USER_ID)
                                          .build();

            final var updatedUserResponse = userServiceClientProvider.putUserRequest(invalidUser);

            assertThat(updatedUserResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }

    @Nested
    @DisplayName("UC-004: Remove User")
    class RemoveUserTests {

        @Test
        @DisplayName("Verify that user was deleted")
        void removeUser() {
            final var createdUserResponse = userServiceClientProvider.postUserRequest(USER_API);

            assertThat(createdUserResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

            final var userId = createdUserResponse.getBody().getUserId();

            final var deletedUserResponse = userServiceClientProvider.deletedUserRequest(userId);

            assertThat(deletedUserResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        }

        @Test
        @DisplayName("Verify that user was not deleted")
        void userDoesNotExist() {
            final var deletedUserResponse = userServiceClientProvider.deletedUserRequest(USER_ID);

            assertThat(deletedUserResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }
}
