package ru.nsu.fit.pak.Budle;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.fit.pak.Budle.Exceptions.ResponseException;
import ru.nsu.fit.pak.Budle.Exceptions.ResponseException2;

@Getter
@Setter
public class BaseResponse<T> {
    Boolean success;
    T result;
    ResponseException2 exception;



    public BaseResponse(T result){
        this.success = true;
        this.result = result;
    }

    public BaseResponse(String message, String type) {
        this.success = false;
        this.exception = new ResponseException2(message,type);


    }
}

