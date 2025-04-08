package com.ua.yushchenko.service;

import com.ua.yushchenko.dal.repository.EpisodeRepository;
import com.ua.yushchenko.model.domain.Episode;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Service that exposes the base functionality for interacting with {@link Episode} data
 *
 * @author ivanshalaev
 * @version 0.1
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class EpisodeService {

    @NonNull
    private final EpisodeRepository episodeRepository;

    @NonNull
    private final ShowService showService;

    /**
     * Get {@link Episode} by current ID
     *
     * @param episodeId ID of {@link Episode}
     * @return {@link Episode} by current ID
     */
    public Episode getEpisodeById(final UUID episodeId) {
        log.debug("getEpisodeById.E: Get episode by current ID: {}", episodeId);

        final var episode = episodeRepository.selectEpisode(episodeId);

        log.debug("getEpisodeById.X: Episode: {}", episode);
        return episode;
    }

    /**
     * Get {@link List} of all {@link Episode}
     *
     * @return {@link List} of all {@link Episode}
     */
    public List<Episode> getAllEpisodes() {
        log.debug("getAllEpisodes.E: Get all episodes");

        final var episodes = episodeRepository.selectAllEpisodes();

        log.debug("getAllEpisodes.X: Retrieved {} episodes: {}", episodes.size(), episodes);
        return episodes;
    }

    /**
     * Get {@link List} of {@link Episode} by show ID
     *
     * @param showId ID of show
     * @return {@link List} of {@link Episode}
     */
    public List<Episode> getEpisodesByShowId(final UUID showId) {
        log.debug("getEpisodesByShowId.E: Get episodes by show ID: {}", showId);

        final var episodes = episodeRepository.selectEpisodesByShowId(showId);

        log.debug("getEpisodesByShowId.X: Retrieved {} episodes: {} with show ID: {}",
                episodes.size(), episodes, showId);
        return episodes;
    }

    /**
     * Get {@link List} of {@link Episode} by the given show ID
     * if show ID was not provided return {@link List} of all {@link Episode}
     *
     * @param showId the ID of the show
     * @return a list of {@link Episode}
     */
    public List<Episode> getEpisodesByFilter(final UUID showId) {
        log.debug("getEpisodesByFilter.E: Get episodes filter by show ID: {}", showId);

        final var episodes = Objects.isNull(showId)
                ? episodeRepository.selectAllEpisodes()
                : episodeRepository.selectEpisodesByShowId(showId);
        log.debug("getEpisodesByFilter.X: Retrieved {} episodes: {} with show ID: {} ",
                episodes.size(), episodes, showId);
        return episodes;
    }

    /**
     * Create {@link Episode} with given show ID
     *
     * @param episode instance of {@link Episode} to create
     * @param showId  the ID of the show
     * @return created {@link Episode}
     */
    public Episode createEpisode(final Episode episode, final UUID showId) {
        log.debug("createEpisode.E: Create episode: {} with show ID: {}", episode, showId);

        final var show = showService.showExistById(showId);

        if (!show) {
            throw new EntityNotFoundException("Show [ID=" + showId + "] doesn't exist in system");
        }

        final var episodeToCreate = episode.toBuilder()
                .showId(showId)
                .build();

        final var createdEpisode = episodeRepository.insertEpisode(episodeToCreate);

        log.debug("createEpisode.X: Created episode: {} with show ID: {}", createdEpisode, showId);
        return createdEpisode;
    }

    /**
     * Update {@link Episode}
     *
     * @param episodeId       ID of {@link Episode}
     * @param episodeToUpdate instance of {@link Episode} to update
     * @return updated {@link Episode}
     */
    public Episode updateEpisode(final UUID episodeId, final Episode episodeToUpdate) {
        log.debug("updateEpisode.E: Update episode by ID: {}", episodeId);

        final var episode = getEpisodeById(episodeId);

        if (Objects.isNull(episode)) {
            log.warn("updateEpisode.X: Episode doesn't find in system");
            return null;
        }

        if (Objects.equals(episodeToUpdate, episode)) {
            log.debug("updateEpisode.X: Episode didn't update due to same object");
            return episode;
        }
        final var updatedEpisode = episodeRepository.updateEpisode(episodeToUpdate);
        log.debug("updateEpisode.X: Updated episode: {}", updatedEpisode);
        return updatedEpisode;
    }

    /**
     * Delete {@link Episode} by current ID
     *
     * @param episodeId ID of {@link Episode}
     * @return deleted {@link Episode}
     */
    public Episode deleteEpisodeById(final UUID episodeId) {
        log.debug("deleteEpisodeById.E: Delete episode by ID: {}", episodeId);

        final var isEpisodeExist = episodeRepository.episodeExistById(episodeId);

        if (!isEpisodeExist) {
            log.warn("deleteEpisodeById.X: Episode doesn't exist in system");
            return null;
        }

        final var deletedEpisode = episodeRepository.deleteEpisodeById(episodeId);
        log.debug("deleteEpisodeById.X: Deleted episode: {}", deletedEpisode);
        return deletedEpisode;
    }

    /**
     * Delete {@link List} of {@link Episode} by show ID
     *
     * @param showId ID of show
     * @return {@link List} of deleted {@link Episode}
     */
    public List<Episode> deleteEpisodesByShowId(final UUID showId) {
        log.debug("deleteEpisodesByShowId.E: Delete episodes by show ID: {}", showId);

        final var isShowExist = showService.showExistById(showId);

        if (!isShowExist) {
            log.warn("deleteEpisodesByShowId.X: Show doesn't find in system");
            return null;
        }

        final var deletedEpisodes = episodeRepository.deleteEpisodesByShowId(showId);
        log.debug("deleteEpisodesByShowId.X: Deleted {} episodes: {}"
                , deletedEpisodes.size(), deletedEpisodes);
        return deletedEpisodes;
    }
}
