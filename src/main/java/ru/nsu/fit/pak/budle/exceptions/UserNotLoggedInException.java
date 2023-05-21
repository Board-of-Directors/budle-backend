package ru.nsu.fit.pak.budle.exceptions;

public class UserNotLoggedInException extends BaseException {
    public UserNotLoggedInException() {
        super("Юзер не вошел в аккаунт.", "UserNotLoggedInException");
    }
}
