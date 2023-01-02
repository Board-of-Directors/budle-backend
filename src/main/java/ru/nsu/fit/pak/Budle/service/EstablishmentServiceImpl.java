package ru.nsu.fit.pak.Budle.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.Budle.dto.EstablishmentDto;
import ru.nsu.fit.pak.Budle.mapper.EstablishmentMapper;
import ru.nsu.fit.pak.Budle.repository.EstablishmentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstablishmentServiceImpl implements EstablishmentService {
    private final EstablishmentRepository establishmentRepository;

    private final EstablishmentMapper establishmentMapper;

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
}
