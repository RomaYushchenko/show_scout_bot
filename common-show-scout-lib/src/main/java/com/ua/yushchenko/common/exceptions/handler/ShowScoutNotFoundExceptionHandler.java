package com.ua.yushchenko.common.exceptions.handler;

import com.ua.yushchenko.common.exceptions.model.ErrorResponse;
import com.ua.yushchenko.common.exceptions.model.ShowScoutNotFoundException;
import org.springframework.http.MediaType;
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
    public ResponseEntity<ErrorResponse> handleNotFound(final ShowScoutNotFoundException ex) {
        return ResponseEntity.status(ex.getHttpStatus())
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new ErrorResponse(ex.getHttpStatus(), ex.getMessage()));
    }
}
