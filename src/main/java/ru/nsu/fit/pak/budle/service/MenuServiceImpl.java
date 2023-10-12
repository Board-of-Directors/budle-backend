package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dao.Category;
import ru.nsu.fit.pak.budle.dao.establishment.restaurant.Restaurant;
import ru.nsu.fit.pak.budle.dto.request.RequestCategoryDto;
import ru.nsu.fit.pak.budle.dto.request.RequestProductDto;
import ru.nsu.fit.pak.budle.dto.response.ShortResponseMenuCategoryDto;
import ru.nsu.fit.pak.budle.dto.response.establishment.ResponseMenuCategoryDto;
import ru.nsu.fit.pak.budle.exceptions.EstablishmentNotFoundException;
import ru.nsu.fit.pak.budle.mapper.MenuMapper;
import ru.nsu.fit.pak.budle.repository.EstablishmentRepository;
import ru.nsu.fit.pak.budle.repository.MenuRepository;
import ru.nsu.fit.pak.budle.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final EstablishmentRepository establishmentRepository;
    private final EstablishmentService establishmentService;
    private final MenuMapper menuMapper;
    private final MenuRepository menuRepository;
    private final ProductRepository productRepository;

    @Override
    public List<ResponseMenuCategoryDto> getMenu(long establishmentId) {
        Restaurant restaurant =
            (Restaurant) establishmentRepository.findByCategoryAndId(Category.restaurant, establishmentId)
                .orElseThrow(() -> new EstablishmentNotFoundException(establishmentId));

        return menuMapper.toDto(restaurant.getCategories());
    }

    @Override
    public void createCategory(RequestCategoryDto category) {
        menuRepository.save(
            menuMapper.toModel(
                category,
                establishmentService.getEstablishmentById(category.getEstablishmentId())
            )
        );
    }

    @Override
    public void createProduct(RequestProductDto product) {
        productRepository.save(menuMapper.toModel(product, menuRepository.findById(product.getCategoryId()).orElseThrow()));
    }

    @Override
    public void deleteCategory(long categoryId) {
        menuRepository.deleteById(categoryId);
    }

    @Override
    public void deleteProduct(long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public List<ShortResponseMenuCategoryDto> getShortMenu(long establishmentId) {
        Restaurant restaurant =
            (Restaurant) establishmentRepository.findByCategoryAndId(Category.restaurant, establishmentId)
                .orElseThrow(() -> new EstablishmentNotFoundException(establishmentId));

        return menuMapper.toShortDto(restaurant.getCategories(), establishmentId);
    }


}
