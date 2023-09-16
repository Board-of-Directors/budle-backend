package ru.nsu.fit.pak.budle.exceptions;

public class EstablishmentMapDoesntExistException extends BaseException {
    public EstablishmentMapDoesntExistException() {
        super(
            "Карта данного заведения не существует. Возможно, создатель заведения еще не добавил ее",
            "EstablishmentMapDoesntExistException"
        );
    }
}
