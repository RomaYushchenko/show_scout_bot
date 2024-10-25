package com.ua.yushchenko.dal.repository;

import com.ua.yushchenko.dal.dao.UserDao;
import com.ua.yushchenko.model.domain.User;
import com.ua.yushchenko.model.mapper.UserMapper;
import com.ua.yushchenko.model.persistence.UserDb;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Repository to work with {@link UserDb}
 *
 * @author romanyushchenko
 * @version 0.1
 */
@Component
@RequiredArgsConstructor
public class UserRepository {

    @NonNull
    private final UserDao userDao;
    @NonNull
    private final UserMapper userMapper;

    /**
     * Select {@link User} by current ID
     *
     * @param userId ID of user
     * @return {@link User} by current ID
     */
    public User selectUserById(final Long userId) {
        return userDao.findById(userId)
                      .map(userMapper::toUser)
                      .orElse(null);
    }

    /**
     * Insert {@link User}
     *
     * @param userToInsert instance of {@link User} to insert
     * @return inserted {@link User}
     */
    public User insertUser(final User userToInsert) {
        final UserDb userDb = userMapper.toUserDb(userToInsert);
        final UserDb insertedUser = userDao.save(userDb);
        return userMapper.toUser(insertedUser);
    }

    /**
     * Update {@link User}
     *
     * @param userToUpdate instance of {@link User} to insert
     * @return updated {@link User}
     */
    public User updateUser(final User userToUpdate) {
        final UserDb userDb = userMapper.toUserDb(userToUpdate);
        final UserDb updatedUser = userDao.save(userDb);
        return userMapper.toUser(updatedUser);
    }

    /**
     * Delete {@link User}
     *
     * @param userId ID of user
     */
    public void deleteUserById(final Long userId) {
        userDao.deleteById(userId);
    }
}
