package ru.nsu.fit.pak.budle.dao;

import ru.nsu.fit.pak.budle.exceptions.IncorrectOrderStatusException;

import java.util.Objects;

public enum OrderStatus {
    WAITING(0), ACCEPTED(1), REJECTED(2);

    private final Integer status;

    OrderStatus(int status) {
        this.status = status;
    }

    public static OrderStatus getStatusByInteger(Integer status) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (Objects.equals(orderStatus.status, status)) {
                return orderStatus;
            }
        }
        throw new IncorrectOrderStatusException();
    }

    public Integer getStatus() {
        return status;
    }

}
