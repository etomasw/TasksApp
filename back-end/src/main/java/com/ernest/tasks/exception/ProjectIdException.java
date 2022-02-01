package com.ernest.tasks.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class ProjectIdException extends RuntimeException{

    public ProjectIdException(String message) {
        super(message);
    }
}
