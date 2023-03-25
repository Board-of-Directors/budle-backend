package ru.nsu.fit.pak.budle.dao;

import ru.nsu.fit.pak.budle.exceptions.IncorrectDataException;

public enum DayOfWeek {
    Monday("Пн"),
    Tuesday("Вт"),
    Wednesday("Ср"),
    Thursday("Чт"),
    Friday("Пт"),
    Saturday("Сб"),
    Sunday("Вс");

    private final String translate;

    DayOfWeek(String translate) {
        this.translate = translate;
    }

    static public DayOfWeek getDayByString(String day) {
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            if (dayOfWeek.translate.equals(day)) {
                return dayOfWeek;
            }
        }
        throw new IncorrectDataException();
    }

    public String getTranslate() {
        return translate;
    }
}
