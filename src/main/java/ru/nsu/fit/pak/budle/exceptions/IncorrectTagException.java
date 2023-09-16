package ru.nsu.fit.pak.budle.exceptions;

import ru.nsu.fit.pak.budle.dao.Tag;

public class IncorrectTagException extends BaseException {
    static private final String EXCEPTION_TYPE = "IncorrectTagException";
    static private final String EXCEPTION_MESSAGE =
        "Тэг заведения выбран неверно. Попробуйте выбрать один из вариантов." + Tag.getTags();

    public IncorrectTagException() {
        super(EXCEPTION_MESSAGE, EXCEPTION_TYPE);
    }
}
