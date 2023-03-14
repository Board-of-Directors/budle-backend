package ru.nsu.fit.pak.budle.service;

import ru.nsu.fit.pak.budle.dto.PhotoDto;

import java.util.Set;

public interface EstablishmentService {
    Set<PhotoDto> getPhotos(Long establishmentId);
}
