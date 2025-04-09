package com.ua.yushchenko.exceptions.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Defines base Exception for all Exceptions
 *
 * @author romanyushchenko
 * @version 0.1
 */
@Getter
public abstract class AbstractApiException extends RuntimeException {

    private final HttpStatus httpStatus;

    public AbstractApiException(final String message, final HttpStatus httpStatus) {
        super(message);

        this.httpStatus = httpStatus;
    }

}
