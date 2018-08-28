package com.wenbin.polls.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Wenbin Luo @ Aalto University
 */

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AppException extends RuntimeException {
    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message,cause);
    }
}
