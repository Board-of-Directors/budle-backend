package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import ru.nsu.fit.pak.budle.dao.Category;
import ru.nsu.fit.pak.budle.dao.Photo;
import ru.nsu.fit.pak.budle.dao.Tag;
import ru.nsu.fit.pak.budle.dao.User;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.EstablishmentListDto;
import ru.nsu.fit.pak.budle.dto.PhotoDto;
import ru.nsu.fit.pak.budle.dto.ValidTimeDto;
import ru.nsu.fit.pak.budle.dto.request.RequestEstablishmentDto;
import ru.nsu.fit.pak.budle.dto.request.RequestGetEstablishmentParameters;
import ru.nsu.fit.pak.budle.dto.request.RequestWorkingHoursDto;
import ru.nsu.fit.pak.budle.dto.response.ResponseSubcategoryDto;
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
import java.util.stream.Stream;

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
    public EstablishmentListDto getEstablishmentByParams(
            RequestGetEstablishmentParameters parameters
    ) {
        logger.info("Getting establishment by parameters");

        logger.debug("Parameters" + parameters);

        PageRequest page = PageRequest.of(parameters.offset(), parameters.limit(),
                Sort.by(parameters.sortValue()));

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Category categoryEnum = parameters.category() == null ? null : Category.getEnumByValue(parameters.category());

        Example<Establishment> exampleQuery =
                Example.of(new Establishment(
                        categoryEnum,
                        parameters.hasMap(),
                        parameters.hasCardPayment(),
                        parameters.name()
                ), matcher);

        Page<Establishment> results = establishmentRepository.findAll(exampleQuery, page);
        logger.debug("Results was " + results);
        List<ResponseBasicEstablishmentInfo> establishments = establishmentMapper.modelListToDtoList(results);
        return new EstablishmentListDto(establishments, establishments.size());
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

        saveEstablishmentData(establishment, dto);
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
            throw new ErrorWhileParsingEstablishmentMapException();
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
    public ResponseSubcategoryDto getCategoryVariants(String category) {
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

    @Override
    @Transactional
    public void updateEstablishment(Long establishmentId, RequestEstablishmentDto establishmentDto) {
        Establishment originalEstablishment = getEstablishmentById(establishmentId);
        Establishment establishment = establishmentMapper.dtoToModel(establishmentDto);
        establishment.setId(establishmentId);
        deleteEstablishmentPhotos(originalEstablishment);
        deleteEstablishmentHours(originalEstablishment);
        saveEstablishmentData(establishment, establishmentDto);
    }

    private void deleteEstablishmentHours(Establishment originalEstablishment) {
        workingHoursService.deleteHours(originalEstablishment.getWorkingHours());
    }

    @Override
    public void deleteEstablishment(Long establishmentId) {
        Establishment establishment = getEstablishmentById(establishmentId);
        deleteEstablishmentPhotos(establishment);
        establishmentRepository.delete(establishment);
    }

    private void deleteEstablishmentPhotos(Establishment establishment) {
        Stream<String> paths = Stream.empty();
        if (!establishment.getPhotos().isEmpty()) {
            paths = Stream.concat(paths, establishment.getPhotos().stream().map(Photo::getFilepath));
        }
        paths = Stream.concat(paths, Stream.of(establishment.getImage()));
        imageService.deleteImages(paths.toList());
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

    private void saveEstablishmentData(Establishment establishment, RequestEstablishmentDto dto) {
        Set<Tag> tags = tagMapper.tagDtoSetToModelSet(dto.getTags());
        logger.info("Tags were converted");
        establishment.setTags(tags);
        Establishment savedEstablishment = establishmentRepository.save(establishment);
        logger.info("Establishment was saved in db");
        Set<RequestWorkingHoursDto> responseWorkingHoursDto = dto.getWorkingHours();
        Set<PhotoDto> photos = dto.getPhotosInput();
        workingHoursService.saveWorkingHours(responseWorkingHoursDto, savedEstablishment);
        logger.info("Working hours was saved");
        imageService.saveImages(photos, savedEstablishment);
        logger.info("Images was saved.");
        logger.info("Establishment save successfully");
    }
}
