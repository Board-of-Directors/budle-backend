package ru.nsu.fit.pak.budle.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dao.establishment.restaurant.CuisineCountry;
import ru.nsu.fit.pak.budle.dto.PhotoDto;
import ru.nsu.fit.pak.budle.dto.request.RequestEstablishmentDto;
import ru.nsu.fit.pak.budle.dto.response.ResponseTagDto;
import ru.nsu.fit.pak.budle.dto.response.ResponseWorkingHoursDto;
import ru.nsu.fit.pak.budle.dto.response.establishment.basic.BasicEstablishmentInfo;
import ru.nsu.fit.pak.budle.dto.response.establishment.basic.ResponseBasicEstablishmentInfo;
import ru.nsu.fit.pak.budle.dto.response.establishment.extended.ResponseExtendedEstablishmentInfo;
import ru.nsu.fit.pak.budle.exceptions.ErrorWhileParsingEstablishmentMapException;
import ru.nsu.fit.pak.budle.utils.EstablishmentFactory;
import ru.nsu.fit.pak.budle.utils.ImageWorker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class, that provide mapping operations for establishments.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class EstablishmentMapper {
    private final ModelMapper modelMapper;
    private final ImageWorker imageWorker;
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

    public ResponseExtendedEstablishmentInfo toExtended(Establishment establishment) {
        Class<? extends ResponseBasicEstablishmentInfo> classOfDto = establishmentFactory
            .getEstablishmentDto(establishment.getCategory().toString(), "extended");

        ResponseExtendedEstablishmentInfo responseEstablishmentInfo =
            (ResponseExtendedEstablishmentInfo) modelMapper.map(establishment, classOfDto);

        responseEstablishmentInfo.setWorkingHours(establishment
            .getWorkingHours()
            .stream()
            .sorted(Comparator.comparing(o -> o.getDayOfWeek().getOrdinal()))
            .map(x -> {
                ResponseWorkingHoursDto dto = modelMapper.map(x, ResponseWorkingHoursDto.class);
                dto.setDayOfWeek(x.getDayOfWeek().getTranslate());
                return dto;
            })
            .collect(Collectors.toList()));

        responseEstablishmentInfo.setTags(establishment
            .getTags()
            .stream()
            .map(x -> new ResponseTagDto(x.translate, imageWorker.getImageFromResource(x.assets)))
            .collect(Collectors.toSet()));

        responseEstablishmentInfo.setPhotos(establishment
            .getPhotos()
            .stream()
            .map(x -> new PhotoDto(imageWorker.loadImage(x.getFilepath())))
            .collect(Collectors.toSet()));

        responseEstablishmentInfo.setImage(imageWorker.loadImage(establishment.getImage()));
        String map = getMap(establishment);
        responseEstablishmentInfo.setMap(map);

        return responseEstablishmentInfo;

    }

    public BasicEstablishmentInfo toBasic(Establishment establishment) {
        return new BasicEstablishmentInfo(
            establishment.getId(),
            establishment.getName(),
            establishment.getAddress(),
            establishment.getRating(),
            establishment.getImage(),
            establishment.getCategory().getValue(),
            establishment.getHasMap(),
            establishment.getHasCardPayment(),
            establishment.getStarsCount(),
            Optional.ofNullable(establishment.getCuisineCountry()).map(CuisineCountry::getValue).orElse(null)
        );
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
        Establishment establishment = modelMapper.map(
            dto,
            establishmentFactory.getEstablishmentEntity(dto.getCategory())
        );
        establishment.setImage(imageWorker.saveImage(establishment.getImage()));
        establishment.setWorkingHours(null);
        establishment.setPhotos(null);
        return establishment;
    }


    private String getMap(Establishment establishment) {
        if (!establishment.getHasMap()) {
            return null;
        }
        try {
            BufferedReader mapXml = new BufferedReader(new FileReader(establishment.getMap()));
            StringBuilder builder = new StringBuilder();
            while (mapXml.ready()) {
                builder.append(mapXml.readLine());
            }
            return builder.toString();
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new ErrorWhileParsingEstablishmentMapException();
        }
    }
}
