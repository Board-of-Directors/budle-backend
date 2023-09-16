package ru.nsu.fit.pak.budle.exceptions;

import ru.nsu.fit.pak.budle.dao.Category;

public class IncorrectCategoryException extends BaseException {
    static private final String EXCEPTION_TYPE = "IncorrectCategoryException";
    static private final String EXCEPTION_MESSAGE =
        "Неверно указана категория. Попробуйте ввести категорию из предложенных" + Category.getCategories();

    public IncorrectCategoryException() {
        super(EXCEPTION_MESSAGE, EXCEPTION_TYPE);
    }
}
