package ru.nsu.fit.pak.budle.exceptions;

import lombok.Getter;

@Getter
public class UserAlreadyExistsException extends BaseException {
    public UserAlreadyExistsException(String message) {
        super(message, "AlreadyExists");
    }

}
