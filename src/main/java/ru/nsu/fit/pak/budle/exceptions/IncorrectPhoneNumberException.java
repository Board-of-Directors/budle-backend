package ru.nsu.fit.pak.budle.exceptions;

public class IncorrectPhoneNumberException extends BaseException {
    public IncorrectPhoneNumberException() {
        super("Номер телефона введен в неверном формате.", "IncorrectPhoneNumberException");
    }
}
