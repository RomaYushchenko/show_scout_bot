package com.ua.yushchenko.it.provider;

import java.util.UUID;

import com.ua.yushchenko.api.UserApi;
import com.ua.yushchenko.common.configuration.ShowScoutObjectMapper;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * Class provide logic for getting User resources
 *
 * @author romanyushchenko
 * @version 0.1
 */
public class UserServiceClientProvider extends BaseServiceClientProvider {

    public UserServiceClientProvider(final TestRestTemplate restTemplate,
                                     final ShowScoutObjectMapper objectMapper,
                                     final int port) {
        super(restTemplate, objectMapper, port);
    }

    /**
     * GET user request by ID of User
     *
     * @param userId ID of User
     * @return user response by ID of User
     */
    public ResponseEntity<UserApi> getUserRequest(final UUID userId) {
        return restTemplate.getForEntity(url("/users/" + userId.toString()), UserApi.class);
    }

    /**
     * POST user request
     *
     * @param userApi instance of {@link UserApi} to create
     * @return user response
     */
    public ResponseEntity<UserApi> postUserRequest(final UserApi userApi) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return restTemplate.exchange(url("/users"),
                                     HttpMethod.POST,
                                     new HttpEntity<>(userApi, headers),
                                     UserApi.class);
    }

    /**
     * PUT user request
     *
     * @param userApi instance of {@link UserApi} to update
     * @return user response
     */
    public ResponseEntity<UserApi> putUserRequest(final UserApi userApi) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return restTemplate.exchange(url("/users/" + userApi.getUserId().toString()),
                                     HttpMethod.PUT,
                                     new HttpEntity<>(userApi, headers),
                                     UserApi.class);
    }

    /**
     * DELETE user request by ID of User
     *
     * @param userId ID of User
     * @return user response by ID of User
     */
    public ResponseEntity<UserApi> deletedUserRequest(final UUID userId) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return restTemplate.exchange(url("/users/" + userId),
                                     HttpMethod.DELETE,
                                     new HttpEntity<>(headers),
                                     UserApi.class);
    }
}
