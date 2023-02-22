package ru.nsu.fit.pak.budle.exceptions;

public class EstablishmentAlreadyExistsException extends BaseException {
    static final private String MESSAGE_TYPE = "EstablishmentAlreadyExists";


    public EstablishmentAlreadyExistsException(String name, String address) {
        super("Заведение с названием " + name + " и адресом " + address + " уже существует", MESSAGE_TYPE);

    }
}
