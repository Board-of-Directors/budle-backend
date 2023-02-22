package ru.nsu.fit.pak.budle.exceptions;

import lombok.Getter;

@Getter
public class UserAlreadyExistsException extends BaseException {
    public UserAlreadyExistsException() {
        super("Пользователь с такими данными уже существует.", "AlreadyExists");
    }

}
