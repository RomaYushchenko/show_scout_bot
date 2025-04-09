package com.ua.yushchenko.exceptions.model;

import org.springframework.http.HttpStatus;

/**
 * Defines error occurs if entity has illegal argument
 *
 * @author romanyushchenko
 * @version 0.1
 */
public class ShowScoutIllegalArgumentException extends AbstractApiException {

    public ShowScoutIllegalArgumentException(final String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public ShowScoutIllegalArgumentException(final String message, final Object ... args) {
        super(String.format(message, args), HttpStatus.BAD_REQUEST);
    }
}
