package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final PhotoMapper photoMapper;

    public void saveImages(Set<PhotoDto> photosDto, Establishment establishment) {
        log.info("Saving image");
        log.info(photosDto.toString());
        log.info(establishment.toString());
        Set<Photo> photos = photoMapper.convertSetPhotoDtoToModelSet(photosDto, establishment);
        imageRepository.saveAll(photos);
        log.info("Images saved successfully");
    }

    @Override
    public void deleteImages(List<String> imagesPath) {
        log.info("Deleting images");
        for (String path : imagesPath) {
            File file = new File("./images/" + path);
            if (!file.delete()) {
                log.warn("Deleting image was false");
            }
        }
    }

    @Override
    public void deleteImages(List<String> imagesPath) {
        logger.info("Deleting images");
        for (String path : imagesPath) {
            File file = new File("./images/" + path);
            if (!file.delete()) {
                logger.warn("Deleting image was false");
            }
        }
    }


}
