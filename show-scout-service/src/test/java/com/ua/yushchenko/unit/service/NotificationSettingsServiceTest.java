package com.ua.yushchenko.unit.service;

import static com.ua.yushchenko.unit.TestData.NOTIFICATION_SETTINGS;
import static com.ua.yushchenko.unit.TestData.NOTIFICATION_SETTINGS_ID;
import static com.ua.yushchenko.unit.TestData.SUBSCRIPTION_ID;
import static com.ua.yushchenko.unit.TestData.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import com.ua.yushchenko.dal.repository.NotificationSettingsRepository;
import com.ua.yushchenko.events.producer.NotificationSettingEventProducer;
import com.ua.yushchenko.model.domain.NotificationSettings;
import com.ua.yushchenko.service.NotificationSettingsService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class NotificationSettingsServiceTest {

    @Mock
    private NotificationSettingsRepository mockNotificationSettingsRepository;
    @Captor
    ArgumentCaptor<NotificationSettings> notificationSettingsArgumentCaptor;
    @Mock
    private NotificationSettingEventProducer mockNotificationSettingEventProducer;

    @InjectMocks
    private NotificationSettingsService unit;

    @Test
    void createNotificationSettings_nominal() {
        //GIVEN
        when(mockNotificationSettingsRepository
                     .insertNotificationSettings(notificationSettingsArgumentCaptor.capture()))
                .thenReturn(NOTIFICATION_SETTINGS);
        //WHEN
        final var notificationSettings = unit.createNotificationSettings();

        //THEN
        verify(mockNotificationSettingsRepository)
                .insertNotificationSettings(notificationSettingsArgumentCaptor.capture());

        assertThat(notificationSettings)
                .isNotNull()
                .isEqualTo(NOTIFICATION_SETTINGS);

        verifyNoMoreInteractions(mockNotificationSettingsRepository);
    }

    @Test
    void updateNotificationSettings_nominal() {
        //GIVEN
        final var notificationSettingsToUpdate = NOTIFICATION_SETTINGS
                .toBuilder()
                .notificationSettingsIntervals(List.of(1, 2, 3))
                .build();
        when(mockNotificationSettingsRepository.selectNotificationSettings(NOTIFICATION_SETTINGS_ID))
                .thenReturn(NOTIFICATION_SETTINGS);
        when(mockNotificationSettingsRepository.updateNotificationSettings(notificationSettingsToUpdate))
                .thenReturn(notificationSettingsToUpdate);
        doNothing().when(mockNotificationSettingEventProducer)
                   .sendUpdatedEvent(notificationSettingsToUpdate);

        //WHEN
        final var notificationSettings =
                unit.updateNotificationSettings(NOTIFICATION_SETTINGS_ID, notificationSettingsToUpdate);

        //THEN
        assertThat(notificationSettings)
                .isNotNull()
                .isEqualTo(notificationSettingsToUpdate);

        verify(mockNotificationSettingsRepository).selectNotificationSettings(NOTIFICATION_SETTINGS_ID);
        verify(mockNotificationSettingsRepository).updateNotificationSettings(notificationSettingsToUpdate);
        verify(mockNotificationSettingEventProducer).sendUpdatedEvent(notificationSettingsToUpdate);

        verifyNoMoreInteractions(mockNotificationSettingsRepository, mockNotificationSettingEventProducer);
    }

    @Test
    void createNotificationSettingsBuildCorrectNotificationSettingsEntity() {
        //GIVEN
        //WHEN
        final var notificationSettings = unit.createNotificationSettings();

        //THEN
        verify(mockNotificationSettingsRepository)
                .insertNotificationSettings(notificationSettingsArgumentCaptor.capture());
        final var capture = notificationSettingsArgumentCaptor.getValue();
        assertThat(capture)
                .isNotNull();
        assertThat(capture.getNotificationSettingsId())
                .isNotNull();
        assertThat(capture.getNotificationSettingsIntervals())
                .isNotNull()
                .isEqualTo(List.of(1));
        assertThat(capture.isEnabled())
                .isTrue();
    }

    @Test
    void getNotificationSettings_nominal() {
        //GIVEN
        when(mockNotificationSettingsRepository.selectNotificationSettings(NOTIFICATION_SETTINGS_ID))
                .thenReturn(NOTIFICATION_SETTINGS);

        //WHEN
        final var notificationSettings = unit.getNotificationSettings(NOTIFICATION_SETTINGS_ID);

        //THEN
        assertThat(notificationSettings)
                .isNotNull()
                .isEqualTo(NOTIFICATION_SETTINGS);

        verify(mockNotificationSettingsRepository).selectNotificationSettings(NOTIFICATION_SETTINGS_ID);

        verifyNoMoreInteractions(mockNotificationSettingsRepository);
    }

    @Test
    void getListNotificationSettingsByFilter_nominal_with_user_id() {
        //GIVEN
        when(mockNotificationSettingsRepository.selectNotificationSettingsListByUserId(USER_ID))
                .thenReturn(List.of(NOTIFICATION_SETTINGS));

        //WHEN
        final var notificationSettings = unit.getListNotificationSettingsByFilter(USER_ID);

        //THEN
        assertThat(notificationSettings)
                .isNotNull()
                .hasSize(1)
                .isEqualTo(List.of(NOTIFICATION_SETTINGS));

        verify(mockNotificationSettingsRepository).selectNotificationSettingsListByUserId(USER_ID);
        verify(mockNotificationSettingsRepository, never()).selectNotificationSettingsList();

        verifyNoMoreInteractions(mockNotificationSettingsRepository);
    }

    @Test
    void getListNotificationSettingsByFilter_nominal_without_user_id() {
        //GIVEN
        when(mockNotificationSettingsRepository.selectNotificationSettingsList())
                .thenReturn(List.of(NOTIFICATION_SETTINGS));

        //WHEN
        final var notificationSettings = unit.getListNotificationSettingsByFilter(null);

        //THEN
        assertThat(notificationSettings)
                .isNotNull()
                .hasSize(1)
                .isEqualTo(List.of(NOTIFICATION_SETTINGS));

        verify(mockNotificationSettingsRepository).selectNotificationSettingsList();
        verify(mockNotificationSettingsRepository, never()).selectNotificationSettingsListByUserId(USER_ID);

        verifyNoMoreInteractions(mockNotificationSettingsRepository);
    }

    @Test
    void getNotificationSettingsBySubscriptionId_nominal() {
        //GIVEN
        when(mockNotificationSettingsRepository.selectNotificationSettingsBySubscriptionId(SUBSCRIPTION_ID))
                .thenReturn(NOTIFICATION_SETTINGS);

        //WHEN
        final var notificationSettings = unit.getNotificationSettingsBySubscriptionId(SUBSCRIPTION_ID);

        //THEN
        assertThat(notificationSettings)
                .isNotNull()
                .isEqualTo(NOTIFICATION_SETTINGS);

        verify(mockNotificationSettingsRepository).selectNotificationSettingsBySubscriptionId(SUBSCRIPTION_ID);

        verifyNoMoreInteractions(mockNotificationSettingsRepository);
    }

    @Test
    void deleteNotificationSettings_nominal() {
        //GIVEN
        when(mockNotificationSettingsRepository.notificationSettingsExistById(NOTIFICATION_SETTINGS_ID))
                .thenReturn(true);
        when(mockNotificationSettingsRepository.deleteNotificationSettings(NOTIFICATION_SETTINGS_ID))
                .thenReturn(NOTIFICATION_SETTINGS);

        //WHEN
        final var notificationSettings = unit.deleteNotificationSettings(NOTIFICATION_SETTINGS_ID);

        //THEN
        assertThat(notificationSettings)
                .isNotNull()
                .isEqualTo(NOTIFICATION_SETTINGS);

        verify(mockNotificationSettingsRepository).notificationSettingsExistById(NOTIFICATION_SETTINGS_ID);
        verify(mockNotificationSettingsRepository).deleteNotificationSettings(NOTIFICATION_SETTINGS_ID);

        verifyNoMoreInteractions(mockNotificationSettingsRepository);
    }

    @Test
    void deleteNotificationSettings_nominal_notification_settings_does_not_exist() {
        //GIVEN
        final var incorrectNotificationSettingsId = UUID.randomUUID();
        when(mockNotificationSettingsRepository.notificationSettingsExistById(incorrectNotificationSettingsId))
                .thenReturn(false);

        //WHEN /THEN
        assertThatThrownBy(() -> unit.deleteNotificationSettings(incorrectNotificationSettingsId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("NotificationSettings [ID= %s] doesn't exist in system", incorrectNotificationSettingsId);

        verify(mockNotificationSettingsRepository).notificationSettingsExistById(incorrectNotificationSettingsId);
        verify(mockNotificationSettingsRepository, never()).deleteNotificationSettings(any());

        verifyNoMoreInteractions(mockNotificationSettingsRepository);
    }
}