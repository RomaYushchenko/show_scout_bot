package com.ua.yushchenko.service;

import static com.ua.yushchenko.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.ua.yushchenko.dal.repository.ShowRepository;
import com.ua.yushchenko.model.domain.Show;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;


/**
 * Unit tests for {@link ShowService}
 *
 * @author ivanshalaev
 * @version 0.1
 */
@ExtendWith(MockitoExtension.class)
public class ShowServiceTest {
    @Mock
    private ShowRepository mockShowRepository;
    @InjectMocks
    private ShowService unit;

    @Test
    void getShowById_nominal() {
        //GIVEN
        when(mockShowRepository.selectShowById(SHOW_ID)).thenReturn(SHOW);

        //WHEN
        final Show show = unit.getShowById(SHOW_ID);

        //THEN
        assertThat(show).isNotNull()
                .isEqualTo(SHOW);

        verify(mockShowRepository).selectShowById(SHOW_ID);

        verifyNoMoreInteractions(mockShowRepository);
    }

    @Test
    void getShowByName_nominal() {
        //GIVEN
        when(mockShowRepository.selectShowByName(SHOW.getShowName())).thenReturn(SHOW);

        //WHEN
        final Show result = unit.getShowByName(SHOW.getShowName());

        //THEN
        assertThat(result).isNotNull()
                .isEqualTo(SHOW);

        verify(mockShowRepository).selectShowByName(SHOW.getShowName());

        verifyNoMoreInteractions(mockShowRepository);
    }

    @Test
    void getAllShows_nominal() {
        //GIVEN
        final List<Show> shows = List.of(SHOW);
        when(mockShowRepository.selectAllShows()).thenReturn(shows);

        //WHEN
        final List<Show> result = unit.getAllShows();

        //THEN
        assertThat(result).isNotNull()
                .isEqualTo(shows);

        verify(mockShowRepository).selectAllShows();

        verifyNoMoreInteractions(mockShowRepository);
    }

    @Test
    void createShow_nominal() {
        //GIVEN
        when(mockShowRepository.insertShow(SHOW)).thenReturn(SHOW);

        //WHEN
        final Show result = unit.createShow(SHOW);

        //THEN
        assertThat(result).isNotNull()
                .isEqualTo(SHOW);

        verify(mockShowRepository).insertShow(SHOW);

        verifyNoMoreInteractions(mockShowRepository);
    }

    @Test
    void updateShow_nominal() {
        //GIVEN
        final Show updatedShow = SHOW.toBuilder()
                .showName("TestShowName_2")
                .build();

        when(mockShowRepository.selectShowById(SHOW_ID)).thenReturn(SHOW);
        when(mockShowRepository.updateShow(updatedShow)).thenReturn(updatedShow);

        //WHEN
        final Show result = unit.updateShow(SHOW_ID, updatedShow);

        //THEN
        assertThat(result).isNotNull()
                .isEqualTo(updatedShow);

        verify(mockShowRepository).selectShowById(SHOW_ID);
        verify(mockShowRepository).updateShow(updatedShow);

        verifyNoMoreInteractions(mockShowRepository);
    }

    @Test
    void updateShowWorksCorrectlyWhenShowWithGivenIdDoesNotExist() {
        //GIVEN
        when(mockShowRepository.selectShowById(SHOW_ID_DOES_NOT_EXIST)).thenReturn(null);

        //WHEN
        Show result = unit.updateShow(SHOW_ID_DOES_NOT_EXIST, SHOW);

        //THEN
        assertThat(result).isNull();

        verify(mockShowRepository).selectShowById(SHOW_ID_DOES_NOT_EXIST);
        verify(mockShowRepository, never()).updateShow(any());
        verifyNoMoreInteractions(mockShowRepository);
    }

    @Test
    void updateShowWorksCorrectlyWhenShowToUpdateHasNoChange() {
        //GIVEN
        final Show show = SHOW.toBuilder().build();
        when(mockShowRepository.selectShowById(SHOW_ID)).thenReturn(SHOW);

        //WHEN
        Show result = unit.updateShow(SHOW_ID, show);

        //THEN
        assertThat(result).isNotNull()
                .isEqualTo(SHOW);

        verify(mockShowRepository).selectShowById(SHOW_ID);
        verify(mockShowRepository, never()).updateShow(any());
        verifyNoMoreInteractions(mockShowRepository);
    }

    @Test
    void deleteShow_nominal() {
        //GIVEN
        when(mockShowRepository.selectShowById(SHOW_ID)).thenReturn(SHOW);
        doNothing().when(mockShowRepository).deletedShowById(SHOW_ID);

        //WHEN
        final Show result = unit.deletedShow(SHOW_ID);

        //THEN
        assertThat(result).isNotNull()
                .isEqualTo(SHOW);

        verify(mockShowRepository).selectShowById(SHOW_ID);
        verify(mockShowRepository).deletedShowById(SHOW_ID);

        verifyNoMoreInteractions(mockShowRepository);
    }

    @Test
    void deleteShowWorksCorrectlyWhenShowWithGivenIdDoesNotExist() {
        //GIVEN
        when(mockShowRepository.selectShowById(SHOW_ID_DOES_NOT_EXIST)).thenReturn(null);

        //WHEN
        final Show result = unit.deletedShow(SHOW_ID_DOES_NOT_EXIST);

        //THEN
        assertThat(result).isNull();

        verify(mockShowRepository).selectShowById(SHOW_ID_DOES_NOT_EXIST);
        verify(mockShowRepository, never()).deletedShowById(any());

        verifyNoMoreInteractions(mockShowRepository);
    }
}
