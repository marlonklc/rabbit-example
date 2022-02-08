package com.asyncexample.exception;

import org.springframework.http.HttpStatus;

public class Error {

    private int status;
    private String message;

    public Error(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
