package com.ua.yushchenko.model.mapper;

import com.ua.yushchenko.api.UserApi;
import com.ua.yushchenko.config.MapperConfiguration;
import com.ua.yushchenko.model.domain.User;
import com.ua.yushchenko.model.persistence.UserDb;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ObjectFactory;

/**
 * Class to represent mapping User entity.
 *
 * @author romanyushchenko
 * @version 0.1
 */
@Mapper(config = MapperConfiguration.class)
public interface UserMapper {

    UserApi toUserApi(final User user);

    User toUser(final UserApi userApi);

    @Mappings({
            @Mapping(source = "id.userId", target = "userId"),
            @Mapping(source = "id.telegramUserId", target = "telegramUserId")
    })
    User toUser(final UserDb userDb);

    @Mapping(target = "id", expression = "java(new UserPk(user.getUserId(), user.getTelegramUserId()))")
    UserDb toUserDb(final User user);

    @ObjectFactory
    default User.UserBuilder createDomainBuilder() {
        return User.builder();
    }

}
