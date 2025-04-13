package com.ua.yushchenko.unit.controller;

import static com.ua.yushchenko.unit.TestData.SHOW;
import static com.ua.yushchenko.unit.TestData.SHOW_API;
import static com.ua.yushchenko.unit.TestData.SHOW_ID;
import static com.ua.yushchenko.unit.TestData.SHOW_ID_DOES_NOT_EXIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import com.ua.yushchenko.api.ShowApi;
import com.ua.yushchenko.common.exceptions.model.ShowScoutNotFoundException;
import com.ua.yushchenko.controller.ShowController;
import com.ua.yushchenko.model.mapper.ShowMapper;
import com.ua.yushchenko.service.ShowService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Tag("unit")
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
                .isInstanceOf(ShowScoutNotFoundException.class)
                .hasMessage("Show by " + SHOW_ID_DOES_NOT_EXIST + " doesn't exist in system");

        verify(mockShowService).getShowById(SHOW_ID_DOES_NOT_EXIST);
        verify(mockShowMapper, never()).toShowApi(any());

        verifyNoMoreInteractions(mockShowService);
    }

    @Test
    void getShows_nominal_with_show_name() {
        //GIVE
        when(mockShowService.getShowsByFilter(SHOW.getShowName())).thenReturn(List.of(SHOW));
        when(mockShowMapper.toShowsApiFromShows(List.of(SHOW))).thenReturn(List.of(SHOW_API));

        //WHEN
        final List<ShowApi> result = unit.getShows(SHOW.getShowName());

        //THEN
        assertThat(result).isNotNull()
                          .isEqualTo(List.of(SHOW_API));

        verify(mockShowService).getShowsByFilter(SHOW.getShowName());
        verify(mockShowMapper).toShowsApiFromShows(List.of(SHOW));

        verifyNoMoreInteractions(mockShowMapper, mockShowService);
    }

    @Test
    void getShows_nominal_without_show_name() {
        //GIVE
        when(mockShowService.getShowsByFilter(null)).thenReturn(List.of(SHOW));
        when(mockShowMapper.toShowsApiFromShows(List.of(SHOW))).thenReturn(List.of(SHOW_API));

        //WHEN
        final List<ShowApi> result = unit.getShows(null);

        //THEN
        assertThat(result)
                .isNotNull()
                .hasSize(1)
                .isEqualTo(List.of(SHOW_API));

        verify(mockShowService).getShowsByFilter(null);
        verify(mockShowMapper).toShowsApiFromShows(List.of(SHOW));

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
}