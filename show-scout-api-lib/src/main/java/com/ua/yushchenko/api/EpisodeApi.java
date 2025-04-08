package com.ua.yushchenko.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

/**
 * Class that represents the API object for an Episode.
 * <p>
 * This class is used to structure and transfer episode data, typically as a
 * request body or response payload in API interactions (e.g., REST endpoints).
 * It defines the contract for how episode information is exchanged via the API.
 * <p>
 *
 * @author ivanshalaiev
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class EpisodeApi {
    UUID episodeID;
    UUID showId;
    String episodeName;
    String summary;
    Integer season;
    Integer episodeNumber;
    LocalDate airDate;
    LocalTime airTime;
}
