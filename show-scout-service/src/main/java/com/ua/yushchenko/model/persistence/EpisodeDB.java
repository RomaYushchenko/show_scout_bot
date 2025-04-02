package com.ua.yushchenko.model.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

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
