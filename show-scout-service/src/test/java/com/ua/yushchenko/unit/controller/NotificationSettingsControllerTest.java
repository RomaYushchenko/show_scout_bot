package com.ua.yushchenko.unit.controller;

import static com.ua.yushchenko.unit.TestData.NOTIFICATION_SETTINGS;
import static com.ua.yushchenko.unit.TestData.NOTIFICATION_SETTINGS_API;
import static com.ua.yushchenko.unit.TestData.NOTIFICATION_SETTINGS_ID;
import static com.ua.yushchenko.unit.TestData.SUBSCRIPTION_ID;
import static com.ua.yushchenko.unit.TestData.TELEGRAM_USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import com.ua.yushchenko.api.NotificationSettingsApi;
import com.ua.yushchenko.controller.NotificationSettingsController;
import com.ua.yushchenko.model.domain.NotificationSettings;
import com.ua.yushchenko.model.mapper.NotificationSettingsMapper;
import com.ua.yushchenko.service.NotificationSettingsService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class NotificationSettingsControllerTest {

    @Mock
    private NotificationSettingsService mockNotificationSettingsService;
    @Mock
    private NotificationSettingsMapper mockNotificationSettingsMapper;
    @InjectMocks
    private NotificationSettingsController unit;

    @Test
    void getNotificationSettings_nominal() {
        //GIVEN
        when(mockNotificationSettingsService.getNotificationSettings(NOTIFICATION_SETTINGS_ID))
                .thenReturn(NOTIFICATION_SETTINGS);
        when(mockNotificationSettingsMapper.toNotificationSettingsApi(NOTIFICATION_SETTINGS))
                .thenReturn(NOTIFICATION_SETTINGS_API);

        //WHEN
        final var notificationSettings = unit.getNotificationSettings(NOTIFICATION_SETTINGS_ID);

        //THEN
        assertThat(notificationSettings)
                .isNotNull()
                .isEqualTo(NOTIFICATION_SETTINGS_API);

        verify(mockNotificationSettingsService).getNotificationSettings(NOTIFICATION_SETTINGS_ID);
        verify(mockNotificationSettingsMapper).toNotificationSettingsApi(NOTIFICATION_SETTINGS);

        verifyNoMoreInteractions(mockNotificationSettingsService, mockNotificationSettingsMapper);
    }

    @Test
    void getNotificationSettingsWorksCorrectlyWhenGivenIdDoesNotExist() {
        //GIVEN
        final var uuid_does_not_exist = UUID.randomUUID();
        when(mockNotificationSettingsService.getNotificationSettings(uuid_does_not_exist))
                .thenReturn(null);
        //WHEN /THEN
        assertThatThrownBy(() -> unit.getNotificationSettings(uuid_does_not_exist))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("NotificationSettings by " + uuid_does_not_exist + " doesn't exist in system");

        verify(mockNotificationSettingsService).getNotificationSettings(uuid_does_not_exist);
        verify(mockNotificationSettingsMapper, never()).toNotificationSettingsApi(any(NotificationSettings.class));

        verifyNoMoreInteractions(mockNotificationSettingsService);
    }

    @Test
    void getNotificationSettingsList_nominal_with_user_id() {
        //GIVEN
        when(mockNotificationSettingsService.getListNotificationSettingsByFilter(TELEGRAM_USER_ID))
                .thenReturn(List.of(NOTIFICATION_SETTINGS));
        when(mockNotificationSettingsMapper.toNotificationSettingsApis(List.of(NOTIFICATION_SETTINGS)))
                .thenReturn(List.of(NOTIFICATION_SETTINGS_API));

        //WHEN
        final var notificationSettings = unit.getNotificationSettingsList(TELEGRAM_USER_ID);

        //THEN
        assertThat(notificationSettings)
                .isNotNull()
                .hasSize(1)
                .isEqualTo(List.of(NOTIFICATION_SETTINGS_API));

        verify(mockNotificationSettingsService).getListNotificationSettingsByFilter(TELEGRAM_USER_ID);
        verify(mockNotificationSettingsMapper).toNotificationSettingsApis(List.of(NOTIFICATION_SETTINGS));

        verifyNoMoreInteractions(mockNotificationSettingsService, mockNotificationSettingsMapper);
    }

    @Test
    void getNotificationSettingsList_nominal_without_user_id() {
        //GIVEN
        when(mockNotificationSettingsService.getListNotificationSettingsByFilter(null))
                .thenReturn(List.of(NOTIFICATION_SETTINGS));
        when(mockNotificationSettingsMapper.toNotificationSettingsApis(List.of(NOTIFICATION_SETTINGS)))
                .thenReturn(List.of(NOTIFICATION_SETTINGS_API));

        //WHEN
        final var notificationSettingsList = unit.getNotificationSettingsList(null);

        //THEN
        assertThat(notificationSettingsList)
                .isNotNull()
                .hasSize(1)
                .isEqualTo(List.of(NOTIFICATION_SETTINGS_API));

        verify(mockNotificationSettingsService).getListNotificationSettingsByFilter(null);
        verify(mockNotificationSettingsMapper).toNotificationSettingsApis(List.of(NOTIFICATION_SETTINGS));

        verifyNoMoreInteractions(mockNotificationSettingsService, mockNotificationSettingsMapper);
    }

    @Test
    void getNotificationSettingsBySubscriptionId_nominal() {
        //GIVEN
        when(mockNotificationSettingsMapper.toNotificationSettingsApi(NOTIFICATION_SETTINGS))
                .thenReturn(NOTIFICATION_SETTINGS_API);
        when(mockNotificationSettingsService.getNotificationSettingsBySubscriptionId(SUBSCRIPTION_ID))
                .thenReturn(NOTIFICATION_SETTINGS);

        //WHEN
        final var notificationSettingsBySubscriptionId = unit.getNotificationSettingsBySubscriptionId(SUBSCRIPTION_ID);

        //THEN
        assertThat(notificationSettingsBySubscriptionId)
                .isNotNull()
                .isEqualTo(NOTIFICATION_SETTINGS_API);

        verify(mockNotificationSettingsMapper).toNotificationSettingsApi(NOTIFICATION_SETTINGS);
        verify(mockNotificationSettingsService).getNotificationSettingsBySubscriptionId(SUBSCRIPTION_ID);

        verifyNoMoreInteractions(mockNotificationSettingsService, mockNotificationSettingsMapper);
    }

    @Test
    void getNotificationSettingsBySubscriptionId_nominal_when_subscription_id_does_not_exist() {
        //GIVEN
        final var incorrectSubscriptionId = UUID.randomUUID();
        when(mockNotificationSettingsService.getNotificationSettingsBySubscriptionId(incorrectSubscriptionId))
                .thenReturn(null);

        //WHEN /THEN
        assertThatThrownBy(() -> unit.getNotificationSettingsBySubscriptionId(incorrectSubscriptionId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("NotificationSettings by subscriptionId " + incorrectSubscriptionId +
                                    " doesn't exist in system");

        verify(mockNotificationSettingsService).getNotificationSettingsBySubscriptionId(incorrectSubscriptionId);
        verify(mockNotificationSettingsMapper, never()).toNotificationSettingsApi(any());

        verifyNoMoreInteractions(mockNotificationSettingsService);
    }

    @Test
    void updateNotificationSettings_nominal() {
        //GIVEN
        when(mockNotificationSettingsMapper.toNotificationSettings(NOTIFICATION_SETTINGS_API))
                .thenReturn(NOTIFICATION_SETTINGS);
        when(mockNotificationSettingsService.updateNotificationSettings(NOTIFICATION_SETTINGS_ID,
                                                                        NOTIFICATION_SETTINGS))
                .thenReturn(NOTIFICATION_SETTINGS);
        when(mockNotificationSettingsMapper.toNotificationSettingsApi(NOTIFICATION_SETTINGS))
                .thenReturn(NOTIFICATION_SETTINGS_API);

        //WHEN
        final var notificationSettingsApi =
                unit.updateNotificationSettings(NOTIFICATION_SETTINGS_ID, NOTIFICATION_SETTINGS_API);

        //THEN
        assertThat(notificationSettingsApi)
                .isNotNull()
                .isEqualTo(NOTIFICATION_SETTINGS_API);

        verify(mockNotificationSettingsMapper).toNotificationSettings(NOTIFICATION_SETTINGS_API);
        verify(mockNotificationSettingsService).updateNotificationSettings(NOTIFICATION_SETTINGS_ID,
                                                                           NOTIFICATION_SETTINGS);
        verify(mockNotificationSettingsMapper).toNotificationSettingsApi(NOTIFICATION_SETTINGS);

        verifyNoMoreInteractions(mockNotificationSettingsService, mockNotificationSettingsMapper);
    }

    @Test
    void updateNotificationSettings_nominal_with_incorrect_notification_settings_id() {
        //GIVEN
        final var incorrectNotificationSettingsId = UUID.randomUUID();
        final var notificationSettings = NOTIFICATION_SETTINGS.toBuilder()
                                                              .notificationSettingsId(incorrectNotificationSettingsId)
                                                              .build();
        final var notificationSettingsApi = NOTIFICATION_SETTINGS_API.toBuilder()
                                                                     .notificationSettingsId(
                                                                             incorrectNotificationSettingsId)
                                                                     .build();
        when(mockNotificationSettingsMapper.toNotificationSettings(notificationSettingsApi))
                .thenReturn(notificationSettings);
        when(mockNotificationSettingsService.updateNotificationSettings(incorrectNotificationSettingsId,
                                                                        notificationSettings))
                .thenReturn(null);

        //WHEN /THEN
        assertThatThrownBy(
                () -> unit.updateNotificationSettings(incorrectNotificationSettingsId, notificationSettingsApi))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("NotificationSettings with notificationSettingsId: " + incorrectNotificationSettingsId +
                                    " doesn't exist in system");

        verify(mockNotificationSettingsMapper).toNotificationSettings(notificationSettingsApi);
        verify(mockNotificationSettingsService).updateNotificationSettings(incorrectNotificationSettingsId,
                                                                           notificationSettings);
        verify(mockNotificationSettingsMapper, never()).toNotificationSettingsApi(any());

        verifyNoMoreInteractions(mockNotificationSettingsMapper, mockNotificationSettingsService);
    }

    @Test
    void updateNotificationSettings_nominal_with_notification_settings_id_as_parameter_does_not_match_with_notification_settings_id_to_update() {
        //GIVEN
        final var incorrectNotificationSettingsId = UUID.randomUUID();

        //WHEN /THEN
        assertThatThrownBy(
                () -> unit.updateNotificationSettings(incorrectNotificationSettingsId, NOTIFICATION_SETTINGS_API))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("PathVariable notificationSettingsId: " + incorrectNotificationSettingsId
                                    +
                                    " does not match with RequestBody notificationSettingsApi" +
                                    ".getNotificationSettingsId(): "
                                    + NOTIFICATION_SETTINGS_API.getNotificationSettingsId());

        verify(mockNotificationSettingsMapper, never()).toNotificationSettings(any(NotificationSettingsApi.class));
        verify(mockNotificationSettingsService, never()).updateNotificationSettings(any(), any());
        verify(mockNotificationSettingsMapper, never()).toNotificationSettingsApi(any());

        verifyNoInteractions(mockNotificationSettingsMapper, mockNotificationSettingsService);
    }
}