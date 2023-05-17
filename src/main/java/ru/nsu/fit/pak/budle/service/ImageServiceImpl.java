package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dao.Photo;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.PhotoDto;
import ru.nsu.fit.pak.budle.mapper.PhotoMapper;
import ru.nsu.fit.pak.budle.repository.ImageRepository;

import java.io.File;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final PhotoMapper photoMapper;
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public void saveImages(Set<PhotoDto> photosDto, Establishment establishment) {
        logger.info("Saving image");
        logger.debug(photosDto.toString());
        logger.debug(establishment.toString());
        Set<Photo> photos = photoMapper.convertSetPhotoDtoToModelSet(photosDto, establishment);
        imageRepository.saveAll(photos);
        logger.info("Images saved successfully");
    }

    @Override
    public void deleteImages(List<String> imagesPath) {
        logger.info("Deleting images");
        for (String path : imagesPath) {
            File file = new File(path);
            if (!file.delete()) {
                logger.warn("Deleting image was false");
            }
        }
    }


}
