package ru.nsu.fit.pak.budle.exceptions;

public class VerificationCodeWasFalseException extends BaseException {
    private final static String ERROR_TYPE = "VerificationCodeWasFalseException";
    private final static String ERROR_MESSAGE =
        "Код, который был введен, оказался неправильным. Попробуйте ввести его еще раз.";

    public VerificationCodeWasFalseException() {
        super(ERROR_MESSAGE, ERROR_TYPE);
    }
}
