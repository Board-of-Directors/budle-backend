package ru.nsu.fit.pak.budle.service;

import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.PhotoDto;

import java.util.Set;

/**
 * Service, that responsible for saving images.
 */

public interface ImageService {

    /**
     * Saving images for provided establishment.
     *
     * @param photos        set of photos those we need to save.
     * @param establishment which will be associated with those photos.
     */
    void saveImages(Set<PhotoDto> photos, Establishment establishment);
}
