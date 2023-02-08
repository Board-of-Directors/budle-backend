package ru.nsu.fit.pak.budle.exceptions;

public class EstablishmentAlreadyExistsException extends BaseException {
    public EstablishmentAlreadyExistsException(String message) {
        super(message, "EstablishmentAlreadyExists");
    }
}
