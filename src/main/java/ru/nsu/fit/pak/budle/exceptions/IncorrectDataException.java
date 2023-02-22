package ru.nsu.fit.pak.budle.exceptions;

import lombok.Getter;

@Getter
public class IncorrectDataException extends BaseException {

    public IncorrectDataException() {
        super("Данные введены неверно", "IncorrectData");
    }
}
