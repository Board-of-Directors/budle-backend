package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dao.Category;
import ru.nsu.fit.pak.budle.dao.Establishment;
import ru.nsu.fit.pak.budle.dto.EstablishmentDto;
import ru.nsu.fit.pak.budle.mapper.EstablishmentMapper;
import ru.nsu.fit.pak.budle.repository.EstablishmentRepository;
import ru.nsu.fit.pak.budle.repository.UserRepository;
import ru.nsu.fit.pak.budle.utils.ImageWorker;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstablishmentServiceImpl implements EstablishmentService {
    private final EstablishmentRepository establishmentRepository;

    private final EstablishmentMapper establishmentMapper;

    private final UserRepository userRepository;

    @Override
    public List<EstablishmentDto> getEstablishments() {
        return establishmentMapper.modelListToDtoList(establishmentRepository.findAll());
    }

    @Override
    public EstablishmentDto getEstablishmentById(Long id) {
        return establishmentMapper.modelToDto(establishmentRepository.getEstablishmentById(id));

    }

    @Override
    public List<EstablishmentDto> getEstablishmentsByCategory(String category) {
        return establishmentMapper.modelListToDtoList(establishmentRepository.findByCategory(category));
    }


    public List<EstablishmentDto> getEstablishmentByParams(Category category, Boolean hasMap, Boolean hasCardPayment) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
        Example<Establishment> exampleQuery = Example.of(new Establishment(category, hasMap, hasCardPayment), matcher);
        List<Establishment> results = establishmentRepository.findAll(exampleQuery);
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
}
