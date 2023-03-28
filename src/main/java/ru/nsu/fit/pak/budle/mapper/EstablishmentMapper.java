package ru.nsu.fit.pak.budle.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.budle.dao.Category;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dao.establishment.restaurant.CuisineCountry;
import ru.nsu.fit.pak.budle.dao.establishment.restaurant.Restaurant;
import ru.nsu.fit.pak.budle.dto.EstablishmentDto;
import ru.nsu.fit.pak.budle.dto.RestaurantDto;
import ru.nsu.fit.pak.budle.dto.TagDto;
import ru.nsu.fit.pak.budle.dto.WorkingHoursDto;
import ru.nsu.fit.pak.budle.repository.UserRepository;
import ru.nsu.fit.pak.budle.utils.EstablishmentFactory;
import ru.nsu.fit.pak.budle.utils.ImageWorker;

import java.io.BufferedReader;
import java.io.FileReader;
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
        Class<? extends EstablishmentDto> classOfDto = establishmentFactory
                .getEstablishmentDto(establishment.getCategory().toString());
        EstablishmentDto establishmentDto = modelMapper.map(establishment, classOfDto);
        establishmentDto.setImage(imageWorker.loadImage(establishment.getImage()));
        establishmentDto.setCategory(establishment.getCategory().value);
        if (establishment instanceof Restaurant) {
            String name = ((Restaurant) establishment).getCuisineCountry().getValue();
            ((RestaurantDto) establishmentDto).setCuisineCountry(name);
        }
        establishmentDto.setWorkingHours(establishment
                .getWorkingHours()
                .stream()
                .map(x -> {
                    WorkingHoursDto dto = modelMapper.map(x, WorkingHoursDto.class);
                    dto.setDayOfWeek(x.getDayOfWeek().getTranslate());
                    return dto;
                })
                .collect(Collectors.toSet()));

        establishmentDto.setTags(establishment
                .getTags()
                .stream()
                .map(x -> new TagDto(x.translate, imageWorker.getImageFromResource(x.assets)))
                .collect(Collectors.toSet()));
        try {
            if (establishment.getHasMap()) {
                BufferedReader mapXml = new BufferedReader(new FileReader(establishment.getMap()));
                StringBuilder builder = new StringBuilder();
                while (mapXml.ready()) {
                    builder.append(mapXml.readLine());
                }
                establishmentDto.setMap(builder.toString());
            }

        } catch (Exception e) {
            System.out.println("Parsing map " + establishment.getMap() + " was broken");
        }
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
        if (dto instanceof RestaurantDto) {
            String name = ((RestaurantDto) dto).getCuisineCountry();
            ((Restaurant) establishment).setCuisineCountry(CuisineCountry.getEnumByValue(name));
        }
        establishment.setImage(imageWorker.saveImage(establishment.getImage()));
        establishment.setOwner(userRepository.getReferenceById(1L));
        establishment.setCategory(Category.valueOf(dto.getCategory()));
        establishment.setWorkingHours(null);
        establishment.setPhotos(null);
        return establishment;
    }
}
