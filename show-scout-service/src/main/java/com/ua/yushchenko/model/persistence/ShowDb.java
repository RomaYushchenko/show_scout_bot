package com.ua.yushchenko.model.persistence;

import com.ua.yushchenko.enums.Genre;
import com.ua.yushchenko.enums.ShowStatus;


import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;


import java.util.List;
import java.util.UUID;

/**
 * Entity class representing a TV show in the database.
 * This class maps to the "show_scout_show" table and contains information
 * about the show's details, including its name, genres, summary, platform,
 * score, status, and image URL.
 * <p>
 * Fields include:
 * <ul>
 *   <li><b>showID</b>: Unique identifier for the show.</li>
 *   <li><b>showName</b>: Name of the show.</li>
 *   <li><b>genres</b>: List of genres associated with the show, stored as JSONB in the database.</li>
 *   <li><b>summary</b>: A brief summary of the show.</li>
 *   <li><b>platform</b>: The platform where the show is available.</li>
 *   <li><b>score</b>: The rating score of the show.</li>
 *   <li><b>showStatus</b>: The current status of the show (e.g., running, ended).</li>
 *   <li><b>img</b>: URL for the image associated with the show.</li>
 * </ul>
 *
 * @author ivanshalaev
 * @version 1.0
 */
@Table(name = "show_scout_show")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowDb {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "show_id", nullable = false)
    UUID showID;
    @Column(name = "show_name", nullable = false)
    String showName;
    @Type(JsonBinaryType.class)
    @Column(name = "genres", columnDefinition = "jsonb", nullable = false)
    List<Genre> genres;
    @Column(name = "summary", nullable = false)
    String summary;
    @Column(name = "platform", nullable = false)
    String platform;
    @Column(name = "score", nullable = false)
    Float score;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    ShowStatus showStatus;
    @Column(name = "img", nullable = false)
    String img;
}
