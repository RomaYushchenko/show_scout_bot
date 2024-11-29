package com.ua.yushchenko.model.mapper;

import com.ua.yushchenko.api.ShowApi;
import com.ua.yushchenko.config.MapperConfiguration;
import com.ua.yushchenko.model.domain.Show;
import com.ua.yushchenko.model.persistence.ShowDb;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;

import java.util.List;

/**
 * Class to represent mapping Show entity.
 *
 * @author ivanshalaev
 * @version 0.1
 */
@Mapper(config = MapperConfiguration.class)
public interface ShowMapper {
    ShowApi toShowApi(final Show show);

    Show toShow(final ShowApi showApi);

    Show toShow(final ShowDb showDb);

    ShowDb toShowDb(final Show show);

    List<ShowApi> toShowsApiFromShows(final List<Show> listShows);

    List<Show> toShowFromShowsApi(final List<ShowApi> listShowsApi);

    List<Show> toShowFromShowsDb(final List<ShowDb> listShowsDb);

    List<ShowDb> toShowsDbFromShows(final List<Show> listShows);

    @ObjectFactory
    default Show.ShowBuilder createDomainBuilder() {
        return Show.builder();
    }
}
