package ru.nsu.fit.pak.budle.exceptions;

/**
 * Child of base exception.
 * Throw this exception, when user try to provide wrong phone number format.
 */
public class IncorrectPhoneNumberFormatException extends BaseException {
    static final private String ERROR_TYPE = "IncorrectPhoneNumberFormatException";

    public IncorrectPhoneNumberFormatException() {
        super("Номер телефона введен в неверном формате.", ERROR_TYPE);
    }
}
