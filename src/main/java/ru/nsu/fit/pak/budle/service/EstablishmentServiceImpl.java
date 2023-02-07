package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dao.Category;
import ru.nsu.fit.pak.budle.dao.Establishment;
import ru.nsu.fit.pak.budle.dto.CategoryDto;
import ru.nsu.fit.pak.budle.dto.EstablishmentDto;
import ru.nsu.fit.pak.budle.mapper.EstablishmentMapper;
import ru.nsu.fit.pak.budle.repository.EstablishmentRepository;
import ru.nsu.fit.pak.budle.repository.UserRepository;
import ru.nsu.fit.pak.budle.utils.ImageWorker;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstablishmentServiceImpl implements EstablishmentService {
    private final EstablishmentRepository establishmentRepository;

    private final EstablishmentMapper establishmentMapper;

    private final UserRepository userRepository;


    public List<EstablishmentDto> getEstablishmentByParams(String category, Boolean hasMap, Boolean hasCardPayment, Pageable page) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
        Category categoryEnum = null;
        if (category != null) {
            categoryEnum = Category.valueOf(category);
        }
        Example<Establishment> exampleQuery = Example.of(new Establishment(categoryEnum, hasMap, hasCardPayment), matcher);
        Page<Establishment> results = establishmentRepository.findAll(exampleQuery, page);
        return establishmentMapper.modelListToDtoList(results);
    }

    // TODO: Удалить пользователя из этой части кода, проверка на name+address
    public void createEstablishment(EstablishmentDto dto) {
        Establishment establishment = establishmentMapper.dtoToModel(dto);
        ImageWorker imageWorker = new ImageWorker();
        establishment.setImage(imageWorker.saveImage(establishment));
        establishment.setOwner(userRepository.getReferenceById(1L));
        establishmentRepository.save(establishment);
    }

    public List<CategoryDto> getCategories() {
        return Arrays.stream(Category.values()).map(CategoryDto::new).toList();
    }
}
