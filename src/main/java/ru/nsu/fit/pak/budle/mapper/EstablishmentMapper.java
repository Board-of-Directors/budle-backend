package ru.nsu.fit.pak.budle.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.budle.dao.Category;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.EstablishmentDto;
import ru.nsu.fit.pak.budle.repository.UserRepository;
import ru.nsu.fit.pak.budle.utils.EstablishmentFactory;
import ru.nsu.fit.pak.budle.utils.ImageWorker;

import java.util.List;


@Component
@RequiredArgsConstructor
public class EstablishmentMapper {
    private final ModelMapper modelMapper;
    private final ImageWorker imageWorker;
    private final UserRepository userRepository;

    private final EstablishmentFactory establishmentFactory = new EstablishmentFactory();

    public EstablishmentDto modelToDto(Establishment establishment) {
        EstablishmentDto establishmentDto = modelMapper.map(establishment,
                establishmentFactory.getEstablishmentDto(establishment.getCategory().toString()));
        establishmentDto.setImage(imageWorker.loadImage(establishment));
        establishmentDto.setCategory(establishment.getCategory().value);
        return establishmentDto;
    }

    public List<EstablishmentDto> modelListToDtoList(Page<Establishment> establishmentList) {
        return establishmentList
                .stream()
                .map(this::modelToDto)
                .toList();
    }

    public Establishment dtoToModel(EstablishmentDto dto) {
        Establishment establishment = modelMapper.map(dto, Establishment.class);
        establishment.setImage(imageWorker.saveImage(establishment));
        establishment.setOwner(userRepository.getReferenceById(1L));
        establishment.setCategory(Category.valueOf(dto.getCategory()));
        return establishment;
    }
}
