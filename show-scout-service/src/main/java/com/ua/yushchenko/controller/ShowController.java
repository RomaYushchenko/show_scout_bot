package com.ua.yushchenko.controller;

import com.ua.yushchenko.api.ShowApi;
import com.ua.yushchenko.common.exceptions.model.ShowScoutNotFoundException;
import com.ua.yushchenko.model.domain.Show;
import com.ua.yushchenko.model.mapper.ShowMapper;
import com.ua.yushchenko.service.ShowService;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Endpoint for communication with Show
 *
 * @author ivanshalaev
 * @version 0.1
 */
@Log4j2
@RestController
@RequiredArgsConstructor
public class ShowController {

    @NonNull
    private final ShowService showService;
    @NonNull
    private final ShowMapper showMapper;

    /**
     * Get {@link ShowApi} by ID
     *
     * @param showId ID of show
     * @return {@link ShowApi}
     */
    @GetMapping("/shows/{showId}")
    public ShowApi getShow(@PathVariable final UUID showId) {
        log.info("getShow.E: Get Show by ID:{}", showId);

        final Show show = showService.getShowById(showId);

        if (Objects.isNull(show)) {
            throw new ShowScoutNotFoundException("Show by %s doesn't exist in system", showId);
        }

        final ShowApi showApi = showMapper.toShowApi(show);

        log.info("getShow.X: show:{}", showId);
        return showApi;
    }

    /**
     * Get {@link List} of {@link Show} based on filter by Show name. If request params aren't provide, the
     * method will return all exist {@link Show}
     *
     * @param showName optional query parameter for the name of the show
     * @return a list of {@link ShowApi}
     */
    @GetMapping("/shows")
    public List<ShowApi> getShows(@RequestParam(required = false) final String showName) {
        log.info("getShows.E: Get shows by name: {}", showName);

        final var showsByFilter = showService.getShowsByFilter(showName);
        final var shows = showMapper.toShowsApiFromShows(showsByFilter);

        log.info("getShows.X: Retrieved {} shows, whit showName: {}", shows.size(), showName);
        return shows;
    }

    /**
     * Create {@link ShowApi}
     *
     * @param showApi instance of {@link ShowApi} to create
     * @return created {@link ShowApi}
     */
    @PostMapping("/shows")
    public ShowApi createShow(@RequestBody final ShowApi showApi) {
        log.info("createShow.E: Create show");

        final Show showToCreate = showMapper.toShow(showApi);
        final Show createdShow = showService.createShow(showToCreate);
        final ShowApi createdShowApi = showMapper.toShowApi(createdShow);

        log.info("createShow.X: Created show:{}", createdShowApi);
        return createdShowApi;
    }

    /**
     * Update {@link ShowApi}
     *
     * @param showId  ID of show
     * @param showApi instance of {@link ShowApi} to update
     * @return updated {@link ShowApi}
     */
    @PutMapping("/shows/{showId}")
    public ShowApi updateShow(@PathVariable final UUID showId,
                              @RequestBody final ShowApi showApi) {
        log.info("updateShow.E: Update show by ID:{}", showId);

        final Show showToUpdate = showMapper.toShow(showApi);
        final Show updatedShow = showService.updateShow(showId, showToUpdate);
        final ShowApi updatedShowApi = showMapper.toShowApi(updatedShow);

        log.info("updateShow.X: Updated show:{}", updatedShowApi);
        return updatedShowApi;
    }

    /**
     * Delete {@link ShowApi} by ID
     *
     * @param showId ID of show
     * @return deleted {@link ShowApi}
     */
    @DeleteMapping("/shows/{showId}")
    public ShowApi deleteShow(@PathVariable final UUID showId) {
        log.info("deleteShow.E: Delete show by ID:{}", showId);

        final Show deletedShow = showService.deletedShow(showId);
        final ShowApi deletedShowApi = showMapper.toShowApi(deletedShow);

        log.info("deleteShow.X: Deleted show:{}", deletedShowApi);
        return deletedShowApi;
    }
}