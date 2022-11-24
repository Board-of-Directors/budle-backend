package ru.nsu.fit.pak.Budle.Exceptions;

import lombok.Getter;

@Getter
public class IncorrectDataException extends BaseException {

    public IncorrectDataException(String message) {
        super(message, "IncorrectData");
    }
}
