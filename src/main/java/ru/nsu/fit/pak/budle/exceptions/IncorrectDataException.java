package ru.nsu.fit.pak.budle.exceptions;

import lombok.Getter;

/**
 * Child of base exception.
 * Throw this exception, if you cannot find specified exception for your occasion.
 * During the refactoring process, all of those exception replaces with new, more detailed exceptions.
 */
@Getter
public class IncorrectDataException extends BaseException {
    static final private String ERROR_TYPE = "IncorrectData";
    static final private String ERROR_MESSAGE = "Данные введены неверно.";

    /**
     * Default constructor of the exception.
     */
    public IncorrectDataException() {
        super(ERROR_MESSAGE, ERROR_TYPE);
    }
}
