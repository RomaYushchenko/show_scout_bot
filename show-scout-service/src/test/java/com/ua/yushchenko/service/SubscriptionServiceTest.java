package com.ua.yushchenko.service;

import static com.ua.yushchenko.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import com.ua.yushchenko.dal.repository.SubscriptionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for {@link SubscriptionService}
 *
 * @author romanyushchenko
 * @version 0.1
 */
@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepository mockSubscriptionRepository;
    @Mock
    private UserService mockUserService;
    @Mock
    private ShowService mockShowService;
    @Mock
    private NotificationSettingsService mockNotificationSettingsService;

    @InjectMocks
    private SubscriptionService unit;

    @Test
    void getSubscriptionsByFilter_nominal_with_user_id() {
        //GIVEN
        when(mockSubscriptionRepository.selectSubscriptionsByUserId(USER_ID)).thenReturn(List.of(SUBSCRIPTION));

        //WHEN
        final var subscriptions = unit.getSubscriptionsByFilter(USER_ID);

        //THEN
        assertThat(subscriptions).isNotEmpty()
                .hasSize(1)
                .first()
                .isEqualTo(SUBSCRIPTION);

        verify(mockSubscriptionRepository).selectSubscriptionsByUserId(USER_ID);
        verify(mockSubscriptionRepository, never()).selectSubscriptions();

        verifyNoMoreInteractions(mockSubscriptionRepository);
    }

    @Test
    void getSubscriptionsByFilter_nominal_without_user_id() {
        //GIVEN
        when(mockSubscriptionRepository.selectSubscriptions()).thenReturn(List.of(SUBSCRIPTION));

        //WHEN
        final var subscriptions = unit.getSubscriptionsByFilter(null);

        //THEN
        assertThat(subscriptions).isNotEmpty()
                .hasSize(1)
                .first()
                .isEqualTo(SUBSCRIPTION);

        verify(mockSubscriptionRepository, never()).selectSubscriptionsByUserId(USER_ID);
        verify(mockSubscriptionRepository).selectSubscriptions();

        verifyNoMoreInteractions(mockSubscriptionRepository);
    }

    @Test
    void getSubscription_nominal() {
        //GIVEN
        when(mockSubscriptionRepository.selectSubscription(SUBSCRIPTION_ID)).thenReturn(SUBSCRIPTION);

        //WHEN
        final var subscription = unit.getSubscription(SUBSCRIPTION_ID);

        //THEN
        assertThat(subscription).isNotNull()
                .isEqualTo(SUBSCRIPTION);

        verify(mockSubscriptionRepository).selectSubscription(SUBSCRIPTION_ID);

        verifyNoMoreInteractions(mockSubscriptionRepository);
    }

    @Test
    void getSubscriptionByShowAndUserId_nominal() {
        //GIVEN
        when(mockSubscriptionRepository.selectSubscriptionByShowAndUserId(SHOW_ID, USER_ID)).thenReturn(SUBSCRIPTION);

        //WHEN
        final var subscription = unit.getSubscriptionByShowAndUserId(SHOW_ID, USER_ID);

        //THEN
        assertThat(subscription).isNotNull()
                .isEqualTo(SUBSCRIPTION);

        verify(mockSubscriptionRepository).selectSubscriptionByShowAndUserId(SHOW_ID, USER_ID);

        verifyNoMoreInteractions(mockSubscriptionRepository);
    }

    @Test
    void createSubscription_nominal() {
        //GIVEN
        final var subscriptionToCreate = SUBSCRIPTION.toBuilder()
                .subscriptionId(null)
                .build();

        when(mockUserService.getUserById(USER_ID)).thenReturn(USER);
        when(mockShowService.getShowById(SHOW_ID)).thenReturn(SHOW);
        when(mockNotificationSettingsService.createNotificationSettings())
                .thenReturn(NOTIFICATION_SETTINGS);
        when(mockSubscriptionRepository.insertSubscription(subscriptionToCreate)).thenReturn(SUBSCRIPTION);

        //WHEN
        final var subscription = unit.createSubscription(SHOW_ID, USER_ID);

        //THEN
        assertThat(subscription).isNotNull()
                .isEqualTo(SUBSCRIPTION);

        verify(mockUserService).getUserById(USER_ID);
        verify(mockShowService).getShowById(SHOW_ID);
        verify(mockNotificationSettingsService).createNotificationSettings();
        verify(mockSubscriptionRepository).insertSubscription(subscriptionToCreate);

        verifyNoMoreInteractions(mockUserService, mockShowService, mockNotificationSettingsService, mockSubscriptionRepository);
    }

    @Test
    void createSubscription_nominal_user_does_not_exist() {
        //GIVEN
        when(mockUserService.getUserById(USER_ID)).thenReturn(null);

        //WHEN //THEN
        assertThatThrownBy(() -> unit.createSubscription(SHOW_ID, USER_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(String.format("User [ID=%s] doesn't exist in system", USER_ID));

        verify(mockUserService).getUserById(USER_ID);
        verify(mockShowService, never()).getShowById(SHOW_ID);
        verify(mockSubscriptionRepository, never()).insertSubscription(SUBSCRIPTION);

        verifyNoMoreInteractions(mockUserService);
        verifyNoInteractions(mockShowService, mockSubscriptionRepository);
    }

    @Test
    void createSubscription_nominal_show_does_not_exist() {
        //GIVEN
        when(mockUserService.getUserById(USER_ID)).thenReturn(USER);
        when(mockShowService.getShowById(SHOW_ID)).thenReturn(null);

        //WHEN //THEN
        assertThatThrownBy(() -> unit.createSubscription(SHOW_ID, USER_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Show [ID=%s] doesn't exist in system", SHOW_ID);

        verify(mockUserService).getUserById(USER_ID);
        verify(mockShowService).getShowById(SHOW_ID);
        verify(mockSubscriptionRepository, never()).insertSubscription(SUBSCRIPTION);

        verifyNoMoreInteractions(mockUserService, mockShowService);
        verifyNoInteractions(mockSubscriptionRepository);
    }

    @Test
    void deleteSubscription_nominal() {
        //GIVEN
        when(mockSubscriptionRepository.selectSubscription(SUBSCRIPTION_ID)).thenReturn(SUBSCRIPTION);
        doNothing().when(mockSubscriptionRepository).deleteSubscription(SUBSCRIPTION_ID);
        when(mockNotificationSettingsService.deleteNotificationSettings(SUBSCRIPTION.getNotificationSettingsId()))
                .thenReturn(NOTIFICATION_SETTINGS);

        //WHEN
        final var subscription = unit.deleteSubscription(SUBSCRIPTION_ID);

        //THEN
        assertThat(subscription).isNotNull()
                .isEqualTo(SUBSCRIPTION);

        verify(mockSubscriptionRepository).selectSubscription(SUBSCRIPTION_ID);
        verify(mockNotificationSettingsService).deleteNotificationSettings(SUBSCRIPTION.getNotificationSettingsId());
        verify(mockSubscriptionRepository).deleteSubscription(SUBSCRIPTION_ID);

        verifyNoMoreInteractions(mockSubscriptionRepository, mockNotificationSettingsService);
    }

    @Test
    void deleteSubscription_nominal_subscription_does_not_exist() {
        //GIVEN
        when(mockSubscriptionRepository.selectSubscription(SUBSCRIPTION_ID)).thenReturn(null);

        //WHEN //THEN
        assertThatThrownBy(() -> unit.deleteSubscription(SUBSCRIPTION_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Subscription [ID=%s] doesn't exist in system", SUBSCRIPTION_ID);

        verify(mockSubscriptionRepository).selectSubscription(SUBSCRIPTION_ID);
        verify(mockSubscriptionRepository, never()).deleteSubscription(SUBSCRIPTION_ID);

        verifyNoMoreInteractions(mockSubscriptionRepository);
    }
}