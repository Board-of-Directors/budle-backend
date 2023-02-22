package ru.nsu.fit.pak.budle.exceptions;

public class EstablishmentAlreadyExistsException extends BaseException {
    static final private String MESSAGE_TYPE = "EstablishmentAlreadyExists";
    

    public EstablishmentAlreadyExistsException(String name, String address) {
        super("Establishment with name " + name + " and address " + address + " already exists", MESSAGE_TYPE);

    }
}
