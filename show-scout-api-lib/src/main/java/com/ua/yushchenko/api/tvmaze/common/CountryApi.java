package com.ua.yushchenko.api.tvmaze.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class that represented API Country object from TVmaze resources
 *
 * @author romanyushchenko
 * @version 0.1
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CountryApi {

    String name;
    String code;
    String timezone;
}
