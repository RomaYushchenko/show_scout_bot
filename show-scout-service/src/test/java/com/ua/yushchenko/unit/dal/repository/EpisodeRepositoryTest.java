package com.ua.yushchenko.unit.dal.repository;

import static com.ua.yushchenko.unit.TestData.*;

import com.ua.yushchenko.dal.dao.EpisodeDao;
import com.ua.yushchenko.dal.repository.EpisodeRepository;
import com.ua.yushchenko.model.mapper.EpisodeMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link EpisodeRepository}
 *
 * @author ivanshalaev
 * @version 0.1
 */
@Tag("unit")
@ExtendWith(MockitoExtension.class)
class EpisodeRepositoryTest {

    @Mock
    EpisodeDao mockEpisodeDao;

    @Mock
    EpisodeMapper mockEpisodeMapper;

    @InjectMocks
    EpisodeRepository unit;

    @Test
    void selectEpisode_nominal() {
        //GIVEN
        when(mockEpisodeDao.findById(EPISODE_ID)).thenReturn(Optional.of(EPISODE_DB));
        when(mockEpisodeMapper.toEpisode(EPISODE_DB)).thenReturn(EPISODE);

        //WHEN
        final var result = unit.selectEpisode(EPISODE_ID);

        //THEN
        assertThat(result)
                .isNotNull()
                .isEqualTo(EPISODE);

        verify(mockEpisodeDao).findById(EPISODE_ID);
        verify(mockEpisodeMapper).toEpisode(EPISODE_DB);

        verifyNoMoreInteractions(mockEpisodeDao, mockEpisodeMapper);
    }

    @Test
    void selectAllEpisodes_nominal() {
        //GIVEN
        when(mockEpisodeDao.findAll()).thenReturn(List.of(EPISODE_DB));
        when(mockEpisodeMapper.toEpisodesFromEpisodesDb(List.of(EPISODE_DB)))
                .thenReturn(List.of(EPISODE));

        //WHEN
        final var result = unit.selectAllEpisodes();

        //THEN
        assertThat(result)
                .isNotEmpty()
                .hasSize(1)
                .first()
                .isEqualTo(EPISODE);

        verify(mockEpisodeDao).findAll();
        verify(mockEpisodeMapper).toEpisodesFromEpisodesDb(List.of(EPISODE_DB));

        verifyNoMoreInteractions(mockEpisodeDao, mockEpisodeMapper);
    }

    @Test
    void selectEpisodesByShowId_nominal() {
        //GIVEN
        when(mockEpisodeDao.findAllByShowId(SHOW_ID)).thenReturn(List.of(EPISODE_DB));
        when(mockEpisodeMapper.toEpisodesFromEpisodesDb(List.of(EPISODE_DB)))
                .thenReturn(List.of(EPISODE));

        //WHEN
        final var result = unit.selectEpisodesByShowId(SHOW_ID);

        //THEN
        assertThat(result)
                .isNotEmpty()
                .hasSize(1)
                .first()
                .isEqualTo(EPISODE);

        verify(mockEpisodeDao).findAllByShowId(SHOW_ID);
        verify(mockEpisodeMapper).toEpisodesFromEpisodesDb(List.of(EPISODE_DB));

        verifyNoMoreInteractions(mockEpisodeDao, mockEpisodeMapper);
    }

    @Test
    void insertEpisode_nominal() {
        //GIVEN
        when(mockEpisodeMapper.toEpisodeDb(EPISODE)).thenReturn(EPISODE_DB);
        when(mockEpisodeDao.save(EPISODE_DB)).thenReturn(EPISODE_DB);
        when(mockEpisodeMapper.toEpisode(EPISODE_DB)).thenReturn(EPISODE);

        //WHEN
        final var result = unit.insertEpisode(EPISODE);

        //THEN
        assertThat(result)
                .isNotNull()
                .isEqualTo(EPISODE);

        verify(mockEpisodeMapper).toEpisodeDb(EPISODE);
        verify(mockEpisodeDao).save(EPISODE_DB);
        verify(mockEpisodeMapper).toEpisode(EPISODE_DB);

        verifyNoMoreInteractions(mockEpisodeMapper, mockEpisodeDao);
    }

    @Test
    void updateEpisode_nominal() {
        //GIVEN
        when(mockEpisodeMapper.toEpisodeDb(EPISODE)).thenReturn(EPISODE_DB);
        when(mockEpisodeDao.save(EPISODE_DB)).thenReturn(EPISODE_DB);
        when(mockEpisodeMapper.toEpisode(EPISODE_DB)).thenReturn(EPISODE);

        //WHEN
        final var result = unit.updateEpisode(EPISODE);

        //THEN
        assertThat(result)
                .isNotNull()
                .isEqualTo(EPISODE);

        verify(mockEpisodeMapper).toEpisodeDb(EPISODE);
        verify(mockEpisodeDao).save(EPISODE_DB);
        verify(mockEpisodeMapper).toEpisode(EPISODE_DB);

        verifyNoMoreInteractions(mockEpisodeMapper, mockEpisodeDao);
    }

    @Test
    void deleteEpisodeById_nominal() {
        //GIVEN
        when(mockEpisodeDao.findById(EPISODE_ID)).thenReturn(Optional.of(EPISODE_DB));
        when(mockEpisodeMapper.toEpisode(EPISODE_DB)).thenReturn(EPISODE);
        doNothing().when(mockEpisodeDao).deleteById(EPISODE_ID);

        //WHEN
        final var result = unit.deleteEpisodeById(EPISODE_ID);

        //THEN
        assertThat(result)
                .isNotNull()
                .isEqualTo(EPISODE);

        verify(mockEpisodeDao).findById(EPISODE_ID);
        verify(mockEpisodeMapper).toEpisode(EPISODE_DB);
        verify(mockEpisodeDao).deleteById(EPISODE_ID);
    }

    @Test
    void deleteEpisodesByShowId_nominal() {
        //GIVEN
        when(mockEpisodeDao.findAllByShowId(SHOW_ID)).thenReturn(List.of(EPISODE_DB));
        when(mockEpisodeMapper.toEpisodesFromEpisodesDb(List.of(EPISODE_DB)))
                .thenReturn(List.of(EPISODE));
        doNothing().when(mockEpisodeDao).deleteAllByShowId(SHOW_ID);

        //WHEN
        final var result = unit.deleteEpisodesByShowId(SHOW_ID);

        //THEN
        assertThat(result)
                .isNotEmpty()
                .hasSize(1)
                .first()
                .isEqualTo(EPISODE);

        verify(mockEpisodeDao).findAllByShowId(SHOW_ID);
        verify(mockEpisodeMapper).toEpisodesFromEpisodesDb(List.of(EPISODE_DB));
        verify(mockEpisodeDao).deleteAllByShowId(SHOW_ID);

        verifyNoMoreInteractions(mockEpisodeDao, mockEpisodeMapper);
    }

    @Test
    void episodeExistById_nominal() {
        //GIVEN
        when(mockEpisodeDao.existsById(EPISODE_ID)).thenReturn(true);

        //WHEN
        final var result = unit.episodeExistById(EPISODE_ID);

        //THEN
        assertThat(result)
                .isTrue();

        verify(mockEpisodeDao).existsById(EPISODE_ID);

        verifyNoMoreInteractions(mockEpisodeDao);
    }

    @Test
    void episodeExistById_nominal_episode_id_does_not_exist() {
        //GIVEN
        when(mockEpisodeDao.existsById(EPISODE_ID)).thenReturn(false);

        //WHEN
        final var result = unit.episodeExistById(EPISODE_ID);

        //THEN
        assertThat(result)
                .isFalse();

        verify(mockEpisodeDao).existsById(EPISODE_ID);

        verifyNoMoreInteractions(mockEpisodeDao);
    }
}