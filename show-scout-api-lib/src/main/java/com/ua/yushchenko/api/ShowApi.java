package com.ua.yushchenko.api;

import com.ua.yushchenko.enums.Genre;
import com.ua.yushchenko.enums.ShowStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
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
