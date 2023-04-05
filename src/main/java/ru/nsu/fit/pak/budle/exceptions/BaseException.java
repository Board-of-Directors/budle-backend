package ru.nsu.fit.pak.budle.exceptions;

import lombok.Getter;
import lombok.Setter;

/**
 * Class, that represent base exception in our system.
 * Modified version of runtime exception.
 * Additional field - exception type.
 * Exception type - is the short name of exception.
 * For example: InvalidDataException
 */
@Getter
@Setter
public class BaseException extends RuntimeException {
    private String type;

    public BaseException(String message, String type) {
        super(message);
        this.type = type;
    }
}
