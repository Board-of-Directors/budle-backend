package ru.nsu.fit.pak.budle.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.pak.budle.dto.request.RequestCategoryDto;
import ru.nsu.fit.pak.budle.dto.request.RequestProductDto;
import ru.nsu.fit.pak.budle.dto.response.ShortResponseMenuCategoryDto;
import ru.nsu.fit.pak.budle.dto.response.establishment.ResponseMenuCategoryDto;
import ru.nsu.fit.pak.budle.service.MenuService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/menu")
public class MenuController {
    private final MenuService menuService;

    @GetMapping()
    @CrossOrigin
    public List<ShortResponseMenuCategoryDto> getShort(@RequestParam long establishmentId) {
        return menuService.getShortMenu(establishmentId);
    }
    @GetMapping
    @CrossOrigin
    public List<ResponseMenuCategoryDto> get(@RequestParam long establishmentId) {
        return menuService.getMenu(establishmentId);
    }

    @PostMapping
    @CrossOrigin
    public void add(@RequestBody RequestCategoryDto category) {
        menuService.createCategory(category);
    }

    @PostMapping(value = "/product")
    @CrossOrigin
    public void addProduct(@RequestBody RequestProductDto product) {
        menuService.createProduct(product);
    }

    @DeleteMapping
    @CrossOrigin
    public void deleteCategory(@RequestParam long categoryId) {
        menuService.deleteCategory(categoryId);
    }

    @DeleteMapping(value = "/product")
    @CrossOrigin
    public void deleteProduct(@RequestParam long productId) {
        menuService.deleteProduct(productId);
    }
}
