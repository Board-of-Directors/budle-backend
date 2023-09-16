package ru.nsu.fit.pak.budle.exceptions;

/**
 * Child of base exception.
 * Throw it when user try to access worker, that does not exist in our system.
 */
public class WorkerNotFoundException extends BaseException {
    static final private String ERROR_TYPE = "WorkerNotFoundException";

    /**
     * Default constructor of the exception.
     *
     * @param id - id of the worker that does not exist.
     */
    public WorkerNotFoundException(Long id) {
        super("Работник с id " + id + " не находится в данном заведении или не существует.", ERROR_TYPE);
    }
}
