package ru.nsu.fit.pak.budle.exceptions;

/**
 * Child of base exception.
 * Throw it when user try to do operation, which is locked for one.
 */
public class NotEnoughRightsException extends BaseException {
    static final private String ERROR_TYPE = "NotEnoughRights";

    /**
     * Default constructor of the exception.
     */
    public NotEnoughRightsException() {
        super("У вас недостаточно прав для совершения данного действия.", ERROR_TYPE);
    }
}
