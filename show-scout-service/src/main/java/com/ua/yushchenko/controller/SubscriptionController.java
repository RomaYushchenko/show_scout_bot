package com.ua.yushchenko.controller;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.ua.yushchenko.api.ShowApi;
import com.ua.yushchenko.api.SubscriptionApi;
import com.ua.yushchenko.api.UserApi;
import com.ua.yushchenko.model.mapper.SubscriptionMapper;
import com.ua.yushchenko.service.SubscriptionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint for communication with Subscription
 *
 * @author romanyushchenko
 * @version 0.1
 */
@Log4j2
@RestController
@RequiredArgsConstructor
public class SubscriptionController {

    @NonNull
    private final SubscriptionMapper subscriptionMapper;
    @NonNull
    private final SubscriptionService subscriptionService;

    /**
     * Get {@link List} of {@link SubscriptionApi} based on filter by User ID. If request params aren't provide, the
     * method will return all exist {@link SubscriptionApi}
     *
     * @param userId ID of User to filtering {@link SubscriptionApi}
     * @return {@link List} of {@link SubscriptionApi}
     */
    @GetMapping("/subscriptions")
    public List<SubscriptionApi> getSubscriptions(@RequestParam(required = false) final Long userId) {
        log.info("getSubscriptions.E: Get Subscriptions filter by userId:{}", userId);

        final var subscriptions = subscriptionService.getSubscriptionsByFilter(userId);
        final var subscriptionApis = subscriptionMapper.toSubscriptionApis(subscriptions);

        log.info("getSubscriptions.X: Subscriptions:{}", subscriptionApis);
        return subscriptionApis;
    }

    /**
     * Get {@link SubscriptionApi} by ID
     *
     * @param subscriptionId ID of {@link SubscriptionApi}
     * @return {@link SubscriptionApi}
     */
    @GetMapping("/subscriptions/{subscriptionId}")
    public SubscriptionApi getSubscription(@PathVariable final UUID subscriptionId) {
        log.info("getSubscription.E: Get Subscription by ID:{}", subscriptionId);

        final var subscription = subscriptionService.getSubscription(subscriptionId);

        if (Objects.isNull(subscription)) {
            throw new EntityNotFoundException("Subscription by " + subscriptionId + " doesn't exist in system");
        }

        final var subscriptionApi = subscriptionMapper.toSubscriptionApi(subscription);

        log.info("getSubscription.X: Subscription:{}", subscriptionApi);
        return subscriptionApi;
    }

    /**
     * Get {@link SubscriptionApi} by ID of {@link ShowApi} and {@link UserApi}
     *
     * @param showId ID of {@link ShowApi}
     * @param userId ID of {@link UserApi}
     * @return {@link SubscriptionApi} by ID of {@link ShowApi} and {@link UserApi}
     */
    @GetMapping("/subscriptions/show/{showId}/user/{userId}")
    public SubscriptionApi getSubscriptionsByShowAndUserId(@PathVariable final UUID showId,
                                                           @PathVariable final Long userId) {
        log.info("getSubscriptionsByShowAndUserId.E: Get Subscription by ShowID:{} and UserID:{}", showId, userId);

        final var subscription = subscriptionService.getSubscriptionByShowAndUserId(showId, userId);

        if (Objects.isNull(subscription)) {
            throw new EntityNotFoundException("Subscription doesn't exist in system");
        }

        final var subscriptionApi = subscriptionMapper.toSubscriptionApi(subscription);

        log.info("getSubscriptionsByShowAndUserId.X: Subscription:{}", subscriptionApi);
        return subscriptionApi;
    }

    /**
     * Create {@link SubscriptionApi} for ID of {@link ShowApi} and {@link UserApi}
     *
     * @param showId ID of {@link ShowApi}
     * @param userId ID of {@link UserApi}
     * @return created {@link SubscriptionApi}
     */
    @PostMapping("/subscriptions/show/{showId}/user/{userId}")
    public SubscriptionApi createSubscription(@PathVariable final UUID showId,
                                              @PathVariable final Long userId) {
        log.info("createSubscription.E: Create Subscription for ShowID:{} and UserID:{}", showId, userId);

        final var createdSubscription = subscriptionService.createSubscription(showId, userId);
        final var subscriptionApi = subscriptionMapper.toSubscriptionApi(createdSubscription);

        log.info("createSubscription.X: Created Subscription:{}", subscriptionApi);
        return subscriptionApi;
    }

    /**
     * Delete {@link SubscriptionApi} by ID
     *
     * @param subscriptionId ID of {@link SubscriptionApi}
     * @return deleted {@link SubscriptionApi}
     */
    @DeleteMapping("/subscriptions/{subscriptionId}")
    public SubscriptionApi deleteSubscription(@PathVariable final UUID subscriptionId) {
        log.info("deleteSubscription.E: Delete Subscription by ID:{}", subscriptionId);

        final var deletedSubscription = subscriptionService.deleteSubscription(subscriptionId);
        final var subscriptionApi = subscriptionMapper.toSubscriptionApi(deletedSubscription);

        log.info("deleteSubscription.X: Deleted Subscription:{}", subscriptionApi);
        return subscriptionApi;
    }
}
