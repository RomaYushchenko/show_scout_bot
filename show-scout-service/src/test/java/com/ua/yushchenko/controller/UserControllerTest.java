package com.ua.yushchenko.controller;

import static com.ua.yushchenko.TestData.USER;
import static com.ua.yushchenko.TestData.USER_API;
import static com.ua.yushchenko.TestData.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.ua.yushchenko.api.UserApi;
import com.ua.yushchenko.model.mapper.UserMapper;
import com.ua.yushchenko.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for {@link UserController}
 *
 * @author romanyushchenko
 * @version 0.1
 */
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService mockUserService;
    @Mock
    private UserMapper mockUserMapper;

    @InjectMocks
    public UserController unit;

    @Test
    void getUser_nominal() {
        //GIVEN
        when(mockUserService.getUserById(USER_ID)).thenReturn(USER);
        when(mockUserMapper.toUserApi(USER)).thenReturn(USER_API);

        //WHEN
        final UserApi result = unit.getUser(USER_ID);

        //THEN
        assertThat(result).isNotNull()
                .isEqualTo(USER_API);

        verify(mockUserService).getUserById(USER_ID);
        verify(mockUserMapper).toUserApi(USER);

        verifyNoMoreInteractions(mockUserService, mockUserMapper);
    }

    @Test
    void createUser_nominal() {
        //GIVEN
        when(mockUserMapper.toUser(USER_API)).thenReturn(USER);
        when(mockUserService.createUser(USER)).thenReturn(USER);
        when(mockUserMapper.toUserApi(USER)).thenReturn(USER_API);

        //WHEN
        final UserApi result = unit.createUser(USER_API);

        //THEN
        assertThat(result).isNotNull()
                .isEqualTo(USER_API);

        verify(mockUserMapper).toUser(USER_API);
        verify(mockUserService).createUser(USER);
        verify(mockUserMapper).toUserApi(USER);

        verifyNoMoreInteractions(mockUserService, mockUserMapper);
    }

    @Test
    void updateUser_nominal() {
        //GIVEN
        when(mockUserMapper.toUser(USER_API)).thenReturn(USER);
        when(mockUserService.updateUser(USER_ID, USER)).thenReturn(USER);
        when(mockUserMapper.toUserApi(USER)).thenReturn(USER_API);

        //WHEN
        final UserApi result = unit.updateUser(USER_ID, USER_API);

        //THEN
        assertThat(result).isNotNull()
                .isEqualTo(USER_API);

        verify(mockUserMapper).toUser(USER_API);
        verify(mockUserService).updateUser(USER_ID, USER);
        verify(mockUserMapper).toUserApi(USER);

        verifyNoMoreInteractions(mockUserService, mockUserMapper);
    }

    @Test
    void updateUser_nominal_with_incorrect_user_id() {
        //GIVEN
        final var incorrectUserId = 1L;
        final var userToUpdate = USER.toBuilder()
                .userID(incorrectUserId)
                .build();
        final var userToUpdateApi = USER_API.toBuilder()
                .userID(incorrectUserId)
                .build();
        when(mockUserMapper.toUser(userToUpdateApi)).thenReturn(userToUpdate);
        when(mockUserService.updateUser(incorrectUserId, userToUpdate)).thenReturn(null);

        //WHEN /THEN
        assertThatThrownBy(() -> unit.updateUser(incorrectUserId, userToUpdateApi))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("User with userId: " + incorrectUserId + " doesn't exist in system");

        verify(mockUserMapper).toUser(userToUpdateApi);
        verify(mockUserService).updateUser(incorrectUserId, userToUpdate);
        verify(mockUserMapper, never()).toUserApi(any());

        verifyNoMoreInteractions(mockUserService, mockUserMapper);
    }

    @Test
    void updateUser_nominal_with_user_id_as_parameter_does_not_match_with_user_id_to_update() {
        //GIVEN
        final var incorrectUserId = 1L;

        //WHEN /THEN
        assertThatThrownBy(() -> unit.updateUser(incorrectUserId, USER_API))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("PathVariable userId: " + incorrectUserId
                        + " does not match with RequestBody userApi.getUserID(): "
                        + USER_API.getUserID());

        verify(mockUserMapper, never()).toUser(any(UserApi.class));
        verify(mockUserMapper, never()).toUserApi(any());
        verify(mockUserService, never()).updateUser(any(), any());

        verifyNoInteractions(mockUserService, mockUserMapper);
    }

    @Test
    void deleteUser_nominal() {
        //GIVEN
        when(mockUserService.deleteUser(USER_ID)).thenReturn(USER);
        when(mockUserMapper.toUserApi(USER)).thenReturn(USER_API);

        //WHEN
        final UserApi result = unit.deleteUser(USER_ID);

        //THEN
        assertThat(result).isNotNull()
                .isEqualTo(USER_API);

        verify(mockUserService).deleteUser(USER_ID);
        verify(mockUserMapper).toUserApi(USER);

        verifyNoMoreInteractions(mockUserService, mockUserMapper);
    }
}
