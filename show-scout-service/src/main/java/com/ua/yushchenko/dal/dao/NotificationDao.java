package com.ua.yushchenko.dal.dao;

import com.ua.yushchenko.model.persistence.NotificationDb;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * Repository to work with {@link NotificationDb} table
 *
 * @author ivanshalaev
 * @version v.0.1
 */
public interface NotificationDao extends CrudRepository<NotificationDb, UUID> {
}
