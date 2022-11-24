package ru.nsu.fit.pak.Budle.Exceptions;

import lombok.Getter;

@Getter
public class UserAlreadyExistsException extends BaseException {
    public UserAlreadyExistsException(String message) {
        super(message, "AlreadyExists");
    }

}
