package com.ua.yushchenko.controller;

import com.ua.yushchenko.api.EpisodeApi;
import com.ua.yushchenko.api.SubscriptionApi;
import com.ua.yushchenko.api.UserApi;
import com.ua.yushchenko.model.domain.Episode;
import com.ua.yushchenko.model.domain.Show;
import com.ua.yushchenko.model.mapper.EpisodeMapper;
import com.ua.yushchenko.service.EpisodeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Endpoint for communication with Episode
 *
 * @author ivanshalaev
 * @version 0.1
 */
@Log4j2
@RestController
@RequiredArgsConstructor
public class EpisodeController {

    @NonNull
    private final EpisodeService episodeService;

    @NonNull
    private final EpisodeMapper episodeMapper;

    /**
     * Get {@link EpisodeApi} by current ID
     *
     * @param episodeId ID of episode
     * @return {@link EpisodeApi}
     */
    @GetMapping("episode/{episodeId}")
    public EpisodeApi getEpisode(@PathVariable("episodeId") final UUID episodeId) {
        log.info("getEpisode.E: Get episode by ID: {}", episodeId);

        final var episode = episodeService.getEpisodeById(episodeId);

        if (Objects.isNull(episode)) {
            throw new EntityNotFoundException("Episode doesn't exist in system");
        }

        final var episodeApi = episodeMapper.toEpisodeApi(episode);

        log.info("getEpisode.X: episode: {}", episodeApi);
        return episodeApi;
    }

    /**
     * Get {@link List} of {@link EpisodeApi} based on filter by show ID. If request params aren't provide, the
     * method will return all exist {@link EpisodeApi}
     *
     * @param showId ID of show to filtering {@link EpisodeApi}
     * @return {@link List} of {@link EpisodeApi}
     */
    @GetMapping("/episode")
    public List<EpisodeApi> getEpisodes(@RequestParam(required = false) final UUID showId) {
        log.info("getEpisodes.E: Get episodes  filter by show ID: {}", showId);

        final var episodes = episodeService.getEpisodesByFilter(showId);
        final var episodesApi = episodeMapper.toEpisodesApiFromEpisodes(episodes);

        log.info("getEpisodes.X: Episodes: {}", episodesApi);
        return episodesApi;
    }

    /**
     * Create {@link EpisodeApi} for ID of {@link Show}
     *
     * @param episodeApi instance of {@link EpisodeApi} to create
     * @param showId     ID of {@link Show}
     * @return created {@link EpisodeApi}
     */
    @PostMapping("/episode/show/{showId}")
    public EpisodeApi createEpisode(@RequestBody final EpisodeApi episodeApi,
                                    @PathVariable final UUID showId) {
        log.info("createEpisode.E: Create episode: {} with showId: {}", episodeApi, showId);

        final var episodeToCreate = episodeMapper.toEpisode(episodeApi);

        final var createdEpisode = episodeService.createEpisode(episodeToCreate, showId);

        final var createdEpisodeApi = episodeMapper.toEpisodeApi(createdEpisode);

        log.info("createEpisode.X: Created episode: {}", createdEpisodeApi);
        return createdEpisodeApi;
    }

    @PutMapping("/episode/{episodeId}")
    public EpisodeApi updateEpisode(@PathVariable("episodeId") final UUID episodeId,
                                    @RequestBody final EpisodeApi episodeApi) {
        log.info("updateEpisode.E: Update episode by ID: {}", episodeId);

        if (!Objects.equals(episodeId, episodeApi.getEpisodeID())) {
            throw new IllegalArgumentException("PathVariable episodeId: " + episodeId
                    + " does not match with RequestBody episodeApi.getEpisodeId: "
                    + episodeApi.getEpisodeID());
        }

        final var episodeToUpdate = episodeMapper.toEpisode(episodeApi);

        final var updatedEpisode = episodeService.updateEpisode(episodeId, episodeToUpdate);

        if (Objects.isNull(updatedEpisode)) {
            throw new EntityNotFoundException("Episode with episodeId: "
                    + episodeId + " doesn't exist in system");
        }

        final var updatedEpisodeApi = episodeMapper.toEpisodeApi(updatedEpisode);

        log.info("updateEpisode.X: Updated episode: {}", updatedEpisodeApi);
        return updatedEpisodeApi;
    }

    /**
     * Delete {@link EpisodeApi} by ID
     *
     * @param episodeId ID of episode
     * @return deleted {@link EpisodeApi}
     */
    @DeleteMapping("/episode/{episodeId}")
    public EpisodeApi deleteEpisode(@PathVariable("episodeId") final UUID episodeId) {
        log.info("deleteEpisode.E: Delete episode by ID: {}", episodeId);

        final var episodeToDelete = episodeService.deleteEpisodeById(episodeId);

        if (Objects.isNull(episodeToDelete)) {
            throw new EntityNotFoundException("Episode doesn't exist in system");
        }

        final var episodeToDeleteApi = episodeMapper.toEpisodeApi(episodeToDelete);

        log.info("deleteEpisode.X: Deleted episode: {}", episodeToDeleteApi);
        return episodeToDeleteApi;
    }
}
