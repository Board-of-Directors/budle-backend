package ru.nsu.fit.pak.budle.exceptions;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException(String message) {
        super(message, "UserNotFound");
    }
}
