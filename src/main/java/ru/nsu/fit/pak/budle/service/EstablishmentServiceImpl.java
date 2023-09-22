package ru.nsu.fit.pak.budle.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import ru.nsu.fit.pak.budle.dao.*;
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
import ru.nsu.fit.pak.budle.exceptions.ErrorWhileParsingEstablishmentMapException;
import ru.nsu.fit.pak.budle.exceptions.EstablishmentAlreadyExistsException;
import ru.nsu.fit.pak.budle.exceptions.EstablishmentNotFoundException;
import ru.nsu.fit.pak.budle.mapper.EstablishmentMapper;
import ru.nsu.fit.pak.budle.mapper.TagMapper;
import ru.nsu.fit.pak.budle.repository.EstablishmentRepository;

import javax.transaction.Transactional;
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
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstablishmentServiceImpl implements EstablishmentService {
    private final EstablishmentRepository establishmentRepository;
    private final SpotService spotService;
    private final ImageService imageService;
    private final EstablishmentMapper establishmentMapper;
    private final SecurityService securityService;
    private final WorkingHoursService workingHoursService;
    private final TagMapper tagMapper;

    @Override
    public EstablishmentListDto getEstablishmentByParams(
            RequestGetEstablishmentParameters parameters
    ) {
        log.info("Getting establishment by parameters");
        securityService.findLoggedInUsername();
        log.info("Parameters" + parameters);

        PageRequest page = PageRequest.of(parameters.offset(), parameters.limit(),
                Sort.by(parameters.sortValue())
        );

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
        log.info("Results was " + results);
        List<Establishment> processing = results.getContent().stream().filter(
                est -> est.getWorkingHours().size() <= parameters.workingDayCount()
        ).toList();
        List<ResponseBasicEstablishmentInfo> establishments = establishmentMapper.modelListToDtoList(processing);
        return new EstablishmentListDto(establishments, establishments.size());
    }

    @Override
    @Transactional
    public void createEstablishment(RequestEstablishmentDto dto) {
        log.info("Creating new establishment");
        checkEstablishmentExistence(dto);
        log.info("Establishment with name and address does not exist");

        Establishment establishment = establishmentMapper.dtoToModel(dto);
        log.info("Establishment was converted");

        establishment.setOwner(securityService.getLoggedInUser());
        saveEstablishmentData(establishment, dto);
    }

    @Override
    public List<String> getCategories() {
        log.info("Getting categories");
        return Arrays.stream(Category.values()).map(x -> x.value).toList();
    }

    @Override
    public List<ResponseTagDto> getTags() {
        return tagMapper.modelArrayToTagDtoList(Tag.values());
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
        try {
            log.info("Creating map of establishment  " + establishmentId);
            Establishment establishment = getEstablishmentById(establishmentId);
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(map)));
            NodeList elems = document.getDocumentElement().getElementsByTagName("path");
            List<Spot> spots = getSpotList(elems);
            spotService.saveSpots(spots, establishmentId);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            String mapPath = "./images/" + establishmentId + ".svg";
            log.info("Map path was " + mapPath);
            establishment.setMap(mapPath);
            establishment.setHasMap(true);
            establishmentRepository.save(establishment);
            log.info("Map was saved");
            StreamResult result = new StreamResult(new File(mapPath));
            transformer.transform(new DOMSource(document), result);
        } catch (Exception e) {
            log.warn("Map parsing was exited with exception");
            log.warn(e.getMessage());
            throw new ErrorWhileParsingEstablishmentMapException();
        }
    }

    @NonNull
    private List<Spot> getSpotList(NodeList elems) {
        return IntStream.range(0, elems.getLength())
                .peek(idx -> ((Element) elems.item(idx)).setAttribute("id", String.valueOf(idx)))
                .mapToObj(idx -> {
                    Spot spot = new Spot();
                    spot.setLocalId((long) idx);
                    return spot;
                })
                .toList();
    }

    @Override
    @NonNull
    public List<ResponseShortEstablishmentInfo> getEstablishmentsByOwner(Long id) {
        User owner = securityService.getLoggedInUser();

        return establishmentMapper.toShortInfoList(
                establishmentRepository.findAllByOwner(owner)
        );
    }

    @Override
    @NonNull
    public ResponseSubcategoryDto getCategoryVariants(String category) {
        Category categoryEnum = Category.getEnumByValue(category);
        return categoryEnum.variants;
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

    @Override
    public String getTagByName(String tagName) {
        return tagMapper.modelToTagDto(Tag.parseEnum(tagName)).getImage();
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
            log.warn("Establishment " + name + " " + address + " already exists");
            throw new EstablishmentAlreadyExistsException(name, address);
        }
    }

    private void saveEstablishmentData(Establishment establishment, RequestEstablishmentDto dto) {
        Set<Tag> tags = tagMapper.tagDtoSetToModelSet(dto.getTags());
        log.info("Tags were converted");
        establishment.setTags(tags);
        Establishment savedEstablishment = establishmentRepository.save(establishment);
        log.info("Establishment was saved in db");
        Set<RequestWorkingHoursDto> responseWorkingHoursDto = dto.getWorkingHours();
        Set<PhotoDto> photos = dto.getPhotosInput();
        workingHoursService.saveWorkingHours(responseWorkingHoursDto, savedEstablishment);
        log.info("Working hours was saved");
        imageService.saveImages(photos, savedEstablishment);
        log.info("Images was saved.");
        log.info("Establishment save successfully");
        if (dto.getMap() != null) {
            addMap(establishment.getId(), dto.getMap());
        }
    }
}
