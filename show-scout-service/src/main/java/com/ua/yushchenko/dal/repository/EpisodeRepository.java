package com.ua.yushchenko.dal.repository;

import com.ua.yushchenko.dal.dao.EpisodeDao;
import com.ua.yushchenko.model.domain.Episode;
import com.ua.yushchenko.model.mapper.EpisodeMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Repository to work with {@link com.ua.yushchenko.model.persistence.EpisodeDB}
 *
 * @author ivanshalaev
 * @version 0.1
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class EpisodeRepository {

    @NonNull
    private final EpisodeDao episodeDao;

    @NonNull
    private final EpisodeMapper episodeMapper;

    /**
     * Select {@link Episode} by current ID
     *
     * @param episodeId ID of Episode
     * @return {@link Episode} by current ID
     */
    public Episode selectEpisode(final UUID episodeId) {
        log.trace("selectEpisode.E: Select episode from table by ID: {}", episodeId);

        final var episode = episodeDao.findById(episodeId)
                .map(episodeMapper::toEpisode)
                .orElse(null);

        log.trace("selectEpisode.X: Episode: {}", episode);
        return episode;
    }

    /**
     * Select all {@link Episode}
     *
     * @return {@link List} of {@link Episode}
     */
    public List<Episode> selectAllEpisodes() {
        log.trace("selectAllEpisodes.E: Select all episodes");

        List<Episode> episodes = episodeMapper.toEpisodesFromEpisodesDb(episodeDao.findAll());

        log.trace("selectAllEpisodes.X: Returned: {} episodes: {}", episodes.size(), episodes);
        return episodes;
    }

    /**
     * Select {@link List} of {@link Episode} by show ID
     *
     * @param showId ID of show
     * @return {@link List} of {@link Episode}
     */
    public List<Episode> selectEpisodesByShowId(final UUID showId) {
        log.trace("selectEpisodesByShowId.E: Select episodes by show ID: {}", showId);

        final var episodes = episodeMapper.toEpisodesFromEpisodesDb(episodeDao.findAllByShowId(showId));

        log.trace("selectEpisodesByShowId.X: Returned: {} episodes: {}", episodes.size(), episodes);
        return episodes;
    }

    /**
     * Insert {@link Episode}
     *
     * @param episodeToInsert instance {@link Episode} to insert
     * @return inserted {@link Episode}
     */
    public Episode insertEpisode(final Episode episodeToInsert) {
        log.trace("insertEpisode.E: Insert episode: {}", episodeToInsert);

        final var episodeDbToInsert = episodeMapper.toEpisodeDb(episodeToInsert);
        final var insertedEpisodeDb = episodeDao.save(episodeDbToInsert);

        log.trace("insertEpisode.X: Inserted episode: {}", insertedEpisodeDb);
        return episodeMapper.toEpisode(insertedEpisodeDb);
    }

    /**
     * Update {@link Episode}
     *
     * @param episodeToUpdate instance {@link Episode} to update
     * @return updated {@link Episode}
     */
    public Episode updateEpisode(final Episode episodeToUpdate) {
        log.trace("updateEpisode.E: Update episode: {}", episodeToUpdate);

        final var episodeDbToUpdate = episodeMapper.toEpisodeDb(episodeToUpdate);
        final var updatedEpisodeDb = episodeDao.save(episodeDbToUpdate);

        log.trace("updateEpisode.X: Updated episode: {}", updatedEpisodeDb);
        return episodeMapper.toEpisode(updatedEpisodeDb);
    }

    /**
     * Delete {@link Episode} by episode ID
     *
     * @param episodeId ID of {@link Episode} to delete
     * @return deleted {@link Episode}
     */
    public Episode deleteEpisodeById(final UUID episodeId) {
        log.trace("deleteEpisodeById.E: Delete episode by ID: {}", episodeId);

        final var episode = selectEpisode(episodeId);
        episodeDao.deleteById(episodeId);

        log.trace("deleteEpisodeById.X: Deleted episode by ID: {}", episodeId);
        return episode;
    }

    /**
     * Delete {@link List} of {@link Episode} by show ID
     *
     * @param showId ID of show
     * @return {@link List} of deleted {@link Episode}
     */
    public List<Episode> deleteEpisodesByShowId(final UUID showId) {
        log.trace("deleteEpisodesByShowId.E: Delete all episodes with show ID: {}", showId);

        final var episodesToDelete = selectEpisodesByShowId(showId);
        episodeDao.deleteAllByShowId(showId);

        log.trace("deleteEpisodesByShowId.X: Deleted: {} episodes: {}"
                , episodesToDelete.size(), episodesToDelete);
        return episodesToDelete;
    }

    /**
     * Check if {@link Episode} exist by current ID
     *
     * @param episodeId ID of {@link Episode}
     * @return true if episode exist, false otherwise
     */
    public boolean episodeExistById(final UUID episodeId) {
        log.trace("episodeExistById.E:  Check if episode exist with provided ID: {}", episodeId);

        final var episodeExistById = episodeDao.existsById(episodeId);

        log.trace("episodeExistById.X: Episode with id: {}, exist: {}", episodeId, episodeExistById);
        return episodeExistById;
    }
}
