package com.ua.yushchenko.service;

import java.util.Objects;
import java.util.UUID;

import com.ua.yushchenko.dal.repository.UserRepository;
import com.ua.yushchenko.model.domain.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * Service that exposes the base functionality for interacting with {@link User} data
 *
 * @author romanyushchenko
 * @version 0.1
 */
@Log4j2
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
    public User getUserById(final UUID userId) {
        log.debug("getUserById.E: Get User by ID:{}", userId);

        final User user = userRepository.selectUserById(userId);

        log.debug("getUserById.X: User:{}", user);
        return user;
    }

    /**
     * Create {@link User}
     *
     * @param user instance of {@link User} to create
     * @return created {@link User}
     */
    public User createUser(final User user) {
        log.debug("createUser.E: Create new user");

        final User createdUser = userRepository.insertUser(user);

        log.debug("createUser.X: Created user:{}", createdUser);
        return createdUser;
    }

    /**
     * Update {@link User}
     *
     * @param userId       ID of {@link User}
     * @param userToUpdate instance of {@link User} to update
     * @return updated {@link User}
     */
    public User updateUser(final UUID userId, final User userToUpdate) {
        log.debug("updateUser.E: Update user by ID:{}", userId);
        final User user = getUserById(userId);

        if (Objects.isNull(user)) {
            log.warn("updateUser.X: User doesn't find in system");
            return null;
        }

        if (Objects.equals(userToUpdate, user)) {
            log.debug("updateUser.X: User didn't update due to same object");
            return user;
        }

        final User updatedUser = userRepository.updateUser(userToUpdate);

        log.debug("updateUser.X: Updated user:{}", updatedUser);
        return updatedUser;
    }

    /**
     * Delete {@link User} by current ID
     *
     * @param userId ID of {@link User}
     * @return deleted {@link User}
     */
    public User deleteUser(final UUID userId) {
        log.debug("deleteUser.E: Delete user by ID:{}", userId);

        final var isUserExist = userRepository.userExistById(userId);

        if (!isUserExist) {
            log.warn("deleteUser.X: User doesn't find in system");
            return null;
        }

        final var user = userRepository.deleteUserById(userId);

        log.debug("deleteUser.X: Deleted user :{}", user);
        return user;
    }

    /**
     * Checks if {@link User} exist for a given user ID
     *
     * @param userId ID of {@link User}
     * @return true if user exist, false otherwise
     */
    public boolean userExistById(final UUID userId) {
        log.debug("userExistById.E: Check if User exist with provided ID: {} ", userId);

        final var userExistById = userRepository.userExistById(userId);

        log.debug("userExistById.X: User with id: {}, exist: {}", userId, userExistById);
        return userExistById;
    }
}
