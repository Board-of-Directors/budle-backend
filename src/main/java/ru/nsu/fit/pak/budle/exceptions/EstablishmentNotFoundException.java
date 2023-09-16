package ru.nsu.fit.pak.budle.exceptions;

/**
 * Child of base exception.
 * Throw this exception when user try to find information about non-existed establishment.
 */
public class EstablishmentNotFoundException extends BaseException {
    static final private String ERROR_TYPE = "EstablishmentNotFound";

    /**
     * Default constructor of this exception.
     *
     * @param id - id of non-existed establishment.
     */
    public EstablishmentNotFoundException(Long id) {
        super("Заведения с id " + id + " не существует", ERROR_TYPE);
    }
}
