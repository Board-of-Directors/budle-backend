package ru.nsu.fit.pak.budle.exceptions;

public class EstablishmentNotFoundException extends BaseException {
    public EstablishmentNotFoundException(String s) {
        super(s, "EstablishmentNotFound");
    }
}
