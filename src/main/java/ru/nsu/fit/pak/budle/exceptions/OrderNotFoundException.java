package ru.nsu.fit.pak.budle.exceptions;

public class OrderNotFoundException extends BaseException {
    public OrderNotFoundException(Long id) {
        super(
                "Заказ с id " + id + " не существует.", "OrderNotFound");
    }
}
