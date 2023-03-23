package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dao.Photo;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.PhotoDto;
import ru.nsu.fit.pak.budle.repository.ImageRepository;
import ru.nsu.fit.pak.budle.utils.ImageWorker;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final ImageWorker imageWorker;
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public void saveImages(Set<PhotoDto> photosDto, Establishment establishment) {
        logger.info("Saving image");
        logger.debug(photosDto.toString());
        logger.debug(establishment.toString());
        Set<Photo> photos = photosDto
                .stream()
                .map(x -> new Photo(imageWorker.saveImage(x.getImage())))
                .peek((x) -> x.setEstablishment(establishment)).collect(Collectors.toSet());
        imageRepository.saveAll(photos);
        logger.info("Images saved successfully");
    }


}
