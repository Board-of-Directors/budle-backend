package ru.nsu.fit.pak.budle.exceptions;

/**
 * Child of base exception.
 * Throw it when user try to act as user, that does not exist in our system.
 */
public class UserNotFoundException extends BaseException {
    static final private String ERROR_TYPE = "UserNotFoundException";
    static final private String ERROR_MESSAGE = "Пользователя с такими данными не существует";

    /**
     * Default constructor of the exception.
     */
    public UserNotFoundException() {
        super(ERROR_MESSAGE, ERROR_TYPE);
    }
}
