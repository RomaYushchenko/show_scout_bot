package com.ua.yushchenko.dal.repository;

import static com.ua.yushchenko.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.ua.yushchenko.dal.dao.NotificationSettingsDao;
import com.ua.yushchenko.model.mapper.NotificationSettingsMapper;
import com.ua.yushchenko.model.persistence.NotificationSettingsDb;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class NotificationSettingsRepositoryTest {
    @Mock
    private NotificationSettingsDao mockNotificationSettingsDao;
    @Mock
    private NotificationSettingsMapper mockNotificationSettingsMapper;
    @InjectMocks
    private NotificationSettingsRepository unit;

    @Test
    void insertNotificationSettings_nominal() {
        //GIVEN
        when(mockNotificationSettingsMapper.toNotificationSettingsDb(NOTIFICATION_SETTINGS)).thenReturn(NOTIFICATION_SETTINGS_DB);
        when(mockNotificationSettingsMapper.toNotificationSettings(NOTIFICATION_SETTINGS_DB)).thenReturn(NOTIFICATION_SETTINGS);
        when(mockNotificationSettingsDao.save(NOTIFICATION_SETTINGS_DB)).thenReturn(NOTIFICATION_SETTINGS_DB);

        //WHEN
        final var notificationSettings = unit.insertNotificationSettings(NOTIFICATION_SETTINGS);

        //THEN
        assertThat(notificationSettings).isNotNull()
                .isEqualTo(NOTIFICATION_SETTINGS);

        verify(mockNotificationSettingsMapper).toNotificationSettingsDb(NOTIFICATION_SETTINGS);
        verify(mockNotificationSettingsMapper).toNotificationSettings(NOTIFICATION_SETTINGS_DB);
        verify(mockNotificationSettingsDao).save(NOTIFICATION_SETTINGS_DB);

        verifyNoMoreInteractions(mockNotificationSettingsDao, mockNotificationSettingsMapper);
    }

    @Test
    void updateNotificationSettings_nominal() {
        //GIVEN
        when(mockNotificationSettingsMapper.toNotificationSettingsDb(NOTIFICATION_SETTINGS)).thenReturn(NOTIFICATION_SETTINGS_DB);
        when(mockNotificationSettingsMapper.toNotificationSettings(NOTIFICATION_SETTINGS_DB)).thenReturn(NOTIFICATION_SETTINGS);
        when(mockNotificationSettingsDao.save(NOTIFICATION_SETTINGS_DB)).thenReturn(NOTIFICATION_SETTINGS_DB);

        //WHEN
        final var notificationSettings = unit.updateNotificationSettings(NOTIFICATION_SETTINGS);

        //THEN
        assertThat(notificationSettings).isNotNull()
                .isEqualTo(NOTIFICATION_SETTINGS);

        verify(mockNotificationSettingsMapper).toNotificationSettingsDb(NOTIFICATION_SETTINGS);
        verify(mockNotificationSettingsMapper).toNotificationSettings(NOTIFICATION_SETTINGS_DB);
        verify(mockNotificationSettingsDao).save(NOTIFICATION_SETTINGS_DB);

        verifyNoMoreInteractions(mockNotificationSettingsDao, mockNotificationSettingsMapper);
    }

    @Test
    void selectNotificationSettings_nominal() {
        //GIVEN
        when(mockNotificationSettingsDao.findById(NOTIFICATION_SETTINGS_ID))
                .thenReturn(Optional.of(NOTIFICATION_SETTINGS_DB));
        when(mockNotificationSettingsMapper.toNotificationSettings(NOTIFICATION_SETTINGS_DB))
                .thenReturn(NOTIFICATION_SETTINGS);

        //WHEN
        final var notificationSettings = unit.selectNotificationSettings(NOTIFICATION_SETTINGS_ID);

        //THEN
        assertThat(notificationSettings)
                .isNotNull()
                .isEqualTo(NOTIFICATION_SETTINGS);

        verify(mockNotificationSettingsDao).findById(NOTIFICATION_SETTINGS_ID);
        verify(mockNotificationSettingsMapper).toNotificationSettings(NOTIFICATION_SETTINGS_DB);

        verifyNoMoreInteractions(mockNotificationSettingsDao, mockNotificationSettingsMapper);
    }

    @Test
    void selectNotificationSettingsWorkCorrectlyWhenGivenIdDoesNotExist() {
        //GIVEN
        final var uuidDoesNotExist = UUID.randomUUID();
        when(mockNotificationSettingsDao.findById(uuidDoesNotExist)).thenReturn(Optional.empty());

        //WHEN
        final var notificationSettings = unit.selectNotificationSettings(uuidDoesNotExist);

        //THEN
        assertThat(notificationSettings)
                .isNull();

        verify(mockNotificationSettingsDao).findById(uuidDoesNotExist);
        verify(mockNotificationSettingsMapper, never()).toNotificationSettings(any(NotificationSettingsDb.class));

        verifyNoMoreInteractions(mockNotificationSettingsDao);
    }

    @Test
    void selectNotificationSettingsListByUserId_nominal() {
        //GIVEN
        when(mockNotificationSettingsDao.findAllNotificationSettingsByUserId(USER_ID))
                .thenReturn(List.of(NOTIFICATION_SETTINGS_DB));
        when(mockNotificationSettingsMapper.toNotificationSettings(List.of(NOTIFICATION_SETTINGS_DB)))
                .thenReturn(List.of(NOTIFICATION_SETTINGS));

        //WHEN
        final var notificationSettings = unit.selectNotificationSettingsListByUserId(USER_ID);

        //THEN
        assertThat(notificationSettings)
                .isNotNull()
                .hasSize(1)
                .isEqualTo(List.of(NOTIFICATION_SETTINGS));

        verify(mockNotificationSettingsDao).findAllNotificationSettingsByUserId(USER_ID);
        verify(mockNotificationSettingsMapper).toNotificationSettings(List.of(NOTIFICATION_SETTINGS_DB));

        verifyNoMoreInteractions(mockNotificationSettingsDao, mockNotificationSettingsMapper);
    }

    @Test
    void selectNotificationSettingsListByUserIdWorkCorrectlyWhenUserIdDoesNotExist() {
        //GIVEN
        final var userIdDoesNotExist = 1L;
        when(mockNotificationSettingsDao.findAllNotificationSettingsByUserId(userIdDoesNotExist))
                .thenReturn(List.of());
        when(mockNotificationSettingsMapper.toNotificationSettings(List.of())).thenReturn(List.of());

        //WHEN
        final var notificationSettings = unit.selectNotificationSettingsListByUserId(userIdDoesNotExist);

        //THEN
        assertThat(notificationSettings)
                .isNotNull()
                .isEqualTo(List.of());

        verify(mockNotificationSettingsDao).findAllNotificationSettingsByUserId(userIdDoesNotExist);

        verifyNoMoreInteractions(mockNotificationSettingsDao, mockNotificationSettingsMapper);
    }

    @Test
    void selectNotificationSettingsList_nominal() {
        //GIVEN
        when(mockNotificationSettingsDao.findAll())
                .thenReturn(List.of(NOTIFICATION_SETTINGS_DB));
        when(mockNotificationSettingsMapper.toNotificationSettings(List.of(NOTIFICATION_SETTINGS_DB)))
                .thenReturn(List.of(NOTIFICATION_SETTINGS));

        //WHEN
        final var notificationSettings = unit.selectNotificationSettingsList();

        //THEN
        assertThat(notificationSettings)
                .isNotNull()
                .hasSize(1)
                .isEqualTo(List.of(NOTIFICATION_SETTINGS));

        verify(mockNotificationSettingsDao).findAll();
        verify(mockNotificationSettingsMapper).toNotificationSettings(List.of(NOTIFICATION_SETTINGS_DB));

        verifyNoMoreInteractions(mockNotificationSettingsDao, mockNotificationSettingsMapper);
    }

    @Test
    void selectNotificationSettingsBySubscriptionId_nominal() {
        //GIVEN
        when(mockNotificationSettingsDao.findNotificationSettingsBySubscriptionId(SUBSCRIPTION_ID))
                .thenReturn(Optional.of(NOTIFICATION_SETTINGS_DB));
        when(mockNotificationSettingsMapper.toNotificationSettings(NOTIFICATION_SETTINGS_DB))
                .thenReturn(NOTIFICATION_SETTINGS);

        //WHEN
        final var notificationSettings = unit.selectNotificationSettingsBySubscriptionId(SUBSCRIPTION_ID);

        //THEN
        assertThat(notificationSettings)
                .isNotNull()
                .isEqualTo(NOTIFICATION_SETTINGS);

        verify(mockNotificationSettingsDao).findNotificationSettingsBySubscriptionId(SUBSCRIPTION_ID);
        verify(mockNotificationSettingsMapper).toNotificationSettings(NOTIFICATION_SETTINGS_DB);

        verifyNoMoreInteractions(mockNotificationSettingsDao, mockNotificationSettingsDao);
    }

    @Test
    void deleteNotificationSettings_nominal() {
        //GIVEN
        when(mockNotificationSettingsDao.findById(NOTIFICATION_SETTINGS_ID))
                .thenReturn(Optional.of(NOTIFICATION_SETTINGS_DB));
        when(mockNotificationSettingsMapper.toNotificationSettings(NOTIFICATION_SETTINGS_DB))
                .thenReturn(NOTIFICATION_SETTINGS);
        doNothing().when(mockNotificationSettingsDao).deleteById(NOTIFICATION_SETTINGS_ID);

        //WHEN
        unit.deleteNotificationSettings(NOTIFICATION_SETTINGS_ID);

        //THEN
        verify(mockNotificationSettingsDao).findById(NOTIFICATION_SETTINGS_ID);
        verify(mockNotificationSettingsMapper).toNotificationSettings(NOTIFICATION_SETTINGS_DB);
        verify(mockNotificationSettingsDao).deleteById(NOTIFICATION_SETTINGS_ID);

        verifyNoMoreInteractions(mockNotificationSettingsDao, mockNotificationSettingsMapper);
    }

    @Test
    void notificationSettingsExistById_nominal() {
        //GIVEN
        when(mockNotificationSettingsDao.existsById(NOTIFICATION_SETTINGS_ID)).thenReturn(true);

        //WHEN
        final var result = unit.notificationSettingsExistById(NOTIFICATION_SETTINGS_ID);

        //THEN
        assertThat(result)
                .isTrue();

        verify(mockNotificationSettingsDao).existsById(NOTIFICATION_SETTINGS_ID);

        verifyNoMoreInteractions(mockNotificationSettingsDao);
    }

    @Test
    void notificationSettingsExistById_nominal_notification_settings_id_does_not_exist() {
        //GIVEN
        final var notificationSettingsIdDoesNotExist = UUID.randomUUID();
        when(mockNotificationSettingsDao.existsById(notificationSettingsIdDoesNotExist)).thenReturn(false);

        //WHEN
        final var result = unit.notificationSettingsExistById(notificationSettingsIdDoesNotExist);

        //THEN
        assertThat(result)
                .isFalse();

        verify(mockNotificationSettingsDao).existsById(notificationSettingsIdDoesNotExist);

        verifyNoMoreInteractions(mockNotificationSettingsDao);
    }
}