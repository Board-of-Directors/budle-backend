package ru.nsu.fit.pak.budle.service;

import ru.nsu.fit.pak.budle.dto.PhotoDto;
import ru.nsu.fit.pak.budle.dto.TagDto;
import ru.nsu.fit.pak.budle.dto.ValidTimeDto;

import java.util.List;
import java.util.Set;

public interface EstablishmentService {
    Set<PhotoDto> getPhotos(Long establishmentId);

    List<ValidTimeDto> getValidTime(Long establishmentId);

    List<TagDto> getSpotTags(Long establishmentId);
}
