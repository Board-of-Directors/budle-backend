package ru.nsu.fit.pak.Budle.Exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends RuntimeException{
    String type;

    public BaseException(String message, String type){
        super(message);
        this.type = type;
    }
}
