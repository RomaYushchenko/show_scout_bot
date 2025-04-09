package com.ua.yushchenko.unit.controller;

import static com.ua.yushchenko.unit.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ua.yushchenko.api.EpisodeApi;
import com.ua.yushchenko.controller.EpisodeController;
import com.ua.yushchenko.model.mapper.EpisodeMapper;
import com.ua.yushchenko.service.EpisodeService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link EpisodeController}
 *
 * @author ivanshalaev
 * @version 0.1
 */
@Tag("unit")
@ExtendWith(MockitoExtension.class)
class EpisodeControllerTest {

    @Mock
    private EpisodeService mockEpisodeService;

    @Mock
    private EpisodeMapper mockEpisodeMapper;

    @InjectMocks
    private EpisodeController unit;

    @Test
    void getEpisode_nominal() {
        //GIVEN
        when(mockEpisodeService.getEpisodeById(EPISODE_ID)).thenReturn(EPISODE);
        when(mockEpisodeMapper.toEpisodeApi(EPISODE)).thenReturn(EPISODE_API);

        //WHEN
        final var result = unit.getEpisode(EPISODE_ID);

        //THEN
        assertThat(result)
                .isNotNull()
                .isEqualTo(EPISODE_API);

        verify(mockEpisodeService).getEpisodeById(EPISODE_ID);
        verify(mockEpisodeMapper).toEpisodeApi(EPISODE);

        verifyNoMoreInteractions(mockEpisodeService, mockEpisodeMapper);
    }

    @Test
    void getEpisode_nominal_episode_id_dose_not_exist() {
        //GIVEN
        when(mockEpisodeService.getEpisodeById(EPISODE_ID)).thenReturn(null);

        //WHEN /THEN
        assertThatThrownBy(() -> unit.getEpisode(EPISODE_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Episode doesn't exist in system");

        verify(mockEpisodeService).getEpisodeById(EPISODE_ID);
        verify(mockEpisodeMapper, never()).toEpisodeApi(any());

        verifyNoMoreInteractions(mockEpisodeService);
    }

    @Test
    void getEpisodes_nominal_with_show_id() {
        //GIVEN
        when(mockEpisodeService.getEpisodesByFilter(SHOW_ID)).thenReturn(List.of(EPISODE));
        when(mockEpisodeMapper.toEpisodesApiFromEpisodes(List.of(EPISODE))).thenReturn(List.of(EPISODE_API));

        //WHEN
        final var result = unit.getEpisodes(SHOW_ID);

        //THEN
        assertThat(result)
                .isNotEmpty()
                .hasSize(1)
                .first()
                .isEqualTo(EPISODE_API);

        verify(mockEpisodeService).getEpisodesByFilter(SHOW_ID);
        verify(mockEpisodeMapper).toEpisodesApiFromEpisodes(List.of(EPISODE));

        verifyNoMoreInteractions(mockEpisodeService, mockEpisodeMapper);
    }

    @Test
    void getEpisodes_nominal_without_show_id() {
        //GIVEN
        when(mockEpisodeService.getEpisodesByFilter(null)).thenReturn(List.of(EPISODE));
        when(mockEpisodeMapper.toEpisodesApiFromEpisodes(List.of(EPISODE))).thenReturn(List.of(EPISODE_API));

        //WHEN
        final var result = unit.getEpisodes(null);

        //THEN
        assertThat(result)
                .isNotEmpty()
                .hasSize(1)
                .first()
                .isEqualTo(EPISODE_API);

        verify(mockEpisodeService).getEpisodesByFilter(null);
        verify(mockEpisodeMapper).toEpisodesApiFromEpisodes(List.of(EPISODE));

        verifyNoMoreInteractions(mockEpisodeService, mockEpisodeMapper);
    }

    @Test
    void createEpisode_nominal() {
        //GIVEN
        when(mockEpisodeMapper.toEpisode(EPISODE_API)).thenReturn(EPISODE);
        when(mockEpisodeService.createEpisode(EPISODE, SHOW_ID)).thenReturn(EPISODE);
        when(mockEpisodeMapper.toEpisodeApi(EPISODE)).thenReturn(EPISODE_API);

        //WHEN
        final var result = unit.createEpisode(EPISODE_API, SHOW_ID);

        //THEN
        assertThat(result)
                .isNotNull()
                .isEqualTo(EPISODE_API);

        verify(mockEpisodeMapper).toEpisode(EPISODE_API);
        verify(mockEpisodeService).createEpisode(EPISODE, SHOW_ID);
        verify(mockEpisodeMapper).toEpisodeApi(EPISODE);

        verifyNoMoreInteractions(mockEpisodeMapper, mockEpisodeService);
    }

    @Test
    void updateEpisode_nominal() {
        //GIVEN
        when(mockEpisodeMapper.toEpisode(EPISODE_API)).thenReturn(EPISODE);
        when(mockEpisodeService.updateEpisode(EPISODE_ID, EPISODE)).thenReturn(EPISODE);
        when(mockEpisodeMapper.toEpisodeApi(EPISODE)).thenReturn(EPISODE_API);

        //WHEN
        final var result = unit.updateEpisode(EPISODE_ID, EPISODE_API);

        //THEN
        assertThat(result)
                .isNotNull()
                .isEqualTo(EPISODE_API);

        verify(mockEpisodeMapper).toEpisode(EPISODE_API);
        verify(mockEpisodeService).updateEpisode(EPISODE_ID, EPISODE);
        verify(mockEpisodeMapper).toEpisodeApi(EPISODE);

        verifyNoMoreInteractions(mockEpisodeMapper, mockEpisodeService);
    }

    @Test
    void updateEpisode_nominal_with_episode_id_as_parameter_does_not_match_with_episode_id_to_update() {
        //GIVEN
        final var incorrectEpisodeId = UUID.randomUUID();
        final var episodeToUpdate = EPISODE_API.toBuilder()
                .episodeID(incorrectEpisodeId)
                .build();

        //WHEN/THEN
        assertThatThrownBy(() -> unit.updateEpisode(EPISODE_ID, episodeToUpdate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format("PathVariable episodeId: %s does not match with RequestBody episodeApi.getEpisodeId: %s"
                        , EPISODE_ID, episodeToUpdate.getEpisodeID()));

        verify(mockEpisodeMapper, never()).toEpisode(any(EpisodeApi.class));
        verify(mockEpisodeService, never()).updateEpisode(any(), any());
        verify(mockEpisodeMapper, never()).toEpisodeApi(any());

        verifyNoInteractions(mockEpisodeMapper, mockEpisodeService);
    }

    @Test
    void updateEpisode_nominal_with_incorrect_episode_id() {
        //GIVEN
        when(mockEpisodeMapper.toEpisode(EPISODE_API)).thenReturn(EPISODE);
        when(mockEpisodeService.updateEpisode(EPISODE_ID, EPISODE)).thenReturn(null);

        //WHEN/THEN
        assertThatThrownBy(() -> unit.updateEpisode(EPISODE_ID, EPISODE_API))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(String.format("Episode with episodeId: %s doesn't exist in system"
                        , EPISODE_ID));

        verify(mockEpisodeMapper).toEpisode(EPISODE_API);
        verify(mockEpisodeService).updateEpisode(EPISODE_ID, EPISODE);
        verify(mockEpisodeMapper, never()).toEpisodeApi(any());

        verifyNoMoreInteractions(mockEpisodeMapper, mockEpisodeService);
    }

    @Test
    void deleteEpisode_nominal() {
        //GIVEN
        when(mockEpisodeService.deleteEpisodeById(EPISODE_ID)).thenReturn(EPISODE);
        when(mockEpisodeMapper.toEpisodeApi(EPISODE)).thenReturn(EPISODE_API);

        //WHEN
        final var result = unit.deleteEpisode(EPISODE_ID);

        //THEN
        assertThat(result)
                .isNotNull()
                .isEqualTo(EPISODE_API);

        verify(mockEpisodeService).deleteEpisodeById(EPISODE_ID);
        verify(mockEpisodeMapper).toEpisodeApi(EPISODE);

        verifyNoMoreInteractions(mockEpisodeService, mockEpisodeMapper);
    }

    @Test
    void deleteEpisode_nominal_episode_id_dose_not_exist() {
        //GIVEN
        when(mockEpisodeService.deleteEpisodeById(EPISODE_ID)).thenReturn(null);

        //WHEN/THEN
        assertThatThrownBy(() -> unit.deleteEpisode(EPISODE_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Episode doesn't exist in system");

        verify(mockEpisodeService).deleteEpisodeById(EPISODE_ID);
        verify(mockEpisodeMapper,never()).toEpisodeApi(any());

        verifyNoMoreInteractions(mockEpisodeService);
    }
}