package ru.nsu.fit.pak.budle.exceptions;

import ru.nsu.fit.pak.budle.dao.establishment.restaurant.CuisineCountry;

public class IncorrectCuisineCountryException extends BaseException {
    static private final String EXCEPTION_TYPE = "IncorrectCuisineCountryException";
    static private final String EXCEPTION_MESSAGE = "Не существует кухни с таким названием." +
            "Попробуйте один из вариантов: " + CuisineCountry.getVariants()
            .stream()
            .reduce(" ", (acc, src) -> acc + src);

    public IncorrectCuisineCountryException() {
        super(EXCEPTION_MESSAGE, EXCEPTION_TYPE);
    }
}
