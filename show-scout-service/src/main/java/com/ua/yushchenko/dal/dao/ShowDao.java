package com.ua.yushchenko.dal.dao;

import com.ua.yushchenko.model.persistence.ShowDb;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository to work with {@link ShowDb} table
 *
 * @author ivanshalaev
 * @version v.0.1
 */
@Repository
public interface ShowDao extends ListCrudRepository<ShowDb, UUID> {
    List<ShowDb> findByShowName(String showName);
}
