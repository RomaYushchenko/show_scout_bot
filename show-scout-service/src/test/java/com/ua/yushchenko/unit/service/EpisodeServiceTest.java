package com.ua.yushchenko.unit.service;

import static com.ua.yushchenko.unit.TestData.EPISODE_ID;
import static com.ua.yushchenko.unit.TestData.EPISODE;
import static com.ua.yushchenko.unit.TestData.SHOW_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.ua.yushchenko.dal.repository.EpisodeRepository;
import com.ua.yushchenko.model.domain.Episode;
import com.ua.yushchenko.service.EpisodeService;
import com.ua.yushchenko.service.ShowService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EpisodeService}
 *
 * @author ivanshalaev
 * @version 0.1
 */
@ExtendWith(MockitoExtension.class)
class EpisodeServiceTest {

    @Mock
    private EpisodeRepository mockEpisodeRepository;

    @Mock
    private ShowService mockShowService;

    @Captor
    private ArgumentCaptor<Episode> episodeCaptor;

    @InjectMocks
    private EpisodeService unit;

    @Test
    void getEpisodeById_nominal() {
        //GIVEN
        when(mockEpisodeRepository.selectEpisode(EPISODE_ID)).thenReturn(EPISODE);

        //WHEN
        final var result = unit.getEpisodeById(EPISODE_ID);

        //THEN
        assertThat(result)
                .isNotNull()
                .isEqualTo(EPISODE);

        verify(mockEpisodeRepository).selectEpisode(EPISODE_ID);

        verifyNoMoreInteractions(mockEpisodeRepository);
    }

    @Test
    void getAllEpisodes_nominal() {
        //GIVEN
        when(mockEpisodeRepository.selectAllEpisodes()).thenReturn(List.of(EPISODE));

        //WHEN
        final var result = unit.getAllEpisodes();

        //THEN
        assertThat(result)
                .isNotEmpty()
                .hasSize(1)
                .first()
                .isEqualTo(EPISODE);

        verify(mockEpisodeRepository).selectAllEpisodes();

        verifyNoMoreInteractions(mockEpisodeRepository);
    }

    @Test
    void getEpisodesByShowId_nominal() {
        //GIVEN
        when(mockEpisodeRepository.selectEpisodesByShowId(SHOW_ID)).thenReturn(List.of(EPISODE));

        //WHEN
        final var result = unit.getEpisodesByShowId(SHOW_ID);

        //THEN
        assertThat(result)
                .isNotEmpty()
                .hasSize(1)
                .first()
                .isEqualTo(EPISODE);

        verify(mockEpisodeRepository).selectEpisodesByShowId(SHOW_ID);

        verifyNoMoreInteractions(mockEpisodeRepository);
    }

    @Test
    void getEpisodesByFilter_nominal_with_show_id() {
        //GIVEN
        when(mockEpisodeRepository.selectEpisodesByShowId(SHOW_ID)).thenReturn(List.of(EPISODE));

        //WHEN
        final var result = unit.getEpisodesByFilter(SHOW_ID);

        //THEN
        assertThat(result)
                .isNotEmpty()
                .hasSize(1)
                .first()
                .isEqualTo(EPISODE);

        verify(mockEpisodeRepository).selectEpisodesByShowId(SHOW_ID);
        verify(mockEpisodeRepository, never()).selectAllEpisodes();

        verifyNoMoreInteractions(mockEpisodeRepository);
    }


    @Test
    void getEpisodesByFilter_nominal_without_show_id() {
        //GIVEN
        when(mockEpisodeRepository.selectAllEpisodes()).thenReturn(List.of(EPISODE));

        //WHEN
        final var result = unit.getEpisodesByFilter(null);

        //THEN
        assertThat(result)
                .isNotEmpty()
                .hasSize(1)
                .first()
                .isEqualTo(EPISODE);

        verify(mockEpisodeRepository, never()).selectEpisodesByShowId(any());
        verify(mockEpisodeRepository).selectAllEpisodes();

        verifyNoMoreInteractions(mockEpisodeRepository);
    }

    @Test
    void createEpisode_nominal() {
        //GIVEN
        final var episodeToBuild = EPISODE.toBuilder()
                .episodeID(null)
                .showId(null)
                .build();

        final var episodeBuilt = EPISODE.toBuilder()
                .episodeID(null)
                .showId(SHOW_ID)
                .build();

        when(mockShowService.showExistById(SHOW_ID)).thenReturn(true);
        when(mockEpisodeRepository.insertEpisode(episodeBuilt)).thenReturn(EPISODE);

        //WHEN
        final var result = unit.createEpisode(episodeToBuild, SHOW_ID);

        //THEN
        assertThat(result)
                .isNotNull()
                .isEqualTo(EPISODE);

        verify(mockShowService).showExistById(SHOW_ID);
        verify(mockEpisodeRepository).insertEpisode(episodeBuilt);

        verifyNoMoreInteractions(mockShowService, mockEpisodeRepository);
    }

    @Test
    void createEpisode_nominal_set_show_id() {
        //GIVE
        final var episodeToBuild = EPISODE.toBuilder()
                .episodeID(null)
                .showId(null)
                .build();

        final var expectedEpisodeToSave = EPISODE.toBuilder()
                .episodeID(null)
                .showId(SHOW_ID)
                .build();

        when(mockShowService.showExistById(SHOW_ID)).thenReturn(true);
        when(mockEpisodeRepository.insertEpisode(any())).thenReturn(EPISODE);

        //WHEN
        final var createdEpisode = unit.createEpisode(episodeToBuild, SHOW_ID);

        //THEN
        verify(mockShowService).showExistById(SHOW_ID);
        verify(mockEpisodeRepository).insertEpisode(episodeCaptor.capture());

        final var capturedEpisode = episodeCaptor.getValue();
        assertEquals(SHOW_ID, capturedEpisode.getShowId());

        assertEquals(expectedEpisodeToSave, capturedEpisode);

        assertEquals(EPISODE, createdEpisode);

        verifyNoMoreInteractions(mockShowService, mockEpisodeRepository);
    }

    @Test
    void createEpisode_nominal_show_id_does_exist_in_system() {
        //GIVEN
        when(mockShowService.showExistById(SHOW_ID)).thenReturn(false);

        //WHEN //THEN
        assertThatThrownBy(() -> unit.createEpisode(EPISODE, SHOW_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(String.format("Show [ID=%s] doesn't exist in system", SHOW_ID));

        verify(mockEpisodeRepository, never()).insertEpisode(any());

        verifyNoMoreInteractions(mockShowService);
    }

    @Test
    void updateEpisode_nominal() {
        //GIVEN
        final var episodeToUpdate = EPISODE.toBuilder()
                .episodeName("Test Name two")
                .build();
        when(mockEpisodeRepository.selectEpisode(EPISODE_ID)).thenReturn(EPISODE);
        when(mockEpisodeRepository.updateEpisode(episodeToUpdate)).thenReturn(episodeToUpdate);

        //WHEN
        final var result = unit.updateEpisode(EPISODE_ID, episodeToUpdate);

        //THEN
        assertThat(result)
                .isNotNull()
                .isEqualTo(episodeToUpdate);

        verify(mockEpisodeRepository).selectEpisode(EPISODE_ID);
        verify(mockEpisodeRepository).updateEpisode(episodeToUpdate);

        verifyNoMoreInteractions(mockEpisodeRepository);
    }

    @Test
    void updateEpisode_nominal_episode_id_does_exist_in_system() {
        //GIVEN
        when(mockEpisodeRepository.selectEpisode(EPISODE_ID)).thenReturn(null);

        //WHEN
        final var result = unit.updateEpisode(EPISODE_ID, EPISODE);

        //THEN
        assertThat(result)
                .isNull();

        verify(mockEpisodeRepository).selectEpisode(EPISODE_ID);
        verify(mockEpisodeRepository, never()).updateEpisode(any());

        verifyNoMoreInteractions(mockEpisodeRepository);
    }

    @Test
    void updateEpisode_nominal_episode_to_update_has_nothing_to_update() {
        //GIVEN
        when(mockEpisodeRepository.selectEpisode(EPISODE_ID)).thenReturn(EPISODE);

        //WHEN
        final var result = unit.updateEpisode(EPISODE_ID, EPISODE);

        //THEN
        assertThat(result)
                .isNotNull()
                .isEqualTo(EPISODE);

        verify(mockEpisodeRepository).selectEpisode(EPISODE_ID);
        verify(mockEpisodeRepository, never()).updateEpisode(any());

        verifyNoMoreInteractions(mockEpisodeRepository);
    }

    @Test
    void deleteEpisodeById_nominal() {
        //GIVEN
        when(mockEpisodeRepository.episodeExistById(EPISODE_ID)).thenReturn(true);
        when(mockEpisodeRepository.deleteEpisodeById(EPISODE_ID)).thenReturn(EPISODE);

        //WHEN
        final var result = unit.deleteEpisodeById(EPISODE_ID);

        //THEN
        assertThat(result)
                .isNotNull()
                .isEqualTo(EPISODE);

        verify(mockEpisodeRepository).episodeExistById(EPISODE_ID);
        verify(mockEpisodeRepository).deleteEpisodeById(EPISODE_ID);

        verifyNoMoreInteractions(mockEpisodeRepository);
    }

    @Test
    void deleteEpisodeById_nominal_episode_id_dose_not_exist() {
        //GIVEN
        when(mockEpisodeRepository.episodeExistById(EPISODE_ID)).thenReturn(false);

        //WHEN
        final var result = unit.deleteEpisodeById(EPISODE_ID);

        //THEN
        assertThat(result)
                .isNull();

        verify(mockEpisodeRepository).episodeExistById(EPISODE_ID);
        verify(mockEpisodeRepository, never()).deleteEpisodeById(any());

        verifyNoMoreInteractions(mockEpisodeRepository);
    }

    @Test
    void deleteEpisodesByShowId_nominal() {
        //GIVEN
        when(mockShowService.showExistById(SHOW_ID)).thenReturn(true);
        when(mockEpisodeRepository.deleteEpisodesByShowId(SHOW_ID)).thenReturn(List.of(EPISODE));

        //WHEN
        final var result = unit.deleteEpisodesByShowId(SHOW_ID);

        //THEN
        assertThat(result)
                .isNotEmpty()
                .hasSize(1)
                .first()
                .isEqualTo(EPISODE);

        verify(mockShowService).showExistById(SHOW_ID);
        verify(mockEpisodeRepository).deleteEpisodesByShowId(SHOW_ID);

        verifyNoMoreInteractions(mockShowService, mockEpisodeRepository);
    }

    @Test
    void deleteEpisodesByShowId_nominal_show_id_dose_not_exist() {
        //GIVEN
        when(mockShowService.showExistById(SHOW_ID)).thenReturn(false);

        //WHEN
        final var result = unit.deleteEpisodesByShowId(SHOW_ID);

        //THEN
        assertThat(result)
                .isNull();

        verify(mockShowService).showExistById(SHOW_ID);
        verify(mockEpisodeRepository, never()).deleteEpisodesByShowId(any());

        verifyNoMoreInteractions(mockShowService);
    }
}