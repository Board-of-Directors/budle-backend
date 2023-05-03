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
import ru.nsu.fit.pak.budle.dao.User;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.PhotoDto;
import ru.nsu.fit.pak.budle.dto.ValidTimeDto;
import ru.nsu.fit.pak.budle.dto.WorkingHoursDto;
import ru.nsu.fit.pak.budle.dto.request.RequestEstablishmentDto;
import ru.nsu.fit.pak.budle.dto.response.ResponseTagDto;
import ru.nsu.fit.pak.budle.dto.response.establishment.basic.ResponseBasicEstablishmentInfo;
import ru.nsu.fit.pak.budle.dto.response.establishment.extended.ResponseExtendedEstablishmentInfo;
import ru.nsu.fit.pak.budle.dto.response.establishment.shortInfo.ResponseShortEstablishmentInfo;
import ru.nsu.fit.pak.budle.exceptions.*;
import ru.nsu.fit.pak.budle.mapper.EstablishmentMapper;
import ru.nsu.fit.pak.budle.mapper.PhotoMapper;
import ru.nsu.fit.pak.budle.mapper.TagMapper;
import ru.nsu.fit.pak.budle.repository.EstablishmentRepository;
import ru.nsu.fit.pak.budle.repository.UserRepository;

import javax.transaction.Transactional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EstablishmentServiceImpl implements EstablishmentService {
    private static final Logger logger = LoggerFactory.getLogger(EstablishmentServiceImpl.class);
    private final EstablishmentRepository establishmentRepository;

    private final UserRepository userRepository;
    private final SpotService spotService;

    private final ImageService imageService;
    private final EstablishmentMapper establishmentMapper;

    private final WorkingHoursService workingHoursService;

    private final PhotoMapper photoMapper;

    private final TagMapper tagMapper;

    @Override
    public List<ResponseBasicEstablishmentInfo> getEstablishmentByParams(String category,
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
    @Transactional
    public void createEstablishment(RequestEstablishmentDto dto) {
        logger.info("Creating new establishment");
        logger.info("Establishment parameters:" + dto);
        checkEstablishmentExistence(dto);
        logger.info("Establishment with name and address does not exist");

        Establishment establishment = establishmentMapper.dtoToModel(dto);
        logger.info("Establishment was converted");
        Set<Tag> tags = tagMapper.tagDtoSetToModelSet(dto.getTags());
        logger.info("Tags were converted");
        establishment.setTags(tags);
        Establishment savedEstablishment = establishmentRepository.save(establishment);
        logger.info("Establishment was saved in db");
        Set<WorkingHoursDto> workingHoursDto = dto.getWorkingHours();
        Set<PhotoDto> photos = dto.getPhotosInput();
        workingHoursService.saveWorkingHours(workingHoursDto, savedEstablishment);
        logger.info("Working hours was saved");
        imageService.saveImages(photos, savedEstablishment);
        logger.info("Images was saved.");
        logger.info("Establishment save successfully");
    }

    @Override
    public List<String> getCategories() {
        logger.info("Getting categories");
        return Arrays.stream(Category.values()).map(x -> x.value).toList();
    }

    @Override
    public List<ResponseTagDto> getTags() {
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
    public List<ResponseTagDto> getSpotTags(Long establishmentId) {
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

    @Override
    public List<ResponseShortEstablishmentInfo> getEstablishmentsByOwner(Long id) {
        User owner = userRepository.findById(id).orElseThrow(
                UserNotFoundException::new);

        return establishmentMapper.toShortInfoList(
                establishmentRepository.findAllByOwner(owner)
        );
    }

    @Override
    public List<String> getCategoryVariants(String category) {
        Category categoryEnum = Category.getEnumByValue(category);
        return categoryEnum.variants;
    }

    @Override
    public String getMap(Long establishmentId) {
        Establishment establishment = getEstablishmentById(establishmentId);
        if (!establishment.getHasMap()) {
            throw new EstablishmentMapDoesntExistException();
        }
        try {
            BufferedReader mapXml = new BufferedReader(new FileReader(establishment.getMap()));
            StringBuilder builder = new StringBuilder();
            while (mapXml.ready()) {
                builder.append(mapXml.readLine());
            }
            return builder.toString();
        } catch (Exception e) {
            logger.warn(e.getMessage());
            throw new ErrorWhileParsingEstablishmentMapException();
        }
    }

    public Establishment getEstablishmentById(Long establishmentId) {
        return establishmentRepository
                .findById(establishmentId).orElseThrow(
                        () -> new EstablishmentNotFoundException(establishmentId)
                );
    }

    @Override
    public ResponseExtendedEstablishmentInfo getEstablishmentInfoById(Long establishmentId) {
        return establishmentMapper.toExtended(getEstablishmentById(establishmentId));
    }

    private void checkEstablishmentExistence(RequestEstablishmentDto dto) {
        String address = dto.getAddress();
        String name = dto.getName();
        if (establishmentRepository.existsByAddressAndName(address, name)) {
            logger.warn("Establishment " + name + " " + address + " already exists");
            throw new EstablishmentAlreadyExistsException(name, address);
        }
    }
}
