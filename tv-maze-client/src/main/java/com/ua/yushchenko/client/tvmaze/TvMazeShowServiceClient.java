package com.ua.yushchenko.client.tvmaze;

import java.util.List;
import java.util.Map;

import com.ua.yushchenko.api.tvmaze.ShowDetailsApi;
import com.ua.yushchenko.api.tvmaze.TvMazeShowApi;
import com.ua.yushchenko.client.config.FeignConfig;
import lombok.NonNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Class that represented Feign Client for Show entity from TVmaze resources
 *
 * @author romanyushchenko
 * @version 0.1
 */
@FeignClient(
        name = "TvMazeShow",
        url = "https://api.tvmaze.com",
        configuration = FeignConfig.class)
public interface TvMazeShowServiceClient {

    /**
     * Get {@link List} of {@link TvMazeShowApi} that matched by showName
     *
     * @param params instance of map of query parameter (ex. Key:q Value:From)
     * @return {@link List} of {@link TvMazeShowApi} by show mame
     */
    @GetMapping("/search/shows")
    List<TvMazeShowApi> getShowsByName(@SpringQueryMap final Map<String, String> params);

    /**
     * Get {@link ShowDetailsApi} that matched by showName
     *
     * @param params instance of map of query parameter (ex. Key:q Value:From)
     * @return {@link ShowDetailsApi} by show mame
     */
    @GetMapping("/singlesearch/shows")
    ShowDetailsApi getShowByName(@SpringQueryMap final Map<String, String> params);

    /**
     * Get {@link ShowDetailsApi} by ID of the {@link ShowDetailsApi}
     *
     * @param showId ID of the {@link ShowDetailsApi}
     * @return {@link ShowDetailsApi} by ID of the {@link ShowDetailsApi}
     */
    @GetMapping("/shows/{showId}")
    ShowDetailsApi getShow(@NonNull @PathVariable("showId") final Integer showId);

    /**
     * Get {@link List} of {@link TvMazeShowApi} that matched by showName
     *
     * @param showName name of show
     * @return {@link List} of {@link TvMazeShowApi} that matched by showName
     */
    default List<TvMazeShowApi> getShowsByName(final String showName) {
        return getShowsByName(Map.of("q", showName));
    }

    /**
     * Get {@link ShowDetailsApi} that matched by showName
     *
     * @param showName name of show
     * @return {@link List} of {@link TvMazeShowApi} that matched by showName
     */
    default ShowDetailsApi getShowByName(final String showName) {
        return getShowByName(Map.of("q", showName));
    }
}
