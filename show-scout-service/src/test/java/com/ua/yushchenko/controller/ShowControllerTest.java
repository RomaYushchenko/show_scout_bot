package com.ua.yushchenko.controller;

import static com.ua.yushchenko.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.ua.yushchenko.api.ShowApi;
import com.ua.yushchenko.model.domain.Show;
import com.ua.yushchenko.model.mapper.ShowMapper;
import com.ua.yushchenko.service.ShowService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;


@ExtendWith(MockitoExtension.class)
public class ShowControllerTest {
    @Mock
    private ShowService mockShowService;
    @Mock
    private ShowMapper mockShowMapper;
    @InjectMocks
    public ShowController unit;

    @Test
    void getShow_nominal() {
        //GIVEN
        when(mockShowService.getShowById(SHOW_ID)).thenReturn(SHOW);
        when(mockShowMapper.toShowApi(SHOW)).thenReturn(SHOW_API);

        //WHEN
        ShowApi result = unit.getShow(SHOW_ID);

        //THEN
        assertThat(result).isNotNull()
                .isEqualTo(SHOW_API);

        verify(mockShowService).getShowById(SHOW_ID);
        verify(mockShowMapper).toShowApi(SHOW);

        verifyNoMoreInteractions(mockShowService, mockShowMapper);
    }

    @Test
    void getShowWorkCorrectlyWhenShowWithGivenIdDoesNotExist() {
        //GIVEN
        when(mockShowService.getShowById(SHOW_ID_DOES_NOT_EXIST)).thenReturn(null);

        //WHEN /THEN
        assertThatThrownBy(() -> unit.getShow(SHOW_ID_DOES_NOT_EXIST))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Show doesn't exist in system");

        verify(mockShowService).getShowById(SHOW_ID_DOES_NOT_EXIST);

        verifyNoMoreInteractions(mockShowService);
        verifyNoInteractions(mockShowMapper);
    }

    @Test
    void getShowByName_nominal() {
        //GIVEN
        when(mockShowService.getShowByName(SHOW.getShowName())).thenReturn(SHOW);
        when(mockShowMapper.toShowApi(SHOW)).thenReturn(SHOW_API);

        //WHEN
        final ShowApi result = unit.getShowByName(SHOW.getShowName());

        //THEN
        assertThat(result).isNotNull()
                .isEqualTo(SHOW_API);

        verify(mockShowService).getShowByName(SHOW.getShowName());
        verify(mockShowMapper).toShowApi(SHOW);

        verifyNoMoreInteractions(mockShowService, mockShowMapper);
    }

    @Test
    void getShowByNameWorkCorrectlyWhenShowWithGivenIdDoesNotExist() {
        //GIVEN
        when(mockShowService.getShowByName(SHOW_NAME_DOES_NOT_EXIST)).thenReturn(null);

        //WHEN /THEN
        assertThatThrownBy(() -> unit.getShowByName(SHOW_NAME_DOES_NOT_EXIST))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Show whit this " + SHOW_NAME_DOES_NOT_EXIST + " doesn't exist in system");

        verify(mockShowService).getShowByName(SHOW_NAME_DOES_NOT_EXIST);

        verifyNoMoreInteractions(mockShowService);
        verifyNoInteractions(mockShowMapper);
    }

    @Test
    void getAllShows_nominal() {
        //GIVEN
        final List<ShowApi> showsApi = List.of(SHOW_API);
        final List<Show> shows = List.of(SHOW);
        when(mockShowService.getAllShows()).thenReturn(shows);
        when(mockShowMapper.toShowsApiFromShows(shows)).thenReturn(showsApi);

        //WHEN
        final List<ShowApi> result = unit.getAllShows();

        //THEN
        assertThat(result).isNotNull()
                .isEqualTo(showsApi);

        verify(mockShowService).getAllShows();
        verify(mockShowMapper).toShowsApiFromShows(shows);

        verifyNoMoreInteractions(mockShowService, mockShowMapper);
    }


    @Test
    void createShow_nominal() {
        //GIVEN
        when(mockShowMapper.toShow(SHOW_API)).thenReturn(SHOW);
        when(mockShowMapper.toShowApi(SHOW)).thenReturn(SHOW_API);
        when(mockShowService.createShow(SHOW)).thenReturn(SHOW);

        //WHEN
        final ShowApi result = unit.createShow(SHOW_API);

        //THEN
        assertThat(result).isNotNull()
                .isEqualTo(SHOW_API);

        verify(mockShowMapper).toShow(SHOW_API);
        verify(mockShowMapper).toShowApi(SHOW);
        verify(mockShowService).createShow(SHOW);

        verifyNoMoreInteractions(mockShowMapper, mockShowService);
    }

    @Test
    void updateShow_nominal() {
        //GIVEN
        when(mockShowMapper.toShow(SHOW_API)).thenReturn(SHOW);
        when(mockShowMapper.toShowApi(SHOW)).thenReturn(SHOW_API);
        when(mockShowService.updateShow(SHOW_ID, SHOW)).thenReturn(SHOW);

        //WHEN
        final ShowApi result = unit.updateShow(SHOW_ID, SHOW_API);

        //THEN
        assertThat(result).isNotNull()
                .isEqualTo(SHOW_API);

        verify(mockShowMapper).toShow(SHOW_API);
        verify(mockShowMapper).toShowApi(SHOW);
        verify(mockShowService).updateShow(SHOW_ID, SHOW);

        verifyNoMoreInteractions(mockShowMapper, mockShowService);
    }

    @Test
    void updateShowWorkCorrectlyWhenShowWithGivenIdDoesNotExist() {
        //GIVEN
        when(mockShowMapper.toShow(SHOW_API)).thenReturn(SHOW);
        when(mockShowService.updateShow(SHOW_ID_DOES_NOT_EXIST, SHOW)).thenReturn(null);

        //WHEN /THEN
        assertThatThrownBy(() -> unit.updateShow(SHOW_ID_DOES_NOT_EXIST, SHOW_API))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Show doesn't exist in system");

        verify(mockShowMapper).toShow(SHOW_API);
        verify(mockShowService).updateShow(SHOW_ID_DOES_NOT_EXIST, SHOW);
        verify(mockShowMapper, never()).toShowApi(any());

        verifyNoMoreInteractions(mockShowMapper, mockShowService);
    }

    @Test
    void deleteShow_nominal() {
        //GIVEN
        when(mockShowService.deletedShow(SHOW_ID)).thenReturn(SHOW);
        when(mockShowMapper.toShowApi(SHOW)).thenReturn(SHOW_API);

        //WHEN
        final ShowApi result = unit.deleteShow(SHOW_ID);

        //THEN
        assertThat(result).isNotNull()
                .isEqualTo(SHOW_API);

        verify(mockShowService).deletedShow(SHOW_ID);
        verify(mockShowMapper).toShowApi(SHOW);

        verifyNoMoreInteractions(mockShowService, mockShowMapper);
    }

    @Test
    void deleteShowWorkCorrectlyWhenShowWithGivenIdDoesNotExist() {
        //GIVEN
        when(mockShowService.deletedShow(SHOW_ID_DOES_NOT_EXIST)).thenReturn(null);

        //WHEN /THEN
        assertThatThrownBy(() -> unit.deleteShow(SHOW_ID_DOES_NOT_EXIST))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Show doesn't exist in system");

        verify(mockShowService).deletedShow(SHOW_ID_DOES_NOT_EXIST);
        verify(mockShowMapper, never()).toShowApi(any());

        verifyNoMoreInteractions(mockShowService);
    }
}
















































































