package com.ua.yushchenko.service;

import static com.ua.yushchenko.TestData.NOTIFICATION_SETTINGS;
import static com.ua.yushchenko.TestData.SHOW_ID;
import static com.ua.yushchenko.TestData.SUBSCRIPTION;
import static com.ua.yushchenko.TestData.SUBSCRIPTION_ID;
import static com.ua.yushchenko.TestData.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import com.ua.yushchenko.dal.repository.SubscriptionRepository;
import com.ua.yushchenko.events.producer.NotificationSettingEventProducer;
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
    @Mock
    private NotificationSettingEventProducer mockNotificationSettingEventProducer;

    @InjectMocks
    private SubscriptionService unit;

    @Test
    void getSubscriptionsByFilter_nominal_with_user_id() {
        //GIVEN
        when(mockSubscriptionRepository.selectSubscriptionsByUserId(USER_ID)).thenReturn(
                List.of(SUBSCRIPTION));

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

        when(mockUserService.userExistById(USER_ID)).thenReturn(true);
        when(mockShowService.showExistById(SHOW_ID)).thenReturn(true);
        when(mockNotificationSettingsService.createNotificationSettings())
                .thenReturn(NOTIFICATION_SETTINGS);
        when(mockSubscriptionRepository.insertSubscription(subscriptionToCreate)).thenReturn(SUBSCRIPTION);
        doNothing().when(mockNotificationSettingEventProducer)
                   .sendCreatedEvent(NOTIFICATION_SETTINGS);

        //WHEN
        final var subscription = unit.createSubscription(SHOW_ID, USER_ID);

        //THEN
        assertThat(subscription).isNotNull()
                                .isEqualTo(SUBSCRIPTION);

        verify(mockUserService).userExistById(USER_ID);
        verify(mockShowService).showExistById(SHOW_ID);
        verify(mockNotificationSettingsService).createNotificationSettings();
        verify(mockSubscriptionRepository).insertSubscription(subscriptionToCreate);
        verify(mockNotificationSettingEventProducer).sendCreatedEvent(NOTIFICATION_SETTINGS);

        verifyNoMoreInteractions(mockUserService, mockShowService, mockNotificationSettingsService,
                                 mockSubscriptionRepository, mockNotificationSettingEventProducer);
    }

    @Test
    void createSubscription_nominal_user_does_not_exist() {
        //GIVEN
        when(mockUserService.userExistById(USER_ID)).thenReturn(false);

        //WHEN //THEN
        assertThatThrownBy(() -> unit.createSubscription(SHOW_ID, USER_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(String.format("User [ID=%s] doesn't exist in system", USER_ID));

        verify(mockUserService).userExistById(USER_ID);
        verify(mockShowService, never()).getShowById(SHOW_ID);
        verify(mockNotificationSettingsService, never()).createNotificationSettings();
        verify(mockSubscriptionRepository, never()).insertSubscription(SUBSCRIPTION);

        verifyNoMoreInteractions(mockUserService);
        verifyNoInteractions(mockShowService, mockNotificationSettingsService, mockSubscriptionRepository);
    }

    @Test
    void createSubscription_nominal_show_does_not_exist() {
        //GIVEN
        when(mockUserService.userExistById(USER_ID)).thenReturn(true);
        when(mockShowService.showExistById(SHOW_ID)).thenReturn(false);

        //WHEN //THEN
        assertThatThrownBy(() -> unit.createSubscription(SHOW_ID, USER_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Show [ID=%s] doesn't exist in system", SHOW_ID);

        verify(mockUserService).userExistById(USER_ID);
        verify(mockShowService).showExistById(SHOW_ID);
        verify(mockNotificationSettingsService, never()).createNotificationSettings();
        verify(mockSubscriptionRepository, never()).insertSubscription(SUBSCRIPTION);

        verifyNoMoreInteractions(mockUserService, mockShowService);
        verifyNoInteractions(mockNotificationSettingsService, mockSubscriptionRepository);
    }

    @Test
    void deleteSubscription_nominal() {
        //GIVEN
        when(mockSubscriptionRepository.selectSubscription(SUBSCRIPTION_ID)).thenReturn(SUBSCRIPTION);
        doNothing().when(mockSubscriptionRepository).deleteSubscription(SUBSCRIPTION_ID);
        when(mockNotificationSettingsService.deleteNotificationSettings(SUBSCRIPTION.getNotificationSettingsId()))
                .thenReturn(NOTIFICATION_SETTINGS);
        doNothing().when(mockNotificationSettingEventProducer)
                   .sendDeletedEvent(NOTIFICATION_SETTINGS);

        //WHEN
        final var subscription = unit.deleteSubscription(SUBSCRIPTION_ID);

        //THEN
        assertThat(subscription).isNotNull()
                                .isEqualTo(SUBSCRIPTION);

        verify(mockSubscriptionRepository).selectSubscription(SUBSCRIPTION_ID);
        verify(mockNotificationSettingsService).deleteNotificationSettings(SUBSCRIPTION.getNotificationSettingsId());
        verify(mockSubscriptionRepository).deleteSubscription(SUBSCRIPTION_ID);
        verify(mockNotificationSettingEventProducer).sendDeletedEvent(NOTIFICATION_SETTINGS);

        verifyNoMoreInteractions(mockSubscriptionRepository, mockNotificationSettingsService,
                                 mockNotificationSettingEventProducer);
    }

    @Test
    void deleteSubscription_nominal_subscription_does_not_exist() {
        //GIVEN
        when(mockSubscriptionRepository.selectSubscription(SUBSCRIPTION_ID)).thenReturn(null);

        //WHEN
        final var result = unit.deleteSubscription(SUBSCRIPTION_ID);

        //THEN
        assertThat(result)
                .isNull();

        verify(mockSubscriptionRepository).selectSubscription(SUBSCRIPTION_ID);
        verify(mockSubscriptionRepository, never()).deleteSubscription(any());

        verifyNoMoreInteractions(mockSubscriptionRepository);
    }
}