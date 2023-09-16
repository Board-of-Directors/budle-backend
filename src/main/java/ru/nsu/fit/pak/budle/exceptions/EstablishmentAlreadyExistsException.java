package ru.nsu.fit.pak.budle.exceptions;

/**
 * Child of base exception.
 * Throw this exception, when user try to create establishment that already exist.
 */
public class EstablishmentAlreadyExistsException extends BaseException {
    static final private String ERROR_TYPE = "EstablishmentAlreadyExists";

    /**
     * Default constructor.
     *
     * @param name    name of the establishment.
     * @param address address of the establishment.
     */
    public EstablishmentAlreadyExistsException(String name, String address) {
        super("Заведение с названием " +
            name + " и адресом " +
            address + " уже существует", ERROR_TYPE);

    }
}
