package com.ua.yushchenko.dal.dao;

import com.ua.yushchenko.model.persistence.EpisodeDB;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository to work with {@link EpisodeDB} table
 *
 * @author ivanshalaev
 * @version v.0.1
 */
@Repository
public interface EpisodeDao extends ListCrudRepository<EpisodeDB, UUID> {

    List<EpisodeDB> findAllByShowId(final UUID showId);

    void deleteAllByShowId(final UUID showId);
}
