package ru.nsu.fit.pak.budle.dao;

import ru.nsu.fit.pak.budle.exceptions.IncorrectDataException;

public enum DayOfWeek {
    Monday("Пн", "пн"),
    Tuesday("Вт", "вт"),
    Wednesday("Ср", "ср"),
    Thursday("Чт", "чт"),
    Friday("Пт", "пт"),
    Saturday("Сб", "сб"),
    Sunday("Вс", "вс");

    private final String translate;
    private final String translateLittle;

    DayOfWeek(String translate, String translateLittle) {

        this.translate = translate;
        this.translateLittle = translateLittle;
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

    public String getTranslateLittle() {
        return translateLittle;
    }
}
