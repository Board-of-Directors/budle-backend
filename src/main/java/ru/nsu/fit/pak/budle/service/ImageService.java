package ru.nsu.fit.pak.budle.service;

import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.PhotoDto;

import java.util.Set;

public interface ImageService {

    void saveImages(Set<PhotoDto> photos, Establishment establishment);
}
