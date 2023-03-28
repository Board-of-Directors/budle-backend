package ru.nsu.fit.pak.budle.dao;

import ru.nsu.fit.pak.budle.exceptions.IncorrectDataException;

public enum Category {
    restaurant("Рестораны"),
    hotel("Отели"),
    bank("Банки"),
    medicine("Медицинские"),
    government("Государственные"),
    cars("Автомобильные"),
    entertainment("Развлекательный"),
    building("Строительные"),
    game_club("Игровые клубы"),
    barbershop("Парикмахерская");
    public final String value;

    Category(String value) {
        this.value = value;
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