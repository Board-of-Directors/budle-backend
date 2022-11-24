package ru.nsu.fit.pak.Budle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.Budle.dao.Establishment;
import ru.nsu.fit.pak.Budle.dto.EstablishmentDto;
import ru.nsu.fit.pak.Budle.mapper.EstablishmentMapper;
import ru.nsu.fit.pak.Budle.repository.EstablishmentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstablishmentService implements EstablishmentServiceInterface {
    @Autowired
    private EstablishmentRepository establishmentRepository;

    @Autowired
    private EstablishmentMapper establishmentMapper;

    @Override
    public List<EstablishmentDto> getEstablishments() {
        return establishmentMapper.modelListToDtoList(establishmentRepository.findAll());
    }
    @Override
    public EstablishmentDto getEstablishmentById(Long id){
       return establishmentMapper.modelToDto(establishmentRepository.getEstablishmentById(id));

    }
}
