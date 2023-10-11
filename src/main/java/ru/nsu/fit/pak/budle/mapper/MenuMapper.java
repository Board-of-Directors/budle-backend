package ru.nsu.fit.pak.budle.mapper;

import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.budle.dao.establishment.restaurant.MenuCategory;
import ru.nsu.fit.pak.budle.dao.establishment.restaurant.Product;
import ru.nsu.fit.pak.budle.dto.response.establishment.ResponseMenuCategoryDto;
import ru.nsu.fit.pak.budle.dto.response.establishment.ResponseProductDto;

import java.util.List;

@Component
public class MenuMapper {
    public List<ResponseMenuCategoryDto> toDto(List<MenuCategory> categories) {
        return categories.stream()
            .filter(category -> category.getParentCategoryId() == null)
            .map(this::toDto)
            .toList();

    }

    public ResponseMenuCategoryDto toDto(MenuCategory category) {
        return new ResponseMenuCategoryDto()
            .setName(category.getName())
            .setProducts(category.getProducts().stream().map(this::toDto).toList())
            .setChildCategories(category.getChildCategories().stream().map(this::toDto).toList());
    }

    public ResponseProductDto toDto(Product product) {
        return new ResponseProductDto()
            .setPrice(product.getPrice())
            .setName(product.getName())
            .setDescription(product.getDescription())
            .setWeightG(product.getWeightG())
            .setOnSale(product.isOnSale());
    }
}
