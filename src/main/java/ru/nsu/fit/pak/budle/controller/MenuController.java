package ru.nsu.fit.pak.budle.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.pak.budle.dto.response.establishment.ResponseMenuCategoryDto;
import ru.nsu.fit.pak.budle.service.MenuService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/menu")
public class MenuController {
    private final MenuService menuService;

    @GetMapping
    public List<ResponseMenuCategoryDto> get(@RequestParam long establishmentId) {
        return menuService.getMenu(establishmentId);
    }
}
