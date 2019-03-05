package com.holidu.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TreeNotFoundException extends Exception{
    private static final long serialVersionUID = 1L;
    public TreeNotFoundException(String message){
        super(message);
    }
}