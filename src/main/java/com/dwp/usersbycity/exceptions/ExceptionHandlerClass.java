package com.dwp.usersbycity.exceptions;

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

    @ExceptionHandler(UsersNotFoundException.class)
    public final ResponseEntity<Object> handleUsersNotFoundException(Exception e) {
        LOGGER.info(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleException(Exception e) {
        LOGGER.error(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }

}
