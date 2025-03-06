package com.ua.yushchenko.controller;

import static com.ua.yushchenko.TestData.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

import com.ua.yushchenko.api.ShowApi;
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
public class
ShowControllerTest {
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
    void updateShow_nominal_with_show_id_dose_not_exist() {
        //GIVEN
        final var showToUpdate = SHOW.toBuilder()
                .showID(SHOW_ID_DOES_NOT_EXIST)
                .build();
        final var showToUpdateApi = SHOW_API.toBuilder()
                .showID(SHOW_ID_DOES_NOT_EXIST)
                .build();
        when(mockShowMapper.toShow(showToUpdateApi)).thenReturn(showToUpdate);
        when(mockShowService.updateShow(SHOW_ID_DOES_NOT_EXIST, showToUpdate)).thenReturn(null);

        //WHEN /THEN
        assertThatThrownBy(() -> unit.updateShow(SHOW_ID_DOES_NOT_EXIST, showToUpdateApi))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Show by " + SHOW_ID_DOES_NOT_EXIST + " doesn't exist in system");

        verify(mockShowMapper).toShow(showToUpdateApi);
        verify(mockShowService).updateShow(SHOW_ID_DOES_NOT_EXIST, showToUpdate);
        verify(mockShowMapper, never()).toShowApi(any());

        verifyNoMoreInteractions(mockShowMapper, mockShowService);
    }

    @Test
    void updateShow_nominal_with_show_id_dose_not_match_with_show_id_to_update() {
        //GIVEN
        //WHEN /THEN
        assertThatThrownBy(() -> unit.updateShow(SHOW_ID_DOES_NOT_EXIST, SHOW_API))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("PathVariable showId: " + SHOW_ID_DOES_NOT_EXIST
                        + " does not match with RequestBody showApi.getShowID(): "
                        + SHOW_API.getShowID());

        verify(mockShowMapper, never()).toShow(any(ShowApi.class));
        verify(mockShowMapper, never()).toShowApi(any());
        verify(mockShowService, never()).updateShow(any(), any());

        verifyNoInteractions(mockShowService, mockShowMapper);
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
                .hasMessage("Show by " + SHOW_ID_DOES_NOT_EXIST + " doesn't exist in system");

        verify(mockShowService).deletedShow(SHOW_ID_DOES_NOT_EXIST);
        verify(mockShowMapper, never()).toShowApi(any());

        verifyNoMoreInteractions(mockShowService);
    }
}