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
import org.xml.sax.SAXException;
import ru.nsu.fit.pak.budle.dao.Category;
import ru.nsu.fit.pak.budle.dao.Tag;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.EstablishmentDto;
import ru.nsu.fit.pak.budle.dto.PhotoDto;
import ru.nsu.fit.pak.budle.dto.TagDto;
import ru.nsu.fit.pak.budle.dto.WorkingHoursDto;
import ru.nsu.fit.pak.budle.exceptions.EstablishmentAlreadyExistsException;
import ru.nsu.fit.pak.budle.exceptions.EstablishmentNotFoundException;
import ru.nsu.fit.pak.budle.mapper.EstablishmentMapper;
import ru.nsu.fit.pak.budle.repository.EstablishmentRepository;
import ru.nsu.fit.pak.budle.utils.ImageWorker;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstablishmentServiceImpl implements EstablishmentService {
    private static final Logger log = LoggerFactory.getLogger(EstablishmentServiceImpl.class);
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
        log.info("getEstablishmentByParams\n" + "Category: " + category + "\n" +
                "HasMap: " + hasMap + "\n" +
                "HasCardPayment " + hasCardPayment + "\n" + "Name: " + name);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
        Category categoryEnum = null;
        if (category != null) {
            categoryEnum = Category.getEnumByValue(category);
        }
        Example<Establishment> exampleQuery = Example.of(new Establishment(categoryEnum, hasMap, hasCardPayment), matcher);
        Page<Establishment> results = establishmentRepository.findAll(exampleQuery, page);
        return establishmentMapper.modelListToDtoList(results)
                .stream()
                .filter(establishment -> establishment.getName().contains(name))
                .toList();
    }

    public void createEstablishment(EstablishmentDto dto) {
        String address = dto.getAddress();
        String name = dto.getName();
        if (establishmentRepository.existsByAddressAndName(address, name)) {
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
    }

    public List<String> getCategories() {
        return Arrays.stream(Category.values()).map(x -> x.value).toList();
    }

    public List<TagDto> getTags() {
        return Arrays.stream(Tag.values())
                .map(x -> new TagDto(x.translate, imageWorker.getImageFromResource(x.assets)))
                .toList();
    }

    @Override
    public Set<PhotoDto> getPhotos(Long establishmentId) {
        Establishment establishment = establishmentRepository
                .findById(establishmentId).orElseThrow(
                        () -> new EstablishmentNotFoundException(establishmentId)
                );

        return establishment.getPhotos()
                .stream()
                .map((photo) -> new PhotoDto(imageWorker.loadImage(photo.getFilepath())))
                .collect(Collectors.toSet());
    }

    public void addMap(Long establishmentId, String map) throws IOException, SAXException, ParserConfigurationException, TransformerException {
        Establishment establishment = establishmentRepository.findById(establishmentId)
                .orElseThrow(() -> new EstablishmentNotFoundException(establishmentId));

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(map)));
        NodeList elems = document.getDocumentElement().getElementsByTagName("path");

        //FIXME: SAVE ALL SPOTS IN ONE QUERY
        for (int i = 0; i < elems.getLength(); i++) {
            spotService.createSpot((long) i, establishmentId);
            Element elem = (Element) elems.item(i);
            elem.setAttribute("id", i + "");
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        DOMSource source = new DOMSource(document);

        String mapPath = "./maps" + establishmentId + ".svg";
        establishment.setMap(mapPath);
        establishmentRepository.save(establishment);

        StreamResult result = new StreamResult(new File(mapPath));
        transformer.transform(source, result);
    }
}
