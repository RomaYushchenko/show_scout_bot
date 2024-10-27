package com.ua.yushchenko.service;

import static com.ua.yushchenko.TestData.USER;
import static com.ua.yushchenko.TestData.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.ua.yushchenko.dal.repository.UserRepository;
import com.ua.yushchenko.model.domain.User;
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
    void createUser_nominal() {
        //GIVEN
        when(mockUserRepository.insertUser(USER)).thenReturn(USER);

        //WHEN
        final User result = unit.createUser(USER);

        //THEN
        assertThat(result).isNotNull()
                          .isEqualTo(USER);

        verify(mockUserRepository).insertUser(USER);

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
    void deleteUser_nominal() {
        //GIVEN
        when(mockUserRepository.selectUserById(USER_ID)).thenReturn(USER);
        doNothing().when(mockUserRepository).deleteUserById(USER_ID);

        //WHEN
        final User result = unit.deleteUser(USER_ID);

        //THEN
        assertThat(result).isNotNull()
                          .isEqualTo(USER);

        verify(mockUserRepository).selectUserById(USER_ID);
        verify(mockUserRepository).deleteUserById(USER_ID);

        verifyNoMoreInteractions(mockUserRepository);
    }
}
