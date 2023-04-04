package ru.nsu.fit.pak.budle.service;

import org.springframework.data.domain.Pageable;
import ru.nsu.fit.pak.budle.dto.EstablishmentDto;
import ru.nsu.fit.pak.budle.dto.PhotoDto;
import ru.nsu.fit.pak.budle.dto.TagDto;
import ru.nsu.fit.pak.budle.dto.ValidTimeDto;

import java.util.List;
import java.util.Set;

public interface EstablishmentService {

    List<EstablishmentDto> getEstablishmentByParams(String category,
                                                    Boolean hasMap,
                                                    Boolean hasCardPayment,
                                                    String name,
                                                    Pageable page);

    void createEstablishment(EstablishmentDto dto);

    List<String> getCategories();

    List<TagDto> getTags();

    void addMap(Long establishmentId, String map);


    Set<PhotoDto> getPhotos(Long establishmentId);

    List<ValidTimeDto> getValidTime(Long establishmentId);

    List<TagDto> getSpotTags(Long establishmentId);
}
