package ru.nsu.fit.pak.budle.service;

import ru.nsu.fit.pak.budle.dto.response.establishment.ResponseMenuCategoryDto;

import java.util.List;

public interface MenuService {
    /**
     * Получение меню заведения.
     *
     * @param establishmentId идентификатор заведения для получения меню
     * @return список категорий меню
     */
    List<ResponseMenuCategoryDto> getMenu(long establishmentId);
}
