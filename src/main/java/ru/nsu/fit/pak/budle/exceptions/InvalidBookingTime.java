package ru.nsu.fit.pak.budle.exceptions;

public class InvalidBookingTime extends BaseException {
    public InvalidBookingTime() {
        super("Неверное время для бронирования.", "InvalidBookingTimeException");
    }
}
