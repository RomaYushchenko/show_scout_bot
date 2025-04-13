package com.ua.yushchenko.unit.controller;

import static com.ua.yushchenko.unit.TestData.USER;
import static com.ua.yushchenko.unit.TestData.USER_API;
import static com.ua.yushchenko.unit.TestData.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.ua.yushchenko.api.UserApi;
import com.ua.yushchenko.controller.UserController;
import com.ua.yushchenko.model.mapper.UserMapper;
import com.ua.yushchenko.service.UserService;
import org.junit.jupiter.api.Tag;
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
@Tag("unit")
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
