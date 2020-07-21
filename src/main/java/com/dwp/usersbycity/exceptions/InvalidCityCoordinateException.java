package com.dwp.usersbycity.exceptions;

public class InvalidCityCoordinateException extends  RuntimeException {
    public InvalidCityCoordinateException(String errorMessage) {
        super(errorMessage);
    }
}
