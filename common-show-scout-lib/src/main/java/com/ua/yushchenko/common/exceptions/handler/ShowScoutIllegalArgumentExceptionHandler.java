package com.ua.yushchenko.common.exceptions.handler;

import com.ua.yushchenko.common.exceptions.model.ErrorResponse;
import com.ua.yushchenko.common.exceptions.model.ShowScoutIllegalArgumentException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handled ShowScoutIllegalArgumentException and maps on HTTP 400 error
 *
 * @author romanyushchenko
 * @version 0.1
 */
@RestControllerAdvice
public class ShowScoutIllegalArgumentExceptionHandler {

    @ExceptionHandler(ShowScoutIllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(final ShowScoutIllegalArgumentException ex) {
        return ResponseEntity.status(ex.getHttpStatus())
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new ErrorResponse(ex.getHttpStatus(), ex.getMessage()));
    }
}
