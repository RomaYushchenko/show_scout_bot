package com.ua.yushchenko.service;

import java.util.Objects;

import com.ua.yushchenko.dal.repository.UserRepository;
import com.ua.yushchenko.model.domain.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service that exposes the base functionality for interacting with {@link User} data
 *
 * @author romanyushchenko
 * @version 0.1
 */
@Service
@RequiredArgsConstructor
public class UserService {

    @NonNull
    private UserRepository userRepository;

    /**
     * Get {@link User} by current ID
     *
     * @param userId ID of {@link User}
     * @return {@link User} by current ID
     */
    public User getUserById(final Long userId) {
        return userRepository.selectUserById(userId);
    }

    /**
     * Create {@link User}
     *
     * @param user instance of {@link User} to create
     * @return created {@link User}
     */
    public User createUser(final User user) {
        return userRepository.insertUser(user);
    }

    /**
     * Update {@link User}
     *
     * @param userId ID of {@link User}
     * @param userToUpdate   instance of {@link User} to update
     * @return updated {@link User}
     */
    public User updateUser(final Long userId, final User userToUpdate) {
        final User user = getUserById(userId);

        if (Objects.isNull(user)) {
            return null;
        }

        if (Objects.equals(userToUpdate, user)) {
            return user;
        }

        return userRepository.updateUser(userToUpdate);
    }

    /**
     * Delete {@link User} by current ID
     *
     * @param userId Id of {@link User}
     * @return deleted {@link User}
     */
    public User deleteUser(final Long userId) {
        final User user = getUserById(userId);

        if (Objects.isNull(user)) {
            return null;
        }

        userRepository.deleteUserById(userId);

        return user;
    }
}
