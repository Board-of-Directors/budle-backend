package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
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

    public void saveImages(Set<PhotoDto> photosDto, Establishment establishment) {
        Set<Photo> photos = photosDto
                .stream()
                .map(x -> new Photo(imageWorker.saveImage(x.getImage())))
                .peek((x) -> x.setEstablishment(establishment)).collect(Collectors.toSet());
        imageRepository.saveAll(photos);
    }


}
