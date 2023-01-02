package ru.nsu.fit.pak.budle.exceptions;

import lombok.Getter;

@Getter
public class IncorrectDataException extends BaseException {

    public IncorrectDataException(String message) {
        super(message, "IncorrectData");
    }
}
