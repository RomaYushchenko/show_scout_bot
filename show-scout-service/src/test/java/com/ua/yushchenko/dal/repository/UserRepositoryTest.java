package com.ua.yushchenko.dal.repository;

import static com.ua.yushchenko.TestData.USER;
import static com.ua.yushchenko.TestData.USER_DB;
import static com.ua.yushchenko.TestData.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.ua.yushchenko.dal.dao.UserDao;
import com.ua.yushchenko.model.domain.User;
import com.ua.yushchenko.model.mapper.UserMapper;
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
}
