package ru.nsu.fit.pak.budle.exceptions;

public class NotEnoughRightsException extends BaseException {

    public NotEnoughRightsException(String message) {
        super(message, "NotEnoughRights");
    }
}
