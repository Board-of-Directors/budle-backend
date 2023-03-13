package ru.nsu.fit.pak.budle.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.budle.dao.Category;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.EstablishmentDto;
import ru.nsu.fit.pak.budle.dto.TagDto;
import ru.nsu.fit.pak.budle.repository.UserRepository;
import ru.nsu.fit.pak.budle.utils.EstablishmentFactory;
import ru.nsu.fit.pak.budle.utils.ImageWorker;

import java.util.List;
import java.util.stream.Collectors;


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
        establishmentDto.setImage(imageWorker.loadImage(establishment.getImage()));
        establishmentDto.setCategory(establishment.getCategory().value);
        establishmentDto.setTags(establishment
                .getTags()
                .stream()
                .map(x -> new TagDto(x.translate, imageWorker.getImageFromResource(x.assets)))
                .collect(Collectors.toSet()));
        return establishmentDto;
    }

    public List<EstablishmentDto> modelListToDtoList(Page<Establishment> establishmentList) {
        return establishmentList
                .stream()
                .map(this::modelToDto)
                .toList();
    }

    public Establishment dtoToModel(EstablishmentDto dto) {
        Establishment establishment = modelMapper.map(dto,
                establishmentFactory.getEstablishmentEntity(dto.getCategory()));
        establishment.setImage(imageWorker.saveImage(establishment.getImage()));
        establishment.setOwner(userRepository.getReferenceById(1L));
        establishment.setCategory(Category.valueOf(dto.getCategory()));
        establishment.setWorkingHours(null);
        return establishment;
    }
}
