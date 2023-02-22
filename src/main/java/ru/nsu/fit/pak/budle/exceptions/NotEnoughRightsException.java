package ru.nsu.fit.pak.budle.exceptions;

public class NotEnoughRightsException extends BaseException {

    public NotEnoughRightsException() {
        super("У вас недостаточно прав для совершения данного действия.", "NotEnoughRights");
    }
}
