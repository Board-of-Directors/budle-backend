package ru.nsu.fit.pak.budle.exceptions;

/**
 * Child of base exception.
 * Throw it when user try to access order, that does not exist in our system.
 */
public class OrderNotFoundException extends BaseException {
    static final private String ERROR_TYPE = "OrderNotFoundException";

    /**
     * Default constructor of exception.
     *
     * @param id - id of order that user try to access.
     */
    public OrderNotFoundException(Long id) {
        super(
                "Заказ с id " + id + " не существует.", ERROR_TYPE);
    }
}
