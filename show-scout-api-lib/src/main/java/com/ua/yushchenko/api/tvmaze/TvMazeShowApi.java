package com.ua.yushchenko.api.tvmaze;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class that represented API TvMaze Show object from TVmaze resources
 *
 * @author romanyushchenko
 * @version 0.1
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class TvMazeShowApi {

    double score;
    ShowDetailsApi show;
}
