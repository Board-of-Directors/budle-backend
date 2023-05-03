package ru.nsu.fit.pak.budle.exceptions;

public class IncorrectOrderStatusException extends BaseException {

    public IncorrectOrderStatusException() {
        super("Был передан неправильный статус заказа", "IncorrectOrderStatusException");
    }
}
