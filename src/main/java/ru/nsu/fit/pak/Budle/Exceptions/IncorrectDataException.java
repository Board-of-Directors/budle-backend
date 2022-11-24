package ru.nsu.fit.pak.Budle.Exceptions;

import lombok.Getter;
import org.apache.catalina.connector.Response;

@Getter
public class IncorrectDataException extends ResponseException {

    public IncorrectDataException(String message) {
        super(message, "IncorrectData");
    }
}
