package ru.nsu.fit.pak.budle.exceptions;

public class ImageLoadingException extends BaseException {
    static private final String ERROR_MESSAGE =
        "Не получилось загрузить одну или несколько фотографий из базы. Проверьте корректность данных";
    static private final String ERROR_TYPE = "ImageLoadingException";

    public ImageLoadingException() {
        super(ERROR_MESSAGE, ERROR_TYPE);
    }
}
