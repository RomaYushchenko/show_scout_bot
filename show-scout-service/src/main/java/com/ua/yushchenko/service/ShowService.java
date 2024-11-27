package com.ua.yushchenko.service;

import com.ua.yushchenko.dal.repository.ShowRepository;
import com.ua.yushchenko.model.domain.Show;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Service that exposes the base functionality for interacting with {@link Show} data
 *
 * @author ivanshalaev
 * @version 0.1
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class ShowService {

    @NonNull
    private ShowRepository showRepository;

    /**
     * Get {@link Show} by current ID
     *
     * @param showId ID of {@link Show}
     * @return {@link Show} by current ID
     */
    public Show getShowById(final UUID showId) {
        log.debug("getShowById.E: Get Show by ID:{}", showId);

        final Show show = showRepository.selectShowById(showId);

        log.debug("getShowById.X: Show:{}", show);
        return show;
    }
    /**
     * Get {@link Show} by name
     *
     * @param name name of {@link Show}
     * @return {@link Show} by name
     */
    public Show getShowByName(final String name){
        log.debug("getShowByName.E: Get Show by name:{}", name);

        final Show show = showRepository.selectShowByName(name);

        log.debug("getShowByName.X: Show:{}",show);
        return show;
    }

    /**
     * Retrieves all {@link Show} instances from the database.
     *
     * @return a {@link List} of all {@link Show} objects in the database as domain objects.
     */
    public List<Show> getAllShows() {
        log.debug("getAllShow.E: Get all shows");

        final List<Show> shows = showRepository.selectAllShows();

        log.debug("getAllShow.X:  Retrieved {} shows", shows.size());
        return shows;
    }

    /**
     * Create {@link Show}
     *
     * @param show instance of {@link Show} to create
     * @return created {@link Show}
     */
    public Show createShow(final Show show) {
        log.debug("createShow.E: Create new show");

        final Show createdShow = showRepository.insertShow(show);

        log.debug("createShow.X: Created show:{}", createdShow);
        return createdShow;
    }

    /**
     * Update {@link Show}
     *
     * @param showId       ID of {@link Show}
     * @param showToUpdate instance of {@link Show} to update
     * @return updated {@link Show}
     */
    public Show updateShow(final UUID showId, final Show showToUpdate) {
        log.debug("updateShow.E: Update show by ID:{}", showId);
        final Show show = showRepository.selectShowById(showId);
//TODO: Add validation for showToUpdate.
        if (Objects.isNull(show)) {
            log.warn("updateShow.X: Show doesn't find in system");
            return null;
        }

        if (Objects.equals(showToUpdate, show)) {
            log.debug("updateShow.X: Show didn't update due to same object");
            return show;
        }

        final Show updatedShow = showRepository.updateShow(showToUpdate);

        log.debug("updateShow.X: Updated show:{}", updatedShow);
        return updatedShow;
    }

    /**
     * Delete {@link Show} by current ID
     *
     * @param showId ID of {@link Show}
     * @return deleted {@link Show}
     */
    public Show deletedShow(final UUID showId) {
        log.debug("deleteShow.E: Delete show by ID:{}", showId);

        final Show show = getShowById(showId);

        if (Objects.isNull(show)) {
            log.warn("deleteShow.X: Show doesn't find in system");
            return null;
        }

        showRepository.deletedShowById(showId);

        log.debug("deleteShow.X: Deleted show:{}", show);
        return show;
    }
}