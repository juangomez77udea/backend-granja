package com.granja.fg.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ValueDontFindException extends RuntimeException{
    public ValueDontFindException(String message){
        super(message);
    }
}
