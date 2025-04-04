package com.ua.yushchenko.unit.dal.repository;

import static com.ua.yushchenko.unit.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ua.yushchenko.dal.dao.ShowDao;
import com.ua.yushchenko.model.domain.Show;
import com.ua.yushchenko.model.mapper.ShowMapper;
import com.ua.yushchenko.model.persistence.ShowDb;
import com.ua.yushchenko.dal.repository.ShowRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for {@link ShowRepository}
 *
 * @author ivanshalaev
 * @version 0.1
 */
@Tag("unit")
@ExtendWith(MockitoExtension.class)
public class ShowRepositoryTest {
    @Mock
    private ShowDao mockShowDao;
    @Mock
    private ShowMapper mockShowMapper;
    @InjectMocks
    private ShowRepository unit;

    @Test
    void selectShowById_nominal() {
        //GIVEN
        when(mockShowDao.findById(SHOW_ID)).thenReturn(Optional.of(SHOW_DB));
        when(mockShowMapper.toShow(SHOW_DB)).thenReturn(SHOW);

        //WHEN
        final Show result = unit.selectShowById(SHOW_ID);

        //THEN
        assertThat(result).isNotNull()
                .isEqualTo(SHOW);

        verify(mockShowDao).findById(SHOW_ID);
        verify(mockShowMapper).toShow(SHOW_DB);

        verifyNoMoreInteractions(mockShowDao, mockShowMapper);
    }

    @Test
    void selectShowByIdWorkCorrectlyWhenShowWithGivenIdDoesNotExist() {
        //GIVEN
        when(mockShowDao.findById(SHOW_ID_DOES_NOT_EXIST)).thenReturn(Optional.empty());

        //WHEN
        final Show result = unit.selectShowById(SHOW_ID_DOES_NOT_EXIST);

        //THEN
        assertThat(result).isNull();

        verify(mockShowDao).findById(SHOW_ID_DOES_NOT_EXIST);
        verify(mockShowMapper, never()).toShow(any(ShowDb.class));

        verifyNoMoreInteractions(mockShowDao);
    }

    @Test
    void selectShowsByName_nominal() {
        //GIVEN
        when(mockShowDao.findByShowName(SHOW_DB.getShowName())).thenReturn(List.of(SHOW_DB));
        when(mockShowMapper.toShowFromShowsDb(List.of(SHOW_DB))).thenReturn(List.of(SHOW));

        //WHEN
        final List<Show> result = unit.selectShowsByName(SHOW_DB.getShowName());

        //THEN
        assertThat(result).isNotNull()
                .isEqualTo(List.of(SHOW));

        verify(mockShowDao).findByShowName(SHOW_DB.getShowName());
        verify(mockShowMapper).toShowFromShowsDb(List.of(SHOW_DB));

        verifyNoMoreInteractions(mockShowDao, mockShowMapper);
    }

    @Test
    void selectShowByNameWorkCorrectlyWhenShowsWithGivenIdDoesNotExist() {
        //GIVEN
        when(mockShowDao.findByShowName(SHOW_NAME_DOES_NOT_EXIST)).thenReturn(Collections.emptyList());
        when(mockShowMapper.toShowFromShowsDb(Collections.emptyList()))
                .thenReturn(Collections.emptyList());

        //WHEN
        final List<Show> result = unit.selectShowsByName(SHOW_NAME_DOES_NOT_EXIST);

        //THEN
        assertThat(result).isNotNull().hasSize(0);

        verify(mockShowDao).findByShowName(SHOW_NAME_DOES_NOT_EXIST);
        verify(mockShowMapper).toShowFromShowsDb(Collections.emptyList());

        verifyNoMoreInteractions(mockShowDao, mockShowMapper);
    }

    @Test
    void selectAllShows_nominal() {
        //GIVEN
        when(mockShowDao.findAll()).thenReturn(List.of(SHOW_DB));
        when(mockShowMapper.toShowFromShowsDb(List.of(SHOW_DB))).thenReturn(List.of(SHOW));

        //WHEN
        final List<Show> result = unit.selectAllShows();

        //THEN
        assertThat(result).isNotNull()
                .isEqualTo(List.of(SHOW));

        verify(mockShowDao).findAll();
        verify(mockShowMapper).toShowFromShowsDb(List.of(SHOW_DB));

        verifyNoMoreInteractions(mockShowDao, mockShowMapper);
    }

    @Test
    void selectAllShowsWorkCorrectlyWhenNoShowsInSystem() {
        //GIVEN
        when(mockShowDao.findAll()).thenReturn(Collections.emptyList());
        when(mockShowMapper.toShowFromShowsDb(Collections.emptyList()))
                .thenReturn(Collections.emptyList());

        //WHEN
        final List<Show> result = unit.selectAllShows();

        //THEN
        assertThat(result).isNotNull().hasSize(0);

        verify(mockShowDao).findAll();
        verify(mockShowMapper).toShowFromShowsDb(Collections.emptyList());

        verifyNoMoreInteractions(mockShowDao, mockShowMapper);
    }

    @Test
    void insertShow_nominal() {
        //GIVEN
        when(mockShowMapper.toShowDb(SHOW)).thenReturn(SHOW_DB);
        when(mockShowMapper.toShow(SHOW_DB)).thenReturn(SHOW);
        when(mockShowDao.save(SHOW_DB)).thenReturn(SHOW_DB);

        //WHEN
        final Show result = unit.insertShow(SHOW);

        //THEN
        assertThat(result).isNotNull()
                .isEqualTo(SHOW);

        verify(mockShowMapper).toShowDb(SHOW);
        verify(mockShowMapper).toShow(SHOW_DB);
        verify(mockShowDao).save(SHOW_DB);

        verifyNoMoreInteractions(mockShowDao, mockShowMapper);
    }

    @Test
    void updateShow_nominal() {
        //GIVEN
        when(mockShowMapper.toShowDb(SHOW)).thenReturn(SHOW_DB);
        when(mockShowMapper.toShow(SHOW_DB)).thenReturn(SHOW);
        when(mockShowDao.save(SHOW_DB)).thenReturn(SHOW_DB);

        //WHEN
        final Show result = unit.updateShow(SHOW);

        //THEN
        assertThat(result).isNotNull()
                .isEqualTo(SHOW);

        verify(mockShowMapper).toShowDb(SHOW);
        verify(mockShowMapper).toShow(SHOW_DB);
        verify(mockShowDao).save(SHOW_DB);

        verifyNoMoreInteractions(mockShowDao, mockShowMapper);
    }

    @Test
    void deleteShowById_nominal() {
        // GIVEN
        when(mockShowDao.findById(SHOW_ID)).thenReturn(Optional.of(SHOW_DB));
        when(mockShowMapper.toShow(SHOW_DB)).thenReturn(SHOW);
        doNothing().when(mockShowDao).deleteById(SHOW_ID);

        // WHEN
        final var result = unit.deletedShowById(SHOW_ID);

        // THEN
        assertThat(result)
                .isNotNull()
                .isEqualTo(SHOW);

        verify(mockShowDao).findById(SHOW_ID);
        verify(mockShowMapper).toShow(SHOW_DB);
        verify(mockShowDao).deleteById(SHOW_ID);

        verifyNoMoreInteractions(mockShowDao, mockShowMapper);
    }

    @Test
    void showExistById_nominal() {
        //GIVEN
        when(mockShowDao.existsById(SHOW_ID)).thenReturn(true);

        //WHEN
        final var result = unit.showExistById(SHOW_ID);

        //THEN
        assertThat(result)
                .isTrue();

        verify(mockShowDao).existsById(SHOW_ID);

        verifyNoMoreInteractions(mockShowDao);
    }

    @Test
    void showExistById_nominal_when_show_id_does_not_exist() {
        //GIVEN
        final var showIdDoesNotExist = UUID.randomUUID();
        when(mockShowDao.existsById(showIdDoesNotExist)).thenReturn(false);

        //WHEN
        final var result = unit.showExistById(showIdDoesNotExist);

        //THEN
        assertThat(result)
                .isFalse();

        verify(mockShowDao).existsById(showIdDoesNotExist);

        verifyNoMoreInteractions(mockShowDao);
    }
}
