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
import ru.nsu.fit.pak.budle.dao.WorkingHours;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.*;
import ru.nsu.fit.pak.budle.exceptions.EstablishmentAlreadyExistsException;
import ru.nsu.fit.pak.budle.exceptions.EstablishmentNotFoundException;
import ru.nsu.fit.pak.budle.mapper.EstablishmentMapper;
import ru.nsu.fit.pak.budle.repository.EstablishmentRepository;
import ru.nsu.fit.pak.budle.utils.ImageWorker;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstablishmentServiceImpl implements EstablishmentService {
    private static final Logger logger = LoggerFactory.getLogger(EstablishmentServiceImpl.class);
    private final EstablishmentRepository establishmentRepository;
    private final SpotService spotService;

    private final ImageService imageService;
    private final EstablishmentMapper establishmentMapper;

    private final WorkingHoursService workingHoursService;

    private final ImageWorker imageWorker;

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


        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
        Category categoryEnum = null;
        if (category != null) {
            categoryEnum = Category.getEnumByValue(category);
        }
        Example<Establishment> exampleQuery = Example.of(new Establishment(categoryEnum, hasMap, hasCardPayment), matcher);
        Page<Establishment> results = establishmentRepository.findAll(exampleQuery, page);
        logger.debug("Results was " + results);
        return establishmentMapper.modelListToDtoList(results)
                .stream()
                .filter(establishment -> establishment.getName().contains(name))
                .toList();
    }

    public void createEstablishment(EstablishmentDto dto) {
        logger.info("Creating new establishment");
        String address = dto.getAddress();
        String name = dto.getName();
        logger.debug("Establishment parameters:" + dto);
        if (establishmentRepository.existsByAddressAndName(address, name)) {
            logger.warn("Establishment " + name + " " + address + " already exists");
            throw new EstablishmentAlreadyExistsException(name, address);
        }

        Set<WorkingHoursDto> workingHoursDto = dto.getWorkingHours();
        Set<PhotoDto> photos = dto.getPhotosInput();
        Establishment establishment = establishmentMapper.dtoToModel(dto);
        Set<Tag> tags = dto
                .getTags()
                .stream()
                .map(x -> Tag.parseEnum(x.getName()))
                .collect(Collectors.toSet());
        establishment.setTags(tags);

        Establishment savedEstablishment = establishmentRepository.save(establishment);
        workingHoursService.saveWorkingHours(workingHoursDto, savedEstablishment);
        imageService.saveImages(photos, savedEstablishment);
        logger.info("Establishment was saved");
    }

    public List<String> getCategories() {
        logger.info("Getting categories");
        return Arrays.stream(Category.values()).map(x -> x.value).toList();
    }

    public List<TagDto> getTags() {
        return Arrays.stream(Tag.values())
                .map(x -> new TagDto(x.translate, imageWorker.getImageFromResource(x.assets)))
                .toList();
    }

    @Override
    public Set<PhotoDto> getPhotos(Long establishmentId) {
        logger.info("Getting photos");
        Establishment establishment = establishmentRepository
                .findById(establishmentId).orElseThrow(
                        () -> new EstablishmentNotFoundException(establishmentId)
                );

        return establishment.getPhotos()
                .stream()
                .map((photo) -> new PhotoDto(imageWorker.loadImage(photo.getFilepath())))
                .collect(Collectors.toSet());
    }

    @Override
    public List<ValidTimeDto> getValidTime(Long establishmentId) {
        Establishment establishment = establishmentRepository.findById(establishmentId)
                .orElseThrow(() -> new EstablishmentNotFoundException(establishmentId));

        List<ValidTimeDto> times = new ArrayList<>();
        Set<WorkingHours> workingHours = establishment.getWorkingHours();
        int dayCountInOneWeek = 7;

        for (int i = 0; i < dayCountInOneWeek; i++) {
            ValidTimeDto currentDto = new ValidTimeDto();
            LocalDate today = LocalDate.now()
                    .plusDays(i);
            String todayDayName = today.getDayOfWeek()
                    .getDisplayName(TextStyle.SHORT, new Locale("ru"));

            for (WorkingHours currentHours : workingHours) {
                if (currentHours.getDayOfWeek().getTranslateLittle().equals(todayDayName)) {

                    currentDto.setMonthName(today.getMonth()
                            .getDisplayName(TextStyle.SHORT, new Locale("ru")));
                    currentDto.setDayName(todayDayName);
                    currentDto.setDayNumber(today.getDayOfMonth() + " ");

                    int extraMinutes = 30 - currentHours.getStartTime().getMinute() % 30;
                    List<String> currentDayList = new ArrayList<>();
                    for (LocalTime currentTime = currentHours.getStartTime().plusMinutes(extraMinutes);
                         currentTime.isBefore(currentHours.getEndTime());
                         currentTime = currentTime.plusMinutes(30)) {
                        currentDayList.add(currentTime.toString());
                    }
                    currentDto.setTimes(currentDayList);
                    times.add(currentDto);
                }

            }
        }
        return times;
    }

    public void addMap(Long establishmentId, String map) {
        logger.info("Creating map of establishment  " + establishmentId);
        Establishment establishment = establishmentRepository.findById(establishmentId)
                .orElseThrow(() -> new EstablishmentNotFoundException(establishmentId));
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
}
