package com.ua.yushchenko.unit.service;

import static com.ua.yushchenko.unit.TestData.TELEGRAM_USER_ID;
import static com.ua.yushchenko.unit.TestData.USER;
import static com.ua.yushchenko.unit.TestData.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.UUID;

import com.ua.yushchenko.dal.repository.UserRepository;
import com.ua.yushchenko.common.exceptions.model.ShowScoutIllegalArgumentException;
import com.ua.yushchenko.common.exceptions.model.ShowScoutNotFoundException;
import com.ua.yushchenko.model.domain.User;
import com.ua.yushchenko.service.UserService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for {@link UserService}
 *
 * @author romanyushchenko
 * @version 0.1
 */
@Tag("unit")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private UserService unit;

    @Test
    void getUserById_nominal() {
        //GIVEN
        when(mockUserRepository.selectUserById(USER_ID)).thenReturn(USER);

        //WHEN
        final User result = unit.getUserById(USER_ID);

        //THEN
        assertThat(result).isNotNull()
                          .isEqualTo(USER);

        verify(mockUserRepository).selectUserById(USER_ID);

        verifyNoMoreInteractions(mockUserRepository);
    }

    @Test
    void getUserByTelegramUserId_nominal() {
        //GIVEN
        when(mockUserRepository.selectUserByTelegramUserId(TELEGRAM_USER_ID)).thenReturn(USER);

        //WHEN
        final User result = unit.getUserByTelegramUserId(TELEGRAM_USER_ID);

        //THEN
        assertThat(result).isNotNull()
                          .isEqualTo(USER);

        verify(mockUserRepository).selectUserByTelegramUserId(TELEGRAM_USER_ID);

        verifyNoMoreInteractions(mockUserRepository);
    }

    @Test
    void createUser_nominal() {
        //GIVEN
        when(mockUserRepository.selectUserByTelegramUserId(TELEGRAM_USER_ID)).thenReturn(null);
        when(mockUserRepository.insertUser(USER)).thenReturn(USER);

        //WHEN
        final User result = unit.createUser(USER);

        //THEN
        assertThat(result).isNotNull()
                          .isEqualTo(USER);

        verify(mockUserRepository).selectUserByTelegramUserId(TELEGRAM_USER_ID);
        verify(mockUserRepository).insertUser(USER);

        verifyNoMoreInteractions(mockUserRepository);
    }

    @Test
    void createUser_nominal_telegram_user_id_is_required() {
        //GIVEN
        final var user = USER.toBuilder()
                             .telegramUserId(null)
                             .build();
        //WHEN //THEN
        assertThatThrownBy(() -> unit.createUser(user))
                .isInstanceOf(ShowScoutIllegalArgumentException.class)
                .hasMessage("Telegram user ID is required");

        verify(mockUserRepository, never()).insertUser(user);

        verifyNoMoreInteractions(mockUserRepository);
    }

    @Test
    void createUser_nominal_already_created() {
        //GIVEN
        when(mockUserRepository.selectUserByTelegramUserId(TELEGRAM_USER_ID)).thenReturn(USER);

        //WHEN //THEN
        assertThatThrownBy(() -> unit.createUser(USER))
                .isInstanceOf(ShowScoutIllegalArgumentException.class)
                .hasMessage("User exist in system by TelegramID: " + TELEGRAM_USER_ID);

        verify(mockUserRepository).selectUserByTelegramUserId(TELEGRAM_USER_ID);
        verify(mockUserRepository, never()).insertUser(USER);

        verifyNoMoreInteractions(mockUserRepository);
    }

    @Test
    void updateUser_nominal() {
        //GIVEN
        final User updatedUser = USER.toBuilder()
                                     .userName("TestUserName_2")
                                     .build();

        when(mockUserRepository.selectUserById(USER_ID)).thenReturn(USER);
        when(mockUserRepository.updateUser(updatedUser)).thenReturn(updatedUser);

        //WHEN
        final User result = unit.updateUser(USER_ID, updatedUser);

        //THEN
        assertThat(result).isNotNull()
                          .isEqualTo(updatedUser);

        verify(mockUserRepository).selectUserById(USER_ID);
        verify(mockUserRepository).updateUser(updatedUser);

        verifyNoMoreInteractions(mockUserRepository);
    }

    @Test
    void updateUser_nominal_with_incorrect_user_id() {
        //GIVEN
        final var incorrectUserId = UUID.randomUUID();
        final var userToUpdate = USER.toBuilder()
                                     .userId(incorrectUserId)
                                     .build();

        when(mockUserRepository.selectUserById(incorrectUserId)).thenReturn(null);

        //WHEN /THEN
        assertThatThrownBy(() -> unit.updateUser(incorrectUserId, userToUpdate))
                .isInstanceOf(ShowScoutNotFoundException.class)
                .hasMessage("User by " + incorrectUserId + " doesn't exist in system");

        verify(mockUserRepository).selectUserById(incorrectUserId);
        verify(mockUserRepository, never()).updateUser(userToUpdate);

        verifyNoMoreInteractions(mockUserRepository);
    }

    @Test
    void updateUser_nominal_with_user_id_as_parameter_does_not_match_with_user_id_to_update() {
        //GIVEN
        final var incorrectUserId = UUID.randomUUID();

        //WHEN /THEN
        assertThatThrownBy(() -> unit.updateUser(incorrectUserId, USER))
                .isInstanceOf(ShowScoutIllegalArgumentException.class)
                .hasMessage("Id of user " + incorrectUserId + " does not match with params user.userId: " +
                                    USER.getUserId());

        verify(mockUserRepository, never()).selectUserById(incorrectUserId);
        verify(mockUserRepository, never()).updateUser(USER);

        verifyNoInteractions(mockUserRepository);
    }

    @Test
    void deleteUser_nominal() {
        //GIVEN
        when(mockUserRepository.userExistById(USER_ID)).thenReturn(true);
        when(mockUserRepository.deleteUserById(USER_ID)).thenReturn(USER);

        //WHEN
        final User result = unit.deleteUser(USER_ID);

        //THEN
        assertThat(result).isNotNull()
                          .isEqualTo(USER);

        verify(mockUserRepository).userExistById(USER_ID);
        verify(mockUserRepository).deleteUserById(USER_ID);

        verifyNoMoreInteractions(mockUserRepository);
    }

    @Test
    void deleteUser_nominal_user_id_does_not_exist() {
        //GIVEN
        final var userIdDoesNotExist = UUID.randomUUID();
        when(mockUserRepository.userExistById(userIdDoesNotExist)).thenReturn(false);

        //WHEN //THEN
        assertThatThrownBy(() -> unit.deleteUser(userIdDoesNotExist))
                .isInstanceOf(ShowScoutNotFoundException.class)
                .hasMessage("User by " + userIdDoesNotExist + " doesn't exist in system");

        verify(mockUserRepository).userExistById(userIdDoesNotExist);
        verify(mockUserRepository, never()).deleteUserById(any());

        verifyNoMoreInteractions(mockUserRepository);
    }

    @Test
    void userExistById_nominal() {
        //GIVEN
        when(mockUserRepository.userExistById(USER_ID)).thenReturn(true);

        //WHEN
        final var result = unit.userExistById(USER_ID);

        //THEN
        assertThat(result)
                .isTrue();

        verify(mockUserRepository).userExistById(USER_ID);

        verifyNoMoreInteractions(mockUserRepository);
    }

    @Test
    void userExistById_nominal_user_id_does_not_exist() {
        //GIVEN
        final var userIdDoesNotExist = UUID.randomUUID();
        when(mockUserRepository.userExistById(userIdDoesNotExist)).thenReturn(false);

        //WHEN
        final var result = unit.userExistById(userIdDoesNotExist);

        //THEN
        assertThat(result)
                .isFalse();

        verify(mockUserRepository).userExistById(userIdDoesNotExist);

        verifyNoMoreInteractions(mockUserRepository);
    }
}
