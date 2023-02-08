package ru.nsu.fit.pak.budle.exceptions;

public class OrderNotFoundException extends BaseException {
    public OrderNotFoundException(String message) {
        super(message, "OrderNotFound");
    }
}
