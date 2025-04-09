package com.ua.yushchenko.exceptions.model;

import org.springframework.http.HttpStatus;

/**
 * Defines model for response exception
 *
 * @author romanyushchenko
 * @version 0.1
 */
public record ErrorResponse(HttpStatus status, String message) {

}
