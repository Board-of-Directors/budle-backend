package ru.nsu.fit.pak.budle.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.budle.dao.Establishment;
import ru.nsu.fit.pak.budle.dto.EstablishmentDto;

import java.util.List;


@Component
@RequiredArgsConstructor
public class EstablishmentMapper {
    private final ModelMapper modelMapper;

    public EstablishmentDto modelToDto(Establishment establishment) {
        return modelMapper.map(establishment, EstablishmentDto.class);
    }

    public List<EstablishmentDto> modelListToDtoList(List<Establishment> establishmentList) {
        return establishmentList
                .stream()
                .map(establishment -> modelMapper.map(establishment, EstablishmentDto.class))
                .toList();
    }

    public Establishment dtoToModel(EstablishmentDto dto) {
        return modelMapper.map(dto, Establishment.class);
    }
}
