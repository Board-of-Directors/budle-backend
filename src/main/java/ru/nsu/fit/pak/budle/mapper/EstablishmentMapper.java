package ru.nsu.fit.pak.budle.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.budle.dao.Category;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dao.establishment.restaurant.CuisineCountry;
import ru.nsu.fit.pak.budle.dao.establishment.restaurant.Restaurant;
import ru.nsu.fit.pak.budle.dto.WorkingHoursDto;
import ru.nsu.fit.pak.budle.dto.request.RequestEstablishmentDto;
import ru.nsu.fit.pak.budle.dto.request.RequestRestaurantDto;
import ru.nsu.fit.pak.budle.dto.response.ResponseTagDto;
import ru.nsu.fit.pak.budle.dto.response.establishment.shortInfo.ResponseShortEstablishmentInfo;
import ru.nsu.fit.pak.budle.repository.UserRepository;
import ru.nsu.fit.pak.budle.utils.EstablishmentFactory;
import ru.nsu.fit.pak.budle.utils.ImageWorker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class, that provide mapping operations for establishments.
 */
@Component
@RequiredArgsConstructor
public class EstablishmentMapper {
    private final ModelMapper modelMapper;
    private final ImageWorker imageWorker;
    private final UserRepository userRepository;

    private final EstablishmentFactory establishmentFactory;

    /**
     * Converts establishment model to establishment dto.
     * First stage: Use establishment factory to get class of establishment.
     * Second stage: Use model mapper with provided from factory class
     * to convert some field automatically.
     * Third stage: Use image worker to get image from file system and
     * convert bytes to BASE64.
     * Other: Convert other fields to those dto.
     * FIXME: need to reduce code, do some refactoring, maybe deal with model mapper
     *
     * @param establishment object that we need to convert.
     * @return establishmentDto with provided fields.
     */

    public RequestEstablishmentDto modelToDto(Establishment establishment) {
        Class<? extends RequestEstablishmentDto> classOfDto = establishmentFactory
                .getEstablishmentDto(establishment.getCategory().toString());

        RequestEstablishmentDto requestEstablishmentDto = modelMapper.map(establishment, classOfDto);

        requestEstablishmentDto.setImage(imageWorker.loadImage(establishment.getImage()));
        requestEstablishmentDto.setOwner(establishment.getOwner().getId());
        requestEstablishmentDto.setCategory(establishment.getCategory().value);
        if (establishment instanceof Restaurant restaurant &&
                requestEstablishmentDto instanceof RequestRestaurantDto requestRestaurantDto) {
            String name = restaurant.getCuisineCountry().getValue();
            requestRestaurantDto.setCuisineCountry(name);
        }
        requestEstablishmentDto.setWorkingHours(establishment
                .getWorkingHours()
                .stream()
                .map(x -> {
                    WorkingHoursDto dto = modelMapper.map(x, WorkingHoursDto.class);
                    dto.setDayOfWeek(x.getDayOfWeek().getTranslate());
                    return dto;
                })
                .collect(Collectors.toSet()));

        requestEstablishmentDto.setTags(establishment
                .getTags()
                .stream()
                .map(x -> new ResponseTagDto(x.translate, imageWorker.getImageFromResource(x.assets)))
                .collect(Collectors.toSet()));
        try {
            if (establishment.getHasMap()) {
                BufferedReader mapXml = new BufferedReader(new FileReader(establishment.getMap()));
                StringBuilder builder = new StringBuilder();
                while (mapXml.ready()) {
                    builder.append(mapXml.readLine());
                }
                requestEstablishmentDto.setMap(builder.toString());
            }

        } catch (Exception e) {
            System.out.println("Parsing map " + establishment.getMap() + " was broken");
        }
        return requestEstablishmentDto;
    }

    /**
     * Convert list of establishment models to list of establishment dto.
     *
     * @param establishmentList list of establishment models
     * @return list of establishment dto.
     */

    public List<RequestEstablishmentDto> modelListToDtoList(Page<Establishment> establishmentList) {
        return establishmentList
                .stream()
                .map(this::modelToDto)
                .toList();
    }

    /**
     * Converts establishment dto to establishment model.
     * Stages are similar to opposite mapping.
     * FIXME: User hardcoded.
     *
     * @param dto establishment dto object.
     * @return establishment model object.
     */

    public Establishment dtoToModel(RequestEstablishmentDto dto) {
        Establishment establishment = modelMapper.map(dto,
                establishmentFactory.getEstablishmentEntity(dto.getCategory()));

        if (dto instanceof RequestRestaurantDto requestRestaurantDto &&
                establishment instanceof Restaurant restaurant) {
            String name = requestRestaurantDto.getCuisineCountry();
            restaurant.setCuisineCountry(CuisineCountry.getEnumByValue(name));
        }
        establishment.setImage(imageWorker.saveImage(establishment.getImage()));
        establishment.setOwner(userRepository.findAll().get(0));
        establishment.setCategory(Category.valueOf(dto.getCategory()));
        establishment.setWorkingHours(null);
        establishment.setPhotos(null);
        return establishment;
    }

    public ResponseShortEstablishmentInfo toShortInfo(Establishment establishment) {
        return modelMapper.map(establishment, ResponseShortEstablishmentInfo.class);
    }


    public List<ResponseShortEstablishmentInfo> toShortInfoList(List<Establishment> establishmentList) {
        return establishmentList.stream().map(this::toShortInfo).toList();
    }
}
