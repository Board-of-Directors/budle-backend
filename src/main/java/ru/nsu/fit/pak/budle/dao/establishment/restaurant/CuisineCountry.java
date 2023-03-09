package ru.nsu.fit.pak.budle.dao.establishment.restaurant;

import ru.nsu.fit.pak.budle.exceptions.IncorrectDataException;

public enum CuisineCountry {
    european("Европейская"),
    georgian("Грузинская"),
    asian("Азиатская"),
    russian("Русская"),
    vietnamese("Вьетнамская");


    private final String value;

    CuisineCountry(String value) {
        this.value = value;
    }

    static public CuisineCountry getEnumByValue(String value) {
        for (CuisineCountry e : CuisineCountry.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        throw new IncorrectDataException();
    }
}
