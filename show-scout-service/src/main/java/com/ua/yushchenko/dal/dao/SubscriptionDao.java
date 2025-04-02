package com.ua.yushchenko.dal.dao;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ua.yushchenko.model.persistence.SubscriptionDb;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to work with {@link SubscriptionDb} table
 *
 * @author romanyushchenko
 * @version v.0.1
 */
@Repository
public interface SubscriptionDao extends ListCrudRepository<SubscriptionDb, UUID> {

    List<SubscriptionDb> findAllByUserId(final Long userId);

    Optional<SubscriptionDb> findSubscriptionDbByShowIdAndUserId(final UUID showId, final Long userId);
}
