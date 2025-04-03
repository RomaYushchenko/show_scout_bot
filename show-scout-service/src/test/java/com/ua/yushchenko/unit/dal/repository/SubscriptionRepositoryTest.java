package com.ua.yushchenko.unit.dal.repository;

import static com.ua.yushchenko.unit.TestData.SHOW_ID;
import static com.ua.yushchenko.unit.TestData.SUBSCRIPTION;
import static com.ua.yushchenko.unit.TestData.SUBSCRIPTION_DB;
import static com.ua.yushchenko.unit.TestData.SUBSCRIPTION_ID;
import static com.ua.yushchenko.unit.TestData.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import com.ua.yushchenko.dal.dao.SubscriptionDao;
import com.ua.yushchenko.model.mapper.SubscriptionMapper;
import com.ua.yushchenko.dal.repository.SubscriptionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for {@link SubscriptionRepository}
 *
 * @author romanyushchenko
 * @version 0.1
 */
@ExtendWith(MockitoExtension.class)
class SubscriptionRepositoryTest {

    @Mock
    private SubscriptionDao mockSubscriptionDao;
    @Mock
    private SubscriptionMapper mockSubscriptionMapper;

    @InjectMocks
    private SubscriptionRepository unit;

    @Test
    void selectSubscriptions_nominal() {
        //GIVEN
        when(mockSubscriptionDao.findAll()).thenReturn(List.of(SUBSCRIPTION_DB));
        when(mockSubscriptionMapper.toSubscriptions(List.of(SUBSCRIPTION_DB))).thenReturn(List.of(SUBSCRIPTION));

        //WHEN
        final var subscriptions = unit.selectSubscriptions();

        //THEN
        assertThat(subscriptions).isNotEmpty()
                                 .hasSize(1)
                                 .first()
                                 .isEqualTo(SUBSCRIPTION);

        verify(mockSubscriptionDao).findAll();
        verify(mockSubscriptionMapper).toSubscriptions(List.of(SUBSCRIPTION_DB));

        verifyNoMoreInteractions(mockSubscriptionDao, mockSubscriptionMapper);
    }

    @Test
    void selectSubscriptionsByUserId_nominal() {
        //GIVEN
        when(mockSubscriptionDao.findAllByUserId(USER_ID)).thenReturn(List.of(SUBSCRIPTION_DB));
        when(mockSubscriptionMapper.toSubscriptions(List.of(SUBSCRIPTION_DB))).thenReturn(List.of(SUBSCRIPTION));

        //WHEN
        final var subscriptions = unit.selectSubscriptionsByUserId(USER_ID);

        //THEN
        assertThat(subscriptions).isNotEmpty()
                                 .hasSize(1)
                                 .first()
                                 .isEqualTo(SUBSCRIPTION);

        verify(mockSubscriptionDao).findAllByUserId(USER_ID);
        verify(mockSubscriptionMapper).toSubscriptions(List.of(SUBSCRIPTION_DB));

        verifyNoMoreInteractions(mockSubscriptionDao, mockSubscriptionMapper);
    }

    @Test
    void selectSubscription_nominal() {
        //GIVEN
        when(mockSubscriptionDao.findById(SUBSCRIPTION_ID)).thenReturn(Optional.of(SUBSCRIPTION_DB));
        when(mockSubscriptionMapper.toSubscription(SUBSCRIPTION_DB)).thenReturn(SUBSCRIPTION);

        //WHEN
        final var subscription = unit.selectSubscription(SUBSCRIPTION_ID);

        //THEN
        assertThat(subscription).isNotNull()
                                .isEqualTo(SUBSCRIPTION);

        verify(mockSubscriptionDao).findById(SUBSCRIPTION_ID);
        verify(mockSubscriptionMapper).toSubscription(SUBSCRIPTION_DB);

        verifyNoMoreInteractions(mockSubscriptionDao, mockSubscriptionMapper);
    }

    @Test
    void selectSubscriptionByShowAndUserId_nominal() {
        //GIVEN
        when(mockSubscriptionDao.findSubscriptionDbByShowIdAndUserId(SHOW_ID, USER_ID))
                .thenReturn(Optional.of(SUBSCRIPTION_DB));
        when(mockSubscriptionMapper.toSubscription(SUBSCRIPTION_DB)).thenReturn(SUBSCRIPTION);

        //WHEN
        final var subscription = unit.selectSubscriptionByShowAndUserId(SHOW_ID, USER_ID);

        //THEN
        assertThat(subscription).isNotNull()
                                .isEqualTo(SUBSCRIPTION);

        verify(mockSubscriptionDao).findSubscriptionDbByShowIdAndUserId(SHOW_ID, USER_ID);
        verify(mockSubscriptionMapper).toSubscription(SUBSCRIPTION_DB);

        verifyNoMoreInteractions(mockSubscriptionDao, mockSubscriptionMapper);
    }

    @Test
    void insertSubscription_nominal() {
        //GIVEN
        when(mockSubscriptionMapper.toSubscriptionDb(SUBSCRIPTION)).thenReturn(SUBSCRIPTION_DB);
        when(mockSubscriptionDao.save(SUBSCRIPTION_DB)).thenReturn(SUBSCRIPTION_DB);
        when(mockSubscriptionMapper.toSubscription(SUBSCRIPTION_DB)).thenReturn(SUBSCRIPTION);

        //WHEN
        unit.insertSubscription(SUBSCRIPTION);

        //THEN
        verify(mockSubscriptionMapper).toSubscriptionDb(SUBSCRIPTION);
        verify(mockSubscriptionDao).save(SUBSCRIPTION_DB);
        verify(mockSubscriptionMapper).toSubscription(SUBSCRIPTION_DB);

        verifyNoMoreInteractions(mockSubscriptionDao, mockSubscriptionMapper);
    }

    @Test
    void deleteSubscription_nominal() {
        //GIVEN
        doNothing().when(mockSubscriptionDao).deleteById(SUBSCRIPTION_ID);

        //WHEN
        unit.deleteSubscription(SUBSCRIPTION_ID);

        //THEN
        verify(mockSubscriptionDao).deleteById(SUBSCRIPTION_ID);

        verifyNoMoreInteractions(mockSubscriptionDao);
    }
}