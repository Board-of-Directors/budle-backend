package ru.nsu.fit.pak.budle.exceptions;

public class EstablishmentNotFoundException extends BaseException {

    public EstablishmentNotFoundException(Long id) {
        super("Заведения с id " + id + " не существует", "EstablishmentNotFound");
    }
}
