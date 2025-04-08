package com.ua.yushchenko.exceptions.model;

import org.springframework.http.HttpStatus;

public class ShowScoutNotFoundException extends AbstractApiException {

    public ShowScoutNotFoundException(final String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public ShowScoutNotFoundException(final String message, final String ... args) {
        super(String.format(message, args), HttpStatus.NOT_FOUND);
    }
}
