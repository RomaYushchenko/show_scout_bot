package com.ua.yushchenko.controller;

import static com.ua.yushchenko.TestData.SHOW_ID;
import static com.ua.yushchenko.TestData.SUBSCRIPTION;
import static com.ua.yushchenko.TestData.SUBSCRIPTION_API;
import static com.ua.yushchenko.TestData.SUBSCRIPTION_ID;
import static com.ua.yushchenko.TestData.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import com.ua.yushchenko.api.SubscriptionApi;
import com.ua.yushchenko.model.mapper.SubscriptionMapper;
import com.ua.yushchenko.service.SubscriptionService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for {@link SubscriptionController}
 *
 * @author romanyushchenko
 * @version 0.1
 */
@ExtendWith(MockitoExtension.class)
class SubscriptionControllerTest {

    @Mock
    private SubscriptionMapper mockSubscriptionMapper;
    @Mock
    private SubscriptionService mockSubscriptionService;

    @InjectMocks
    private SubscriptionController unit;

    @Test
    void getSubscriptions_nominal_without_user_id() {
        //GIVEN
        when(mockSubscriptionService.getSubscriptionsByFilter(null)).thenReturn(List.of(SUBSCRIPTION));
        when(mockSubscriptionMapper.toSubscriptionApis(List.of(SUBSCRIPTION))).thenReturn(List.of(SUBSCRIPTION_API));

        //WHEN
        final List<SubscriptionApi> subscriptions = unit.getSubscriptions(null);

        //THEN
        assertThat(subscriptions).isNotEmpty()
                                 .hasSize(1)
                                 .first()
                                 .isEqualTo(SUBSCRIPTION_API);

        verify(mockSubscriptionService).getSubscriptionsByFilter(null);
        verify(mockSubscriptionMapper).toSubscriptionApis(List.of(SUBSCRIPTION));
        verifyNoMoreInteractions(mockSubscriptionService, mockSubscriptionMapper);
    }

    @Test
    void getSubscriptions_nominal_with_user_id() {
        //GIVEN
        when(mockSubscriptionService.getSubscriptionsByFilter(USER_ID)).thenReturn(List.of(SUBSCRIPTION));
        when(mockSubscriptionMapper.toSubscriptionApis(List.of(SUBSCRIPTION))).thenReturn(List.of(SUBSCRIPTION_API));

        //WHEN
        final List<SubscriptionApi> subscriptions = unit.getSubscriptions(USER_ID);

        //THEN
        assertThat(subscriptions).isNotEmpty()
                                 .hasSize(1)
                                 .first()
                                 .isEqualTo(SUBSCRIPTION_API);

        verify(mockSubscriptionService).getSubscriptionsByFilter(USER_ID);
        verify(mockSubscriptionMapper).toSubscriptionApis(List.of(SUBSCRIPTION));

        verifyNoMoreInteractions(mockSubscriptionService, mockSubscriptionMapper);
    }

    @Test
    void getSubscription_nominal() {
        //GIVEN
        when(mockSubscriptionService.getSubscription(SUBSCRIPTION_ID)).thenReturn(SUBSCRIPTION);
        when(mockSubscriptionMapper.toSubscriptionApi(SUBSCRIPTION)).thenReturn(SUBSCRIPTION_API);

        //WHEN
        final var subscription = unit.getSubscription(SUBSCRIPTION_ID);

        //THEN
        assertThat(subscription).isNotNull()
                                .isEqualTo(SUBSCRIPTION_API);

        verify(mockSubscriptionService).getSubscription(SUBSCRIPTION_ID);
        verify(mockSubscriptionMapper).toSubscriptionApi(SUBSCRIPTION);

        verifyNoMoreInteractions(mockSubscriptionService, mockSubscriptionMapper);
    }

    @Test
    void getSubscription_nominal_subscription_not_found() {
        //GIVEN
        when(mockSubscriptionService.getSubscription(SUBSCRIPTION_ID)).thenReturn(null);

        //WHEN //THEN
        assertThatThrownBy(() -> unit.getSubscription(SUBSCRIPTION_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(String.format("Subscription by %s doesn't exist in system", SUBSCRIPTION_ID));

        verify(mockSubscriptionService).getSubscription(SUBSCRIPTION_ID);
        verify(mockSubscriptionMapper, never()).toSubscriptionApi(SUBSCRIPTION);

        verifyNoMoreInteractions(mockSubscriptionService);
        verifyNoMoreInteractions(mockSubscriptionMapper);
    }

    @Test
    void getSubscriptionByShowAndUserId_nominal() {
        //GIVEN
        when(mockSubscriptionService.getSubscriptionByShowAndUserId(SHOW_ID, USER_ID)).thenReturn(
                SUBSCRIPTION);
        when(mockSubscriptionMapper.toSubscriptionApi(SUBSCRIPTION)).thenReturn(SUBSCRIPTION_API);

        //WHEN
        final var subscription = unit.getSubscriptionByShowAndUserId(SHOW_ID, USER_ID);

        //THEN
        assertThat(subscription).isNotNull()
                                .isEqualTo(SUBSCRIPTION_API);

        verify(mockSubscriptionService).getSubscriptionByShowAndUserId(SHOW_ID, USER_ID);
        verify(mockSubscriptionMapper).toSubscriptionApi(SUBSCRIPTION);

        verifyNoMoreInteractions(mockSubscriptionService, mockSubscriptionMapper);
    }

    @Test
    void getSubscriptionByShowAndUserId_nominal_subscription_not_found() {
        //GIVEN
        when(mockSubscriptionService.getSubscriptionByShowAndUserId(SHOW_ID, USER_ID)).thenReturn(null);

        //WHEN //THEN
        assertThatThrownBy(() -> unit.getSubscriptionByShowAndUserId(SHOW_ID, USER_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(String.format("Subscription by [showId=%s] and [userId=%s] doesn't exist in system",
                                          SHOW_ID, USER_ID));

        verify(mockSubscriptionService).getSubscriptionByShowAndUserId(SHOW_ID, USER_ID);
        verify(mockSubscriptionMapper, never()).toSubscriptionApi(SUBSCRIPTION);

        verifyNoMoreInteractions(mockSubscriptionService);
        verifyNoMoreInteractions(mockSubscriptionMapper);
    }

    @Test
    void createSubscription_nominal() {
        //GIVEN
        when(mockSubscriptionService.createSubscription(SHOW_ID, USER_ID)).thenReturn(SUBSCRIPTION);
        when(mockSubscriptionMapper.toSubscriptionApi(SUBSCRIPTION)).thenReturn(SUBSCRIPTION_API);

        //WHEN
        final var createdSubscription = unit.createSubscription(SHOW_ID, USER_ID);

        //THEN
        assertThat(createdSubscription).isNotNull()
                                       .isEqualTo(SUBSCRIPTION_API);

        verify(mockSubscriptionService).createSubscription(SHOW_ID, USER_ID);
        verify(mockSubscriptionMapper).toSubscriptionApi(SUBSCRIPTION);

        verifyNoMoreInteractions(mockSubscriptionService, mockSubscriptionMapper);
    }

    @Test
    void deleteSubscription_nominal() {
        //GIVEN
        when(mockSubscriptionService.deleteSubscription(SUBSCRIPTION_ID)).thenReturn(SUBSCRIPTION);
        when(mockSubscriptionMapper.toSubscriptionApi(SUBSCRIPTION)).thenReturn(SUBSCRIPTION_API);

        //WHEN
        final var deletedSubscription = unit.deleteSubscription(SUBSCRIPTION_ID);

        //THEN
        assertThat(deletedSubscription).isNotNull()
                                       .isEqualTo(SUBSCRIPTION_API);

        verify(mockSubscriptionService).deleteSubscription(SUBSCRIPTION_ID);
        verify(mockSubscriptionMapper).toSubscriptionApi(SUBSCRIPTION);

        verifyNoMoreInteractions(mockSubscriptionService, mockSubscriptionMapper);
    }

    @Test
    void deleteSubscription_nominal_subscription_does_not_exist() {
        //GIVEN
        when(mockSubscriptionService.deleteSubscription(SUBSCRIPTION_ID)).thenReturn(null);

        //WHEN //THEN
        assertThatThrownBy(() -> unit.deleteSubscription(SUBSCRIPTION_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Subscription [ID=%s] doesn't exist in system", SUBSCRIPTION_ID);

        verify(mockSubscriptionService).deleteSubscription(SUBSCRIPTION_ID);
        verify(mockSubscriptionMapper, never()).toSubscriptionApi(any());

        verifyNoMoreInteractions(mockSubscriptionService);
        verifyNoInteractions(mockSubscriptionMapper);
    }
}