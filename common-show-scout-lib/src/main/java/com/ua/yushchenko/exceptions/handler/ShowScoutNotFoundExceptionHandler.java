package com.ua.yushchenko.exceptions.handler;

import com.ua.yushchenko.exceptions.model.ErrorResponse;
import com.ua.yushchenko.exceptions.model.ShowScoutNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handled ShowScoutNotFoundException and maps on HTTP 404 error
 *
 * @author romanyushchenko
 * @version 0.1
 */
@RestControllerAdvice
public class ShowScoutNotFoundExceptionHandler {

    @ExceptionHandler(ShowScoutNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(final ShowScoutNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(new ErrorResponse(ex.getHttpStatus(), ex.getMessage()));
    }
}
