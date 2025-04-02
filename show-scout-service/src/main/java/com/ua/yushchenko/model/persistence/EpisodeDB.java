package com.ua.yushchenko.model.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
/**
 * Entity class representing a single TV show episode in the database.
 * This class maps to the "show_scout_episode" table and contains detailed
 * information about an episode, including its parent show, name, summary,
 * season and episode number, and air date/time.
 * <p>
 * Fields include:
 * <ul>
 * <li><b>episodeID</b>: Unique identifier for the episode (primary key).</li>
 * <li><b>showId</b>: Identifier of the parent show this episode belongs to (foreign key).</li>
 * <li><b>episodeName</b>: The name or title of the episode.</li>
 * <li><b>summary</b>: A brief summary or description of the episode's content.</li>
 * <li><b>season</b>: The season number in which the episode appears.</li>
 * <li><b>episodeNumber</b>: The number of the episode within its season.</li>
 * <li><b>airDate</b>: The date the episode was originally aired.</li>
 * <li><b>airTime</b>: The time the episode was originally aired.</li>
 * </ul>
 * <p>
 *
 * @author ivanshalaev
 * @version 1.0
 */
@Table(name = "show_scout_episode")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EpisodeDB {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "episode_id", nullable = false)
    UUID episodeID;
    @Column(name = "show_id", nullable = false)
    UUID showId;
    @Column(name = "episode_name", nullable = false)
    String episodeName;
    @Column(name = "summary", nullable = false)
    String summary;
    @Column(name = "season",nullable = false)
    Integer season;
    @Column(name = "episode_number", nullable = false)
    Integer episodeNumber;
    @Column(name = "air_date", nullable = false)
    LocalDate airDate;
    @Column(name = "air_time", nullable = false)
    LocalTime airTime;
}
