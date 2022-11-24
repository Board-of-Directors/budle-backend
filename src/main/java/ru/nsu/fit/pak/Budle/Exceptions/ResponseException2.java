package ru.nsu.fit.pak.Budle.Exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseException2 {
    private String message;
    private String type;

    public ResponseException2(String message, String type) {
        this.message = message;
        this.type = type;
    }

}
