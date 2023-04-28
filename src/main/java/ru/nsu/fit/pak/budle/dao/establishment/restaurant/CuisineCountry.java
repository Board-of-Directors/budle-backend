package ru.nsu.fit.pak.budle.dao.establishment.restaurant;

import ru.nsu.fit.pak.budle.exceptions.IncorrectDataException;

import java.util.Arrays;
import java.util.List;

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

    public static List<String> getVariants() {
        return Arrays.stream(CuisineCountry.values())
                .map(CuisineCountry::getValue)
                .toList();
    }

    public String getValue() {
        return value;
    }
}
