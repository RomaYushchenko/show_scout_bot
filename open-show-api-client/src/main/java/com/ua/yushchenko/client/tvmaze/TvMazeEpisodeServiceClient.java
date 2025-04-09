package com.ua.yushchenko.client.tvmaze;

import java.util.List;

import com.ua.yushchenko.api.tvmaze.EpisodeDetailsApi;
import com.ua.yushchenko.common.client.config.FeignConfig;
import lombok.NonNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Class that represented Feign Client for Episode entity from TVmaze resources
 *
 * @author romanyushchenko
 * @version 0.1
 */
@FeignClient(
        name = "TvMazeEpisode",
        url = "https://api.tvmaze.com",
        configuration = FeignConfig.class)
public interface TvMazeEpisodeServiceClient {

    /**
     * Get {@link List} of {@link EpisodeDetailsApi} by ID of show
     *
     * @param showId ID of show
     * @return {@link List} of {@link EpisodeDetailsApi} by ID of show
     */
    @GetMapping("/shows/{showId}/episodes")
    List<EpisodeDetailsApi> getEpisodesByShowId(@NonNull @PathVariable("showId") final String showId);

    /**
     * Get {@link  EpisodeDetailsApi} by ID of episode
     *
     * @param episodeId ID of episode
     * @return {@link  EpisodeDetailsApi} by ID of episode
     */
    @GetMapping("/episodes/{episodeId}")
    EpisodeDetailsApi getEpisodeById(@NonNull @PathVariable("episodeId") final String episodeId);
}
