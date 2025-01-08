package com.ua.yushchenko.service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.ua.yushchenko.dal.repository.SubscriptionRepository;
import com.ua.yushchenko.model.domain.Show;
import com.ua.yushchenko.model.domain.Subscription;
import com.ua.yushchenko.model.domain.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * Service that exposes the base functionality for interacting with {@link Subscription} data
 *
 * @author romanyushchenko
 * @version 0.1
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class SubscriptionService {

    @NonNull
    private final SubscriptionRepository subscriptionRepository;
    @NonNull
    private final UserService userService;
    @NonNull
    private final ShowService showService;

    /**
     * Get {@link List} of {@link Subscription} based on filter by User ID. If request params aren't provide, the
     * method will return all exist {@link Subscription}
     *
     * @param userId ID of {@link User}
     * @return {@link List} of {@link Subscription}
     */
    public List<Subscription> getSubscriptionsByFilter(final Long userId) {
        log.debug("getSubscriptionsByFilter.E: Get Subscriptions filter by userId:{}", userId);

        final var subscriptions = Objects.isNull(userId)
                ? subscriptionRepository.selectSubscriptions()
                : subscriptionRepository.selectSubscriptionsByUserId(userId);

        log.debug("getSubscriptionsByFilter.X: Subscriptions:{}", subscriptions);
        return subscriptions;
    }

    /**
     * Get {@link Subscription} by ID
     *
     * @param subscriptionId ID of {@link Subscription}
     * @return {@link Subscription}
     */
    public Subscription getSubscription(final UUID subscriptionId) {
        log.debug("getSubscription.E: Get Subscription by id:{}", subscriptionId);

        final var subscription = subscriptionRepository.selectSubscription(subscriptionId);

        log.debug("getSubscription.X: Subscription:{}", subscription);
        return subscription;
    }

    /**
     * Get {@link Subscription} by ID of {@link Show} and {@link User}
     *
     * @param showId ID of {@link Show}
     * @param userId ID of {@link User}
     * @return {@link Subscription} by ID of {@link Show} and {@link User}
     */
    public Subscription getSubscriptionByShowAndUserId(final UUID showId, final Long userId) {
        log.debug("getSubscriptionByShowAndUserId.E: Get Subscription by showId:{}, userId:{}", showId, userId);

        final var subscription = subscriptionRepository.selectSubscriptionByShowAndUserId(showId, userId);

        log.debug("getSubscriptionByShowAndUserId.E: Subscription:{}", subscription);
        return subscription;
    }

    /**
     * Create {@link Subscription} for ID of {@link Show} and {@link User}
     *
     * @param showId ID of {@link Show}
     * @param userId ID of {@link User}
     * @return Created {@link Subscription}
     */
    public Subscription createSubscription(final UUID showId, final long userId) {
        log.debug("createSubscription.E: Create Subscription with showId:{}, userId:{}", showId, userId);

        final var user = userService.getUserById(userId);

        if (Objects.isNull(user)) {
            throw new EntityNotFoundException("User doesn't exist in system");
        }

        final var show = showService.getShowById(showId);

        if (Objects.isNull(show)) {
            throw new EntityNotFoundException("Show doesn't exist in system");
        }

        final var subscriptionToCreate = Subscription.builder()
                                                     .userId(userId)
                                                     .showId(showId)
                                                     .build();

        final var createdSubscription = subscriptionRepository.insertSubscription(subscriptionToCreate);

        log.debug("createSubscription.X: Created Subscription:{}", createdSubscription);
        return createdSubscription;
    }

    /**
     * Delete {@link Subscription} by ID
     *
     * @param subscriptionId ID of {@link Subscription}
     * @return Deleted {@link Subscription}
     */
    public Subscription deleteSubscription(final UUID subscriptionId) {
        log.debug("deleteSubscription.E: Delete Subscription by id:{}", subscriptionId);

        final var subscription = getSubscription(subscriptionId);

        if (Objects.isNull(subscription)) {
            throw new EntityNotFoundException("Subscription doesn't exist in system");
        }

        subscriptionRepository.deleteSubscription(subscriptionId);

        log.debug("deleteSubscription.X: Deleted Subscription:{}", subscription);
        return subscription;
    }
}
