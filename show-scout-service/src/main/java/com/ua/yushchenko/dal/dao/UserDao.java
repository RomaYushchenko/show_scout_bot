package com.ua.yushchenko.dal.dao;

import com.ua.yushchenko.model.persistence.UserDb;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to work with {@link UserDb} table
 *
 * @author romanyushchenko
 * @version v.0.1
 */
@Repository
public interface UserDao extends ListCrudRepository<UserDb, Long> {

}
