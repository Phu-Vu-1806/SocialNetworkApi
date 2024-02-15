package com.springboot.socialnetworkapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExistsLocation extends RuntimeException{
    public ExistsLocation(String message) {
        super(message);
    }
}
