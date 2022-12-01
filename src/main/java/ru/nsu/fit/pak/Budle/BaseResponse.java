package ru.nsu.fit.pak.Budle;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.fit.pak.Budle.Exceptions.ResponseException;

@Getter
@Setter
public class BaseResponse<T> {
    private T result;
    private ResponseException exception;


    public BaseResponse(T result) {
        this.result = result;
    }

    public BaseResponse(String message, String type) {
        this.exception = new ResponseException(message, type);


    }
}

