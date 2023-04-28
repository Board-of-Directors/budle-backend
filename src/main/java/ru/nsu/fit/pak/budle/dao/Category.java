package ru.nsu.fit.pak.budle.dao;

import ru.nsu.fit.pak.budle.dao.establishment.hotel.HotelStars;
import ru.nsu.fit.pak.budle.dao.establishment.restaurant.CuisineCountry;
import ru.nsu.fit.pak.budle.exceptions.IncorrectDataException;

import java.util.Collections;
import java.util.List;

public enum Category {
    restaurant("Рестораны", CuisineCountry.getVariants()),
    hotel("Отели", HotelStars.getVariants()),
    game_club("Игровые клубы", Collections.emptyList()),
    barbershop("Парикмахерские", Collections.emptyList());


    public final String value;

    public final List<String> variants;

    Category(String value, List<String> variants) {
        this.value = value;
        this.variants = variants;
    }

    static public Category getEnumByValue(String value) {
        for (Category e : Category.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        throw new IncorrectDataException();
    }


}