package com.dwp.usersbycity.exceptions;

import com.dwp.usersbycity.models.ErrorResponse;
import com.dwp.usersbycity.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerClass {

    private final Logger LOGGER = LoggerFactory.getLogger(User.class);

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleException(Exception e) {
        LOGGER.error(e.getMessage());
        ErrorResponse errorResponse =
                new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), "Internal Server Error");
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }

}
