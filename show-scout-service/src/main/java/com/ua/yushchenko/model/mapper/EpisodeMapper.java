package com.ua.yushchenko.model.mapper;

import com.ua.yushchenko.api.EpisodeApi;
import com.ua.yushchenko.config.MapperConfiguration;
import com.ua.yushchenko.model.domain.Episode;
import com.ua.yushchenko.model.domain.Show;
import com.ua.yushchenko.model.persistence.EpisodeDB;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;

import java.util.List;

/**
 * Class to represent mapping Episode entity.
 *
 * @author ivanshalaev
 * @version 0.1
 */
@Mapper(config = MapperConfiguration.class)
public interface EpisodeMapper {
    EpisodeApi toEpisodeApi(final Episode episode);

    Episode toEpisode(final EpisodeApi episodeApi);

    Episode toEpisode(final EpisodeDB episodeDB);

    EpisodeDB toEpisodeDb(final Episode episode);

    List<EpisodeApi> toEpisodesApiFromEpisodes(final List<Episode> episodes);

    List<Episode> toEpisodesFromEpisodesApi(final List<EpisodeApi> episodes);

    List<Episode> toEpisodesFromEpisodesDb(final List<EpisodeDB> episodes);

    List<EpisodeDB> toEpisodesDbFromEpisodes(final List<Episode> episodes);

    @ObjectFactory
    default Episode.EpisodeBuilder createDomainBuilder() {
        return Episode.builder();
    }
}
