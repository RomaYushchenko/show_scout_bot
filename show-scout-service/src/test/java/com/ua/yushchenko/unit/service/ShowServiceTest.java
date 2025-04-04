package com.ua.yushchenko.unit.service;

import static com.ua.yushchenko.unit.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.ua.yushchenko.dal.repository.ShowRepository;
import com.ua.yushchenko.model.domain.Show;
import com.ua.yushchenko.service.ShowService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;


/**
 * Unit tests for {@link ShowService}
 *
 * @author ivanshalaev
 * @version 0.1
 */
@Tag("unit")
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
    void getShowsByName_nominal() {
        //GIVEN
        final List<Show> shows = List.of(SHOW);
        when(mockShowRepository.selectShowsByName(SHOW.getShowName())).thenReturn(shows);

        //WHEN
        final List<Show> result = unit.getShowsByName(SHOW.getShowName());

        //THEN
        assertThat(result).isNotNull()
                .isEqualTo(shows);
        verify(mockShowRepository).selectShowsByName(SHOW.getShowName());

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
    void getShowsByFilter_nominal_with_show_name() {
        //GIVEN
        when(mockShowRepository.selectShowsByName(SHOW.getShowName()))
                .thenReturn(List.of(SHOW));

        //WHEN
        final var showsByFilter = unit.getShowsByFilter(SHOW.getShowName());

        //THEN
        assertThat(showsByFilter)
                .isNotNull()
                .hasSize(1)
                .isEqualTo(List.of(SHOW));

        verify(mockShowRepository).selectShowsByName(SHOW.getShowName());
        verify(mockShowRepository, never()).selectAllShows();

        verifyNoMoreInteractions(mockShowRepository);
    }

    @Test
    void getShowsByFilter_nominal_without_show_name() {
        //GIVE
        when(mockShowRepository.selectAllShows()).thenReturn(List.of(SHOW));

        //WHEN
        final var showsByFilter = unit.getShowsByFilter(null);

        //THEN
        assertThat(showsByFilter)
                .isNotNull()
                .hasSize(1)
                .isEqualTo(List.of(SHOW));

        verify(mockShowRepository).selectAllShows();
        verify(mockShowRepository, never()).selectShowsByName(any());

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
        when(mockShowRepository.showExistById(SHOW_ID)).thenReturn(true);
        when(mockShowRepository.deletedShowById(SHOW_ID)).thenReturn(SHOW);

        //WHEN
        final Show result = unit.deletedShow(SHOW_ID);

        //THEN
        assertThat(result).isNotNull()
                .isEqualTo(SHOW);

        verify(mockShowRepository).showExistById(SHOW_ID);
        verify(mockShowRepository).deletedShowById(SHOW_ID);

        verifyNoMoreInteractions(mockShowRepository);
    }

    @Test
    void deleteShowWorksCorrectlyWhenShowWithGivenIdDoesNotExist() {
        //GIVEN
        when(mockShowRepository.showExistById(SHOW_ID_DOES_NOT_EXIST)).thenReturn(false);

        //WHEN
        final Show result = unit.deletedShow(SHOW_ID_DOES_NOT_EXIST);

        //THEN
        assertThat(result).isNull();

        verify(mockShowRepository).showExistById(SHOW_ID_DOES_NOT_EXIST);
        verify(mockShowRepository, never()).deletedShowById(any());

        verifyNoMoreInteractions(mockShowRepository);
    }

    @Test
    void showExistById_nominal() {
        //GIVEN
        when(mockShowRepository.showExistById(SHOW_ID)).thenReturn(true);

        //WHEN
        final var result = unit.showExistById(SHOW_ID);

        //THEN
        assertThat(result)
                .isTrue();

        verify(mockShowRepository).showExistById(SHOW_ID);

        verifyNoMoreInteractions(mockShowRepository);
    }

    @Test
    void showExistById_nominal_when_show_id_does_not_exist() {
        //GIVEN
        final var showIdDoesNotExist = UUID.randomUUID();
        when(mockShowRepository.showExistById(showIdDoesNotExist)).thenReturn(false);

        //WHEN
        final var result = unit.showExistById(showIdDoesNotExist);

        //THEN
        assertThat(result)
                .isFalse();

        verify(mockShowRepository).showExistById(showIdDoesNotExist);

        verifyNoMoreInteractions(mockShowRepository);
    }
}
