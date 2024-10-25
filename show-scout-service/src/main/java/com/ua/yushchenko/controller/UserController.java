package com.ua.yushchenko.controller;

import com.ua.yushchenko.api.UserApi;
import com.ua.yushchenko.model.domain.User;
import com.ua.yushchenko.model.mapper.UserMapper;
import com.ua.yushchenko.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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

        final User user = userService.getUserById(userID);
        final UserApi userApi = userMapper.toUserApi(user);

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

        final User userToCreate = userMapper.toUser(userApi);
        final User createdUser = userService.createUser(userToCreate);
        final UserApi createdUserApi = userMapper.toUserApi(createdUser);

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

        final User userToUpdate = userMapper.toUser(userApi);
        final User updatedUser = userService.updateUser(userID, userToUpdate);
        final UserApi updatedUserApi = userMapper.toUserApi(updatedUser);

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

        final User deletedUser = userService.deleteUser(userID);
        final UserApi deletedUserApi = userMapper.toUserApi(deletedUser);

        return deletedUserApi;
    }
}
