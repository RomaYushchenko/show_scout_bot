package com.ua.yushchenko.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

/**
 * Class representing a domain object for a single TV show episode within the application.
 * It encapsulates the core business information about an episode, such as its title,
 * summary, season/episode number, and air date/time. This class serves as the
 * primary representation of an episode in the application's business logic, distinct
 * from database persistence entities.
 * <p>
 * Fields include:
 * <ul>
 * <li>Unique identifier for the episode</li>
 * <li>Identifier for the parent show</li>
 * <li>The name or title of the episode</li>
 * <li>A summary or description of the episode</li>
 * <li>The season number</li>
 * <li>The episode number within the season</li>
 * <li>The original air date</li>
 * <li>The original air time</li>
 * </ul>
 * <p>
 * Lombok annotations (@Data, @NoArgsConstructor, @AllArgsConstructor, @Builder)
 * are used to generate common boilerplate code like getters, setters, constructors, etc.
 *
 * @author ivanshalaev
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Episode {
    UUID episodeID;
    UUID showId;
    String episodeName;
    String summary;
    Integer season;
    Integer episodeNumber;
    LocalDate airDate;
    LocalTime airTime;
}
