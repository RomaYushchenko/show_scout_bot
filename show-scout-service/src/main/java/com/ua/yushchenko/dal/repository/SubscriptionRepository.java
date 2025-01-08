package com.ua.yushchenko.dal.repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

import com.ua.yushchenko.dal.dao.SubscriptionDao;
import com.ua.yushchenko.model.domain.Show;
import com.ua.yushchenko.model.domain.Subscription;
import com.ua.yushchenko.model.domain.User;
import com.ua.yushchenko.model.mapper.SubscriptionMapper;
import com.ua.yushchenko.model.persistence.SubscriptionDb;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * Repository to work with {@link SubscriptionDb}
 *
 * @author romanyushchenko
 * @version 0.1
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class SubscriptionRepository {

    @NonNull
    private final SubscriptionDao subscriptionDao;
    @NonNull
    private final SubscriptionMapper subscriptionMapper;

    /**
     * Select all {@link Subscription}
     *
     * @return {@link List} of {@link Subscription}
     */
    public List<Subscription> selectSubscriptions() {
        log.trace("selectSubscriptions.E: Selecting all subscriptions");

        final var subscriptions = StreamSupport.stream(subscriptionDao.findAll().spliterator(), false)
                                               .map(subscriptionMapper::toSubscription)
                                               .toList();

        log.trace("selectSubscriptions.X: Returned {} subscriptions {}", subscriptions.size(), subscriptions);
        return subscriptions;
    }

    /**
     * Select all {@link Subscription} for user
     *
     * @param userId ID of {@link User}
     * @return {@link List} of {@link Subscription} for user
     */
    public List<Subscription> selectSubscriptionsByUserId(final Long userId) {
        log.trace("selectSubscriptionsByUserId.E: Selecting all subscriptions for user {}", userId);

        final var subscriptions = StreamSupport.stream(subscriptionDao.findAllByUserId(userId).spliterator(), false)
                                               .map(subscriptionMapper::toSubscription)
                                               .toList();

        log.trace("selectSubscriptionsByUserId.X: Returned {} subscriptions for user {}. Subscriptions:{}",
                  subscriptions.size(), userId, subscriptions);
        return subscriptions;
    }

    /**
     * Select {@link Subscription} by ID
     *
     * @param subscriptionId ID of {@link Subscription}
     * @return {@link Subscription}
     */
    public Subscription selectSubscription(final UUID subscriptionId) {
        log.trace("selectSubscription.E: Selecting subscription with id {}", subscriptionId);

        final var subscription = subscriptionDao.findById(subscriptionId)
                                                .map(subscriptionMapper::toSubscription)
                                                .orElse(null);

        log.trace("selectSubscription.X: Returned subscription {} with id {}", subscription, subscriptionId);
        return subscription;
    }

    /**
     * Select {@link Subscription} by ID of {@link Show} and {@link User}
     *
     * @param showId ID of {@link Show}
     * @param userId ID of {@link User}
     * @return {@link Subscription}
     */
    public Subscription selectSubscriptionByShowAndUserId(final UUID showId, final Long userId) {
        log.trace("selectSubscriptionByShowAndUserId.E: Selecting subscription with showId:{} and userId:{}",
                  showId, userId);

        final var subscription = subscriptionDao.findSubscriptionDbByShowIdAndUserId(showId, userId)
                                                .map(subscriptionMapper::toSubscription)
                                                .orElse(null);

        log.trace("selectSubscriptionByShowAndUserId.X: Returned subscription {}", subscription);
        return subscription;
    }

    /**
     * Insert {@link Subscription}
     *
     * @param subscription instance of {@link Subscription} to insert
     * @return inserted {@link Subscription}
     */
    public Subscription insertSubscription(final Subscription subscription) {
        log.trace("createSubscription.E: Creating subscription {}", subscription);

        final var subscriptionDbToCreate = subscriptionMapper.toSubscriptionDb(subscription);
        final var createdSubscriptionDb = subscriptionDao.save(subscriptionDbToCreate);

        log.trace("createSubscription.E: Created subscription {}", createdSubscriptionDb);
        return subscriptionMapper.toSubscription(createdSubscriptionDb);
    }

    /**
     * Delete {@link Subscription}
     *
     * @param subscriptionId ID of {@link Subscription}
     */
    public void deleteSubscription(final UUID subscriptionId) {
        log.trace("deleteSubscription.E: Deleting subscription with id {}", subscriptionId);
        subscriptionDao.deleteById(subscriptionId);

        log.trace("deleteSubscription.E: Deleted subscription with id {}", subscriptionId);
    }
}
