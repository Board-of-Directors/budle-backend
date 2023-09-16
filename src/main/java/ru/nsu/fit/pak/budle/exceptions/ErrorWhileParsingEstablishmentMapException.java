package ru.nsu.fit.pak.budle.exceptions;

public class ErrorWhileParsingEstablishmentMapException extends BaseException {
    public ErrorWhileParsingEstablishmentMapException() {
        super("В ходе считывания карты произошла ошибка", "ErrorWhileParsingEstablishmentMapException");
    }
}
