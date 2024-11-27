package com.ua.yushchenko.dal.repository;

import static com.ua.yushchenko.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.ua.yushchenko.dal.dao.ShowDao;
import com.ua.yushchenko.model.domain.Show;
import com.ua.yushchenko.model.mapper.ShowMapper;
import com.ua.yushchenko.model.persistence.ShowDb;
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

        verifyNoInteractions(mockShowMapper);
        verifyNoMoreInteractions(mockShowDao);
    }

    @Test
    void selectShowByName_nominal() {
        //GIVEN
        when(mockShowDao.findByShowName(SHOW.getShowName())).thenReturn(Optional.of(SHOW_DB));
        when(mockShowMapper.toShow(SHOW_DB)).thenReturn(SHOW);

        //WHEN
        final Show result = unit.selectShowByName(SHOW.getShowName());

        //THEN
        assertThat(result).isNotNull()
                .isEqualTo(SHOW);

        verify(mockShowDao).findByShowName(SHOW.getShowName());
        verify(mockShowMapper).toShow(SHOW_DB);

        verifyNoMoreInteractions(mockShowDao, mockShowMapper);
    }

    @Test
    void selectShowByNameWorkCorrectlyWhenShowWithGivenIdDoesNotExist() {
        //GIVEN
        when(mockShowDao.findByShowName(SHOW_NAME_DOES_NOT_EXIST)).thenReturn(Optional.empty());

        //WHEN
        final Show result = unit.selectShowByName(SHOW_NAME_DOES_NOT_EXIST);

        //THEN
        assertThat(result).isNull();

        verify(mockShowDao).findByShowName(SHOW_NAME_DOES_NOT_EXIST);

        verifyNoMoreInteractions(mockShowDao);
        verifyNoInteractions(mockShowMapper);
    }

    @Test
    void selectAllShows_nominal() {
        //GIVEN
        final Iterable<ShowDb> showDbList = List.of(SHOW_DB);
        final List<Show> showList = List.of(SHOW);
        when(mockShowDao.findAll()).thenReturn(showDbList);
        when(mockShowMapper.toShow(SHOW_DB)).thenReturn(SHOW);

        //WHEN
        final List<Show> result = unit.selectAllShows();

        //THEN
        assertThat(result).isNotNull()
                .isEqualTo(showList);

        verify(mockShowDao).findAll();
        verify(mockShowMapper).toShow(SHOW_DB);

        verifyNoMoreInteractions(mockShowDao, mockShowMapper);
    }

    @Test
    void selectAllShowsWorkCorrectlyWhenNoShowsInSystem() {
        //GIVEN
        when(mockShowDao.findAll()).thenReturn(Collections.emptyList());

        //WHEN
        final List<Show> result = unit.selectAllShows();

        //THEN
        assertThat(result).isNotNull().hasSize(0);

        verify(mockShowDao).findAll();

        verifyNoMoreInteractions(mockShowDao);
        verifyNoInteractions(mockShowMapper);
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
    void deleteShowById() {
        // GIVEN

        // No setup required for mockShowDao as the deleteById method returns void

        // WHEN
        unit.deletedShowById(SHOW_ID);

        // THEN
        verify(mockShowDao).deleteById(SHOW_ID);

        verifyNoMoreInteractions(mockShowDao);
    }
}
