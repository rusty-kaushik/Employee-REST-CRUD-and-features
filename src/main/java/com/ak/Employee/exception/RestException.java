package com.ak.Employee.exception;

import org.springframework.http.HttpStatus;

public class RestException {

    private final String message;
    private final Throwable throwable;
    private final HttpStatus httpStatus;

    public RestException(String message, Throwable throwable, HttpStatus httpStatus) {
        this.message = message;
        this.throwable = throwable;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
