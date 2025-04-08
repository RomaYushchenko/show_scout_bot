package com.ua.yushchenko.exceptions.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class AbstractApiException extends RuntimeException {

    private final HttpStatus httpStatus;

    public AbstractApiException(final String message, final HttpStatus httpStatus) {
        super(message);

        this.httpStatus = httpStatus;
    }

}
