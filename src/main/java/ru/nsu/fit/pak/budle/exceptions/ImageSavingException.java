package ru.nsu.fit.pak.budle.exceptions;

public class ImageSavingException extends BaseException {
    static private final String EXCEPTION_TYPE = "ImageSavingException";
    static private final String EXCEPTION_MESSAGE = "Не получилось сохранить одну или несколько из переданных картинок." +
            "Проверьте корректность введенных данных.";

    public ImageSavingException() {
        super(EXCEPTION_MESSAGE, EXCEPTION_TYPE);
    }
}
