package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dao.Category;
import ru.nsu.fit.pak.budle.dao.establishment.restaurant.Restaurant;
import ru.nsu.fit.pak.budle.dto.response.establishment.ResponseMenuCategoryDto;
import ru.nsu.fit.pak.budle.exceptions.EstablishmentNotFoundException;
import ru.nsu.fit.pak.budle.mapper.MenuMapper;
import ru.nsu.fit.pak.budle.repository.EstablishmentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final EstablishmentRepository establishmentRepository;
    private final MenuMapper menuMapper;

    @Override
    public List<ResponseMenuCategoryDto> getMenu(long establishmentId) {
        Restaurant restaurant =
            (Restaurant) establishmentRepository.findByCategoryAndId(Category.restaurant, establishmentId)
                .orElseThrow(() -> new EstablishmentNotFoundException(establishmentId));

        return menuMapper.toDto(restaurant.getCategories());
    }
}
