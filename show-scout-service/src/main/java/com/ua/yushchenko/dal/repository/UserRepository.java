package com.ua.yushchenko.dal.repository;

import java.util.List;
import java.util.UUID;

import com.ua.yushchenko.dal.dao.UserDao;
import com.ua.yushchenko.model.domain.User;
import com.ua.yushchenko.model.mapper.UserMapper;
import com.ua.yushchenko.model.persistence.UserDb;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * Repository to work with {@link UserDb}
 *
 * @author romanyushchenko
 * @version 0.1
 */
@Log4j2
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
    public User selectUserById(final UUID userId) {
        log.trace("selectUserById.E: Select user from table by ID:{}", userId);

        final User user = userDao.findById(userId)
                                 .map(userMapper::toUser)
                                 .orElse(null);

        log.trace("selectUserById.X: User:{}", user);
        return user;
    }

    /**
     * Insert {@link User}
     *
     * @param userToInsert instance of {@link User} to insert
     * @return inserted {@link User}
     */
    public User insertUser(final User userToInsert) {
        log.trace("insertUser.E: Insert user to table");

        final UserDb userDb = userMapper.toUserDb(userToInsert);
        final UserDb insertedUser = userDao.save(userDb);

        log.trace("insertUser.X: Inserted user:{}", insertedUser);
        return userMapper.toUser(insertedUser);
    }

    /**
     * Update {@link User}
     *
     * @param userToUpdate instance of {@link User} to insert
     * @return updated {@link User}
     */
    public User updateUser(final User userToUpdate) {
        log.trace("updateUser.E: Update user to table");

        final UserDb userDb = userMapper.toUserDb(userToUpdate);
        final UserDb updatedUser = userDao.save(userDb);

        log.trace("updateUser.X: Updated user:{}", updatedUser);
        return userMapper.toUser(updatedUser);
    }

    /**
     * Delete {@link User}
     *
     * @param userId ID of user
     * @return deleted user {@link User}
     */
    public User deleteUserById(final UUID userId) {
        log.trace("deleteUserById.E: Delete user from table by ID:{}", userId);

        final var user = selectUserById(userId);

        userDao.deleteById(userId);

        log.trace("deleteUserById.X: User from table by ID:{} was deleted", userId);
        return user;
    }

    /**
     * Checks if {@link User} exist for a given user ID
     *
     * @param userId ID of {@link User}
     * @return true if user exist, false otherwise
     */
    public boolean userExistById(final UUID userId) {
        log.trace("userExistById.E: Check if User exist with provided ID: {} ", userId);

        final var userExistById = userDao.existsById(userId);

        log.trace("userExistById.X: User with id: {}, exist: {}", userId, userExistById);
        return userExistById;
    }

    /**
     * Select all Users
     *
     * @return List of {@link User}
     */
    public List<User> selectAllUsers() {
        log.trace("selectAllUsers.E: select all users");

        final List<User> users = userDao.findAll()
                                        .stream()
                                        .map(userMapper::toUser)
                                        .toList();

        log.trace("selectAllUsers.X: Users:{}", users);
        return users;
    }
}
