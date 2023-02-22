package ru.nsu.fit.pak.budle.dao;

import ru.nsu.fit.pak.budle.exceptions.IncorrectDataException;

public enum Category {
    restaurant("рестораны"),
    hotel("отели"),
    bank("банки"),
    medicine("медицина"),
    government("государственные"),
    cars("машины"),
    entertainment("развлечения"),
    building("строительство");
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
        throw new IncorrectDataException("Such category does not exist");// not found
    }

}