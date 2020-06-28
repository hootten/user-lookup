package com.dwp.usersbycity.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class UsersNotFoundException extends RuntimeException {

    public UsersNotFoundException(String exception) {
        super(exception);
    }

}
