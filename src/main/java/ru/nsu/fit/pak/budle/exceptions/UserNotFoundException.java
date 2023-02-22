package ru.nsu.fit.pak.budle.exceptions;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
        super("Пользователя с такими данными не существует", "UserNotFound");
    }
}
