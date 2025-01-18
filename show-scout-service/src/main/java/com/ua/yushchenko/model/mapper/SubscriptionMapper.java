package com.ua.yushchenko.model.mapper;

import java.util.List;

import com.ua.yushchenko.api.SubscriptionApi;
import com.ua.yushchenko.config.MapperConfiguration;
import com.ua.yushchenko.model.domain.Subscription;
import com.ua.yushchenko.model.persistence.SubscriptionDb;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;

/**
 * Class to represent mapping User entity.
 *
 * @author romanyushchenko
 * @version 0.1
 */
@Mapper(config = MapperConfiguration.class)
public interface SubscriptionMapper {

    SubscriptionApi toSubscriptionApi(final Subscription subscription);

    Subscription toSubscription(final SubscriptionApi subscriptionApi);

    Subscription toSubscription(final SubscriptionDb subscriptionDb);

    SubscriptionDb toSubscriptionDb(final Subscription subscription);

    List<SubscriptionApi> toSubscriptionApis(final List<Subscription> subscriptions);

    @ObjectFactory
    default Subscription.SubscriptionBuilder createDomainBuilder() {
        return Subscription.builder();
    }
}
