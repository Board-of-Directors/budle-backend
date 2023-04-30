package ru.nsu.fit.pak.budle.exceptions;

public class IncorrectEstablishmentType extends BaseException {
    public IncorrectEstablishmentType() {
        super("Данного типа заведения не существует", "IncorrectEstablishmentType");
    }
}
