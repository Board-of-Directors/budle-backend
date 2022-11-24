package ru.nsu.fit.pak.Budle.Exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseException extends RuntimeException{
    String type;

    public ResponseException(String message, String type){
        super(message);
        this.type = type;
    }
}
