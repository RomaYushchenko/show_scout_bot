package com.ua.yushchenko.controller;

import java.util.Objects;

import com.ua.yushchenko.api.UserApi;
import com.ua.yushchenko.model.domain.User;
import com.ua.yushchenko.model.mapper.UserMapper;
import com.ua.yushchenko.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint for communication with Clients
 *
 * @author romanyushchenko
 * @version 0.1
 */
@Log4j2
@RestController
@RequiredArgsConstructor
public class UserController {

    @NonNull
    private final UserService userService;
    @NonNull
    private final UserMapper userMapper;

    /**
     * Get {@link UserApi} by ID
     *
     * @param userID ID of user
     * @return {@link UserApi}
     */
    @GetMapping("/users/{userId}")
    public UserApi getUser(@PathVariable("userId") final Long userID) {
        log.info("getUser.E: Get User by ID:{}", userID);

        final User user = userService.getUserById(userID);

        if (Objects.isNull(user)) {
            throw new EntityNotFoundException("User doesn't exist in system");
        }

        final UserApi userApi = userMapper.toUserApi(user);

        log.info("getUser.X: user:{}", userApi);
        return userApi;
    }

    /**
     * Create {@link UserApi}
     *
     * @param userApi instance of {@link UserApi} to create
     * @return created {@link UserApi}
     */
    @PostMapping("/users")
    public UserApi createUser(@RequestBody final UserApi userApi) {
        log.info("createUser.E: Create user");

        final User userToCreate = userMapper.toUser(userApi);
        final User createdUser = userService.createUser(userToCreate);
        final UserApi createdUserApi = userMapper.toUserApi(createdUser);

        log.info("createUser.X: Created user:{}", createdUserApi);
        return createdUserApi;
    }

    /**
     * Update {@link UserApi}
     *
     * @param userID  ID of user
     * @param userApi instance of {@link UserApi} to update
     * @return updated {@link UserApi}
     */
    @PutMapping("/users/{userId}")
    public UserApi updateUser(@PathVariable("userId") final Long userID,
                              @RequestBody final UserApi userApi) {
        log.info("updateUser.E: Update user by ID:{}", userID);

        if (!Objects.equals(userID, userApi.getUserID())) {
            throw new IllegalArgumentException("PathVariable userId: " + userID
                    + " does not match with RequestBody userApi.getUserID(): "
                    + userApi.getUserID());
        }

        final User userToUpdate = userMapper.toUser(userApi);
        final User updatedUser = userService.updateUser(userID, userToUpdate);

        if (Objects.isNull(updatedUser)) {
            throw new EntityNotFoundException("User with userId: " + userID + " doesn't exist in system");
        }

        final UserApi updatedUserApi = userMapper.toUserApi(updatedUser);

        log.info("updateUser.X: Updated user:{}", updatedUserApi);
        return updatedUserApi;
    }

    /**
     * Delete {@link UserApi} by ID
     *
     * @param userID ID of user
     * @return deleted {@link UserApi}
     */
    @DeleteMapping("/users/{userId}")
    public UserApi deleteUser(@PathVariable("userId") final Long userID) {
        log.info("deleteUser.E: Delete user by ID:{}", userID);

        final User deletedUser = userService.deleteUser(userID);

        if (Objects.isNull(deletedUser)) {
            throw new EntityNotFoundException("User doesn't exist in system");
        }

        final UserApi deletedUserApi = userMapper.toUserApi(deletedUser);

        log.info("deleteUser.X: Deleted user:{}", deletedUserApi);
        return deletedUserApi;
    }
}
