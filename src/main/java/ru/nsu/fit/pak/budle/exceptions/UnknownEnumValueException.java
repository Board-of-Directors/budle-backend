package ru.nsu.fit.pak.budle.exceptions;

public class UnknownEnumValueException extends BaseException {
    public UnknownEnumValueException(String message, String type) {
        super(message, "UnknownEnumValueException");
    }
}
