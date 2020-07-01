package com.dwp.usersbycity.exceptions;

public class UnableToAccessExternalApiException extends RuntimeException {

    public UnableToAccessExternalApiException(String errorMessage) {
        super(errorMessage);
    }

}
