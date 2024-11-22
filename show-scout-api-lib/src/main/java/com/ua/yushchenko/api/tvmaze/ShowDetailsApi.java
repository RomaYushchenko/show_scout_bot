package com.ua.yushchenko.api.tvmaze;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ua.yushchenko.api.tvmaze.common.ExternalsApi;
import com.ua.yushchenko.api.tvmaze.common.ImageApi;
import com.ua.yushchenko.api.tvmaze.common.LinksApi;
import com.ua.yushchenko.api.tvmaze.common.NetworkApi;
import com.ua.yushchenko.api.tvmaze.common.RatingApi;
import com.ua.yushchenko.api.tvmaze.common.ScheduleApi;
import com.ua.yushchenko.api.tvmaze.common.WebChannelApi;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class that represented API Show Details object from TVmaze resources
 *
 * @author romanyushchenko
 * @version 0.1
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ShowDetailsApi {

    int id;
    String url;
    String name;
    String type;
    List<String> genres;
    String status;
    int averageRuntime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date premiered;
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date ended;
    String officialSite;
    ScheduleApi schedule;
    RatingApi rating;
    int weight;
    NetworkApi network;
    WebChannelApi webChannel;
    String dvdCountry;
    ExternalsApi externals;
    ImageApi image;
    String summary;
    long updated;
    @JsonProperty("_links")
    LinksApi links;

}
