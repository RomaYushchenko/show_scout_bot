package com.ua.yushchenko.api.tvmaze.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class that represented API Links object from TVmaze resources
 *
 * @author romanyushchenko
 * @version 0.1
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class LinksApi {

    private LinkApi self;
    @JsonProperty("previousepisode")
    private LinkApi previousEpisode;
    @JsonProperty("nextepisode")
    private LinkApi nextEpisode;
    private LinkApi show;
    private LinkApi character;
}
