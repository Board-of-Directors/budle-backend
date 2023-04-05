package ru.nsu.fit.pak.budle.utils;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.fit.pak.budle.exceptions.ResponseException;

/**
 * Base response of our application.
 * Base response contains two fields: result and exception.
 * Both fields are not required, but it must be at least one.
 * Result can be any type and any class.
 * Exception must be instance os response exception, because it contains exception type.
 *
 * @param <T> type, of what type will be response.
 */
@Getter
@Setter
public class BaseResponse<T> {
    private T result;
    private ResponseException exception;

    /**
     * Constructor that contains only result.
     *
     * @param result will be result of any type.
     */
    public BaseResponse(T result) {
        this.result = result;
    }

    /**
     * Constructor that takes message and type, after that created instance of ResponseException.
     *
     * @param message will be message of response exception.
     * @param type    will be type of response exception.
     */
    public BaseResponse(String message, String type) {
        this.exception = new ResponseException(message, type);


    }
}

