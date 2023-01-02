package ru.nsu.fit.pak.budle.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseException {
    private String message;
    private String type;

    public ResponseException(String message, String type) {
        this.message = message;
        this.type = type;
    }

}
