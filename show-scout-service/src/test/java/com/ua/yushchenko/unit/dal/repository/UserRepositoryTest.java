package com.ua.yushchenko.unit.dal.repository;

import static com.ua.yushchenko.unit.TestData.TELEGRAM_USER_ID;
import static com.ua.yushchenko.unit.TestData.USER;
import static com.ua.yushchenko.unit.TestData.USER_DB;
import static com.ua.yushchenko.unit.TestData.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ua.yushchenko.dal.dao.UserDao;
import com.ua.yushchenko.dal.repository.UserRepository;
import com.ua.yushchenko.model.domain.User;
import com.ua.yushchenko.model.mapper.UserMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for {@link UserRepository}
 *
 * @author romanyushchenko
 * @version 0.1
 */
@Tag("unit")
@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Mock
    private UserDao mockUserDao;
    @Mock
    private UserMapper mockUserMapper;

    @InjectMocks
    private UserRepository unit;

    @Test
    void selectUserById_nominal() {
        //GIVEN
        when(mockUserDao.findById(USER_ID)).thenReturn(Optional.of(USER_DB));
        when(mockUserMapper.toUser(USER_DB)).thenReturn(USER);

        //WHEN
        final User result = unit.selectUserById(USER_ID);

        //THEN
        assertThat(result).isNotNull()
                          .isEqualTo(USER);

        verify(mockUserDao).findById(USER_ID);
        verify(mockUserMapper).toUser(USER_DB);

        verifyNoMoreInteractions(mockUserDao, mockUserMapper);
    }

    @Test
    void selectUserByTelegramUserId_nominal() {
        //GIVEN
        when(mockUserDao.findByTelegramUserId(TELEGRAM_USER_ID)).thenReturn(Optional.of(USER_DB));
        when(mockUserMapper.toUser(USER_DB)).thenReturn(USER);

        //WHEN
        final User result = unit.selectUserByTelegramUserId(TELEGRAM_USER_ID);

        //THEN
        assertThat(result).isNotNull()
                          .isEqualTo(USER);

        verify(mockUserDao).findByTelegramUserId(TELEGRAM_USER_ID);
        verify(mockUserMapper).toUser(USER_DB);

        verifyNoMoreInteractions(mockUserDao, mockUserMapper);
    }

    @Test
    void deleteUserById_nominal() {
        //GIVEN
        when(mockUserDao.findById(USER_ID)).thenReturn(Optional.of(USER_DB));
        when(mockUserMapper.toUser(USER_DB)).thenReturn(USER);
        doNothing().when(mockUserDao).deleteById(USER_ID);

        //WHEN
        final var result = unit.deleteUserById(USER_ID);

        //THEN
        assertThat(result)
                .isNotNull()
                .isEqualTo(USER);

        verify(mockUserDao).findById(USER_ID);
        verify(mockUserMapper).toUser(USER_DB);
        verify(mockUserDao).deleteById(USER_ID);

        verifyNoMoreInteractions(mockUserDao, mockUserMapper);
    }

    @Test
    void userExistById_nominal() {
        //GIVEN
        when(mockUserDao.existsById(USER_ID)).thenReturn(true);

        //WHEN
        final var result = unit.userExistById(USER_ID);

        //THEN
        assertThat(result)
                .isTrue();

        verify(mockUserDao).existsById(USER_ID);

        verifyNoMoreInteractions(mockUserDao);
    }

    @Test
    void userExistById_nominal_user_id_does_not_exist() {
        //GIVEN
        final var userIdDoesNotExist = UUID.randomUUID();
        when(mockUserDao.existsById(userIdDoesNotExist)).thenReturn(false);

        //WHEN
        final var result = unit.userExistById(userIdDoesNotExist);

        //THEN
        assertThat(result)
                .isFalse();

        verify(mockUserDao).existsById(userIdDoesNotExist);

        verifyNoMoreInteractions(mockUserDao);
    }

    @Test
    void selectAllUsers_nominal() {
        //GIVEN
        when(mockUserDao.findAll()).thenReturn(List.of(USER_DB));
        when(mockUserMapper.toUser(USER_DB)).thenReturn(USER);

        //WHEN
        final var result = unit.selectAllUsers();

        //THEN
        assertThat(result).isNotNull()
                          .hasSize(1)
                          .isEqualTo(List.of(USER));

        verify(mockUserDao).findAll();
        verify(mockUserMapper).toUser(USER_DB);

        verifyNoMoreInteractions(mockUserDao, mockUserMapper);
    }
}
