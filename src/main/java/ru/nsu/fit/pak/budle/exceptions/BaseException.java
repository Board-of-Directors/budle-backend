package ru.nsu.fit.pak.budle.exceptions;

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
