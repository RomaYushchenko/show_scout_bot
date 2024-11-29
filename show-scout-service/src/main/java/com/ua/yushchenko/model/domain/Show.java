package com.ua.yushchenko.model.domain;

import com.ua.yushchenko.enums.Genre;
import com.ua.yushchenko.enums.ShowStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * Class that represents a domain object for a TV show in the application.
 * It contains detailed information about the show, including its name, genres,
 * summary, platform, score, and current status. This class is designed to
 * accommodate both existing and future enhancements.
 * <p>
 * Fields include:
 * <ul>
 *   <li>Unique identifier for the show</li>
 *   <li>Name of the show</li>
 *   <li>List of genres associated with the show</li>
 *   <li>Summary description</li>
 *   <li>Platform where the show is available</li>
 *   <li>Score rating of the show</li>
 *   <li>Status indicating whether the show is running or ended</li>
 *   <li>Image URL for the show</li>
 *   <li>Planned extensions such as external API details and episodes</li>
 * </ul>
 *
 * @author ivanshalaev
 * @version 0.1
 */
@Data
@Builder(toBuilder = true)
public class Show {
    UUID showID;
    String showName;
    List<Genre> genres;
    String summary;
    String platform;
    Float score;
    ShowStatus showStatus;
    String img;
/*TODO: Add fields:
    ExternalApis externalApis;
 */
}
