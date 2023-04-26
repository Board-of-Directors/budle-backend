package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import ru.nsu.fit.pak.budle.dao.Category;
import ru.nsu.fit.pak.budle.dao.Tag;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.*;
import ru.nsu.fit.pak.budle.exceptions.EstablishmentAlreadyExistsException;
import ru.nsu.fit.pak.budle.exceptions.EstablishmentNotFoundException;
import ru.nsu.fit.pak.budle.mapper.EstablishmentMapper;
import ru.nsu.fit.pak.budle.mapper.PhotoMapper;
import ru.nsu.fit.pak.budle.mapper.TagMapper;
import ru.nsu.fit.pak.budle.repository.EstablishmentRepository;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EstablishmentServiceImpl implements EstablishmentService {
    private static final Logger logger = LoggerFactory.getLogger(EstablishmentServiceImpl.class);
    private final EstablishmentRepository establishmentRepository;
    private final SpotService spotService;

    private final ImageService imageService;
    private final EstablishmentMapper establishmentMapper;

    private final WorkingHoursService workingHoursService;

    private final PhotoMapper photoMapper;

    private final TagMapper tagMapper;

    @Override
    public List<EstablishmentDto> getEstablishmentByParams(String category,
                                                           Boolean hasMap,
                                                           Boolean hasCardPayment,
                                                           String name,
                                                           Pageable page) {
        logger.info("Getting establishment by parameters");

        logger.debug("Parameters\n" +
                "Category: " + category + "\n" +
                "HasMap: " + hasMap + "\n" +
                "HasCardPayment " + hasCardPayment + "\n" +
                "Name: " + name + "\n" +
                "Page: " + page + "\n");

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Category categoryEnum = category == null ? null : Category.getEnumByValue(category);
        Example<Establishment> exampleQuery = Example.of(new Establishment(categoryEnum, hasMap, hasCardPayment, name), matcher);
        Page<Establishment> results = establishmentRepository.findAll(exampleQuery, page);
        logger.debug("Results was " + results);
        return establishmentMapper.modelListToDtoList(results);
    }

    @Override
    public void createEstablishment(EstablishmentDto dto) {
        logger.info("Creating new establishment");
        logger.debug("Establishment parameters:" + dto);
        checkEstablishmentExistence(dto);


        Establishment establishment = establishmentMapper.dtoToModel(dto);
        Set<Tag> tags = tagMapper.tagDtoSetToModelSet(dto.getTags());
        establishment.setTags(tags);
        Establishment savedEstablishment = establishmentRepository.save(establishment);

        Set<WorkingHoursDto> workingHoursDto = dto.getWorkingHours();
        Set<PhotoDto> photos = dto.getPhotosInput();
        workingHoursService.saveWorkingHours(workingHoursDto, savedEstablishment);
        imageService.saveImages(photos, savedEstablishment);
        logger.info("Establishment was saved");
    }

    @Override
    public List<String> getCategories() {
        logger.info("Getting categories");
        return Arrays.stream(Category.values()).map(x -> x.value).toList();
    }

    @Override
    public List<TagDto> getTags() {
        return tagMapper.modelArrayToTagDtoList(Tag.values());
    }

    @Override
    public Set<PhotoDto> getPhotos(Long establishmentId) {
        logger.info("Getting photos");
        Establishment establishment = getEstablishmentById(establishmentId);
        return photoMapper.convertModelPhotoSetToDtoSet(establishment.getPhotos());
    }

    @Override
    public List<ValidTimeDto> getValidTime(Long establishmentId) {
        Establishment establishment = getEstablishmentById(establishmentId);
        return workingHoursService.getValidBookingHoursByEstablishment(establishment);
    }

    @Override
    public List<TagDto> getSpotTags(Long establishmentId) {
        Establishment establishment = getEstablishmentById(establishmentId);
        return tagMapper.modelSetToSpotTagDtoList(establishment.getTags());

    }

    @Override
    public void addMap(Long establishmentId, String map) {
        logger.info("Creating map of establishment  " + establishmentId);
        Establishment establishment = getEstablishmentById(establishmentId);
        Transformer transformer;
        DOMSource source;
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(map)));
            NodeList elems = document.getDocumentElement().getElementsByTagName("path");

            //FIXME: SAVE ALL SPOTS IN ONE QUERY
            for (int i = 0; i < elems.getLength(); i++) {
                spotService.createSpot((long) i, establishmentId);
                Element elem = (Element) elems.item(i);
                elem.setAttribute("id", i + "");
            }

            transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            source = new DOMSource(document);
        } catch (Exception e) {
            logger.warn("Map parsing was exited with exception");
            logger.warn(e.getMessage());
            return;
        }

        String mapPath = "./maps" + establishmentId + ".svg";
        logger.debug("Map path was " + mapPath);

        establishment.setMap(mapPath);
        establishment.setHasMap(true);
        establishmentRepository.save(establishment);

        logger.info("Map was saved");

        StreamResult result = new StreamResult(new File(mapPath));
        try {
            transformer.transform(source, result);
        } catch (Exception e) {
            logger.warn("Transform exception");
            logger.warn(e.getMessage());
        }
    }

    public Establishment getEstablishmentById(Long establishmentId) {
        return establishmentRepository
                .findById(establishmentId).orElseThrow(
                        () -> new EstablishmentNotFoundException(establishmentId)
                );
    }

    private void checkEstablishmentExistence(EstablishmentDto dto) {
        String address = dto.getAddress();
        String name = dto.getName();
        if (establishmentRepository.existsByAddressAndName(address, name)) {
            logger.warn("Establishment " + name + " " + address + " already exists");
            throw new EstablishmentAlreadyExistsException(name, address);
        }
    }
}
