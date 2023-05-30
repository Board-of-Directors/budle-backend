package ru.nsu.fit.pak.budle.dao;

import ru.nsu.fit.pak.budle.dao.establishment.hotel.HotelStars;
import ru.nsu.fit.pak.budle.dao.establishment.restaurant.CuisineCountry;
import ru.nsu.fit.pak.budle.dto.response.ResponseSubcategoryDto;
import ru.nsu.fit.pak.budle.exceptions.IncorrectCategoryException;

import java.util.Arrays;

public enum Category {
    restaurant("Рестораны", new ResponseSubcategoryDto(CuisineCountry.getVariants(), "Тип кухни", "cuisineCountry")),
    hotel("Отели", new ResponseSubcategoryDto(HotelStars.getVariants(), "Количество звезд", "starsCount")),
    game_club("Игровые клубы", new ResponseSubcategoryDto()),
    barbershop("Парикмахерские", new ResponseSubcategoryDto());


    public final String value;

    public final ResponseSubcategoryDto variants;

    Category(String value, ResponseSubcategoryDto variants) {
        this.value = value;
        this.variants = variants;
    }

    static public Category getEnumByValue(String value) {
        for (Category e : Category.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        throw new IncorrectCategoryException();
    }

    static public String getCategories() {
        return Arrays.stream(Category.values()).map(x -> x.value).reduce(":", (str, acc) -> acc + "," + str);
    }

    public String getValue() {
        return value;
    }


}