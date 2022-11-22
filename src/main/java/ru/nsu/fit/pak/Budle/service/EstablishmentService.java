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
    EstablishmentRepository establishmentRepository;

    @Autowired
    EstablishmentMapper establishmentMapper;

    @Override
    public List<EstablishmentDto> getEstablishments() {
        Iterable<Establishment> establishments = establishmentRepository.findAll();
        ArrayList<EstablishmentDto> establishmentDtos = new ArrayList<>();
        for (Establishment establishment : establishments) {
            EstablishmentDto dto = establishmentMapper.modelToDto(establishment);
            establishmentDtos.add(dto);
        }
        return establishmentDtos;
    }
    @Override
    public EstablishmentDto getEstablishmentById(Long id){
       return establishmentMapper.modelToDto(establishmentRepository.getEstablishmentById(id));

    }
}
