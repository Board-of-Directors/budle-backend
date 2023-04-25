package ru.nsu.fit.pak.budle.exceptions;

public class SpotNotFoundException extends BaseException {
    private final static String ERROR_TYPE = "SpotNotFoundException";

    public SpotNotFoundException(Long spotId) {
        super("Место с id " + spotId + " не было найдено в этом заведении.", ERROR_TYPE);
    }
}
