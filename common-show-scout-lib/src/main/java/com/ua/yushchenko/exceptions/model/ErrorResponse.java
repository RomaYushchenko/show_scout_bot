package com.ua.yushchenko.exceptions.model;

import org.springframework.http.HttpStatus;

public record ErrorResponse(HttpStatus status, String message) {

}
