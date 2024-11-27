package com.ua.yushchenko.dal.dao;

import com.ua.yushchenko.model.persistence.ShowDb;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository to work with {@link ShowDb} table
 *
 * @author ivanshalaev
 * @version v.0.1
 */
@Repository
public interface ShowDao extends CrudRepository<ShowDb, UUID> {
    Iterable<ShowDb> findByShowName(String showName);
}
