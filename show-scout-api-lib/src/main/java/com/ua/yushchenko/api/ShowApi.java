package com.ua.yushchenko.api;

import com.ua.yushchenko.enums.Genre;
import com.ua.yushchenko.enums.ShowStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Class that represents the API object for a Show.
 * <p>
 * This class is used to transfer show data between the API layer and other application layers.
 *
 * @author ivanshalaiev
 * @version 0.1
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ShowApi {
    UUID showID;
    String showName;
    List<Genre> genres;
    String summary;
    String platform;
    Float score;
    ShowStatus showStatus;
    String img;
}
