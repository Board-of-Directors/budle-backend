package ru.nsu.fit.pak.budle.dao.establishment.hotel;

import java.util.List;
import java.util.stream.Stream;

public enum HotelStars {
    one, two, three, four, five;

    public static List<String> getVariants() {
        return Stream
                .iterate(1, i -> i + 1)
                .limit(5)
                .map(Object::toString)
                .toList();
    }
}
