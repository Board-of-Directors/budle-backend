package ru.nsu.fit.pak.budle.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.budle.dao.Establishment;
import ru.nsu.fit.pak.budle.dto.EstablishmentDto;
import ru.nsu.fit.pak.budle.utils.ImageWorker;

import java.util.List;


@Component
@RequiredArgsConstructor
public class EstablishmentMapper {
    private final ModelMapper modelMapper;
    private final ImageWorker imageWorker;

    public EstablishmentDto modelToDto(Establishment establishment) {
        EstablishmentDto establishmentDto = modelMapper.map(establishment, EstablishmentDto.class);
        establishmentDto.setImage(imageWorker.loadImage(establishment));
        return establishmentDto;
    }

    public List<EstablishmentDto> modelListToDtoList(Page<Establishment> establishmentList) {
        return establishmentList
                .stream()
                .map(this::modelToDto)
                .toList();
    }

    public Establishment dtoToModel(EstablishmentDto dto) {
        return modelMapper.map(dto, Establishment.class);
    }
}
