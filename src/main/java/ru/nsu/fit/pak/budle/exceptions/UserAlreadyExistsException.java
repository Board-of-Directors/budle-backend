package ru.nsu.fit.pak.budle.exceptions;

import lombok.Getter;

/**
 * Child of base exception.
 * Throw it when user try to register with credentials, that already exists in our system.
 * For example, phone number or username.
 */
@Getter
public class UserAlreadyExistsException extends BaseException {
    static final private String ERROR_TYPE = "UserAlreadyExistsException";
    static final private String ERROR_MESSAGE = "Пользователь с такими данными уже существует.";

    /**
     * Default constructor of exception.
     */
    public UserAlreadyExistsException() {
        super(ERROR_MESSAGE, ERROR_TYPE);
    }

}
