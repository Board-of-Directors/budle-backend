package ru.nsu.fit.pak.budle.dao;

import ru.nsu.fit.pak.budle.exceptions.IncorrectDataException;

public enum DayOfWeek {
    Monday("Пн", "пн", 1),
    Tuesday("Вт", "вт", 2),
    Wednesday("Ср", "ср", 3),
    Thursday("Чт", "чт", 4),
    Friday("Пт", "пт", 5),
    Saturday("Сб", "сб", 6),
    Sunday("Вс", "вс", 7);

    private final String translate;
    private final String translateLittle;

    private final Integer ordinal;

    DayOfWeek(String translate, String translateLittle, Integer ordinal) {

        this.translate = translate;
        this.translateLittle = translateLittle;
        this.ordinal = ordinal;
    }

    static public DayOfWeek getDayByString(String day) {
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            if (dayOfWeek.translate.equals(day)) {
                return dayOfWeek;
            }
        }
        throw new IncorrectDataException();
    }

    static public DayOfWeek geyDayByLittleString(String day) {
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            if (dayOfWeek.translateLittle.equals(day)) {
                return dayOfWeek;
            }
        }
        throw new IncorrectDataException();
    }

    static public DayOfWeek getDayByOrdinal(Integer number) {
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            if (dayOfWeek.ordinal.equals(number)) {
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
