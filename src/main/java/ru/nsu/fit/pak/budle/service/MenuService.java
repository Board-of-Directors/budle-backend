package ru.nsu.fit.pak.budle.service;

import ru.nsu.fit.pak.budle.dto.request.RequestCategoryDto;
import ru.nsu.fit.pak.budle.dto.request.RequestProductDto;
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

    /**
     * Создание категории в меню.
     *
     * @param category создаваемая категория
     */
    void createCategory(RequestCategoryDto category);

    /**
     * Создание продукта в меню.
     *
     * @param product создаваемый продукт
     */
    void createProduct(RequestProductDto product);

    /**
     * Удаление соответсвующей категории.
     *
     * @param categoryId идентификатор категории
     */
    void deleteCategory(long categoryId);

    /**
     * Удаление соответствующего продукта.
     *
     * @param productId идентификатор продукта
     */
    void deleteProduct(long productId);
}
