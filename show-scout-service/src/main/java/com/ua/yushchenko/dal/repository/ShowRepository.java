package com.ua.yushchenko.dal.repository;

import com.ua.yushchenko.dal.dao.ShowDao;
import com.ua.yushchenko.model.domain.Show;
import com.ua.yushchenko.model.domain.User;
import com.ua.yushchenko.model.mapper.ShowMapper;
import com.ua.yushchenko.model.persistence.ShowDb;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

/**
 * Repository to work with {@link ShowDb}
 *
 * @author ivanshalaev
 * @version 0.1
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class ShowRepository {
    @NonNull
    private final ShowDao showDao;
    @NonNull
    private final ShowMapper showMapper;

    /**
     * Select {@link Show} by current ID
     *
     * @param showId ID of show
     * @return {@link Show} by current ID
     */
    public Show selectShowById(final UUID showId) {
        log.trace("selectShowById.E: Select show from table by ID:{}", showId);

        final Show show = showDao.findById(showId)
                .map(showMapper::toShow)
                .orElse(null);

        log.trace("selectShowById.X: Show:{}", show);
        return show;
    }

    /**
     * Select a list of {@link Show} by the given name.
     *
     * @param name the name of the show
     * @return a list of {@link Show} with the given name
     */
    public List<Show> selectShowsByName(final String name) {
        log.trace("selectShowsByName.E: Select shows from table by name:{}", name);

        final List<Show> shows = showMapper.toShowFromShowsDb(showDao.findByShowName(name));


        log.trace("selectShowsByName.X: Retrieved {} shows with the name '{}'", shows.size(), name);
        return shows;
    }

    /**
     * Retrieves all {@link Show} from the database
     *
     * @return a {@link List} of all {@link Show} objects in the database as domain objects
     */
    public List<Show> selectAllShows() {
        log.trace("selectAllShows.E: Retrieving all shows from the database");

        List<Show> shows = showMapper.toShowFromShowsDb(showDao.findAll());

        log.trace("selectAllShows.X: Retrieved {} shows", shows.size());
        return shows;
    }

    /**
     * Insert {@link Show}
     *
     * @param showToInsert instance of {@link Show} to insert
     * @return inserted {@link Show}
     */
    public Show insertShow(final Show showToInsert) {
        log.trace("insertShow.E: Insert show to table");

        final ShowDb showDb = showMapper.toShowDb(showToInsert);
        final ShowDb insertedShow = showDao.save(showDb);

        log.trace("insertShow.X: Inserted show:{}", insertedShow);
        return showMapper.toShow(insertedShow);
    }

    /**
     * Update {@link Show}
     *
     * @param showToUpdate instance of {@link Show} to insert
     * @return updated {@link User}
     */
    public Show updateShow(final Show showToUpdate) {
        log.trace("updateShow.E: Update show to table");

        final ShowDb showDb = showMapper.toShowDb(showToUpdate);
        final ShowDb updatedShow = showDao.save(showDb);

        log.trace("updateShow.X: Updated show:{}", updatedShow);
        return showMapper.toShow(updatedShow);
    }

    /**
     * Delete {@link Show}
     *
     * @param showId ID of user
     */
    public void deletedShowById(final UUID showId) {
        log.trace("deleteShowById.E: Delete show from table by ID:{}", showId);

        showDao.deleteById(showId);

        log.trace("deleteShowById.X: Show from table by ID:{} was deleted", showId);
    }
}
