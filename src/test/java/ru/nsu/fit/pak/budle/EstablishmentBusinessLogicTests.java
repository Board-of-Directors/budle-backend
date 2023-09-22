package ru.nsu.fit.pak.budle;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import ru.nsu.fit.pak.budle.controller.EstablishmentController;
import ru.nsu.fit.pak.budle.dao.Category;
import ru.nsu.fit.pak.budle.dao.Tag;
import ru.nsu.fit.pak.budle.dao.User;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.EstablishmentListDto;
import ru.nsu.fit.pak.budle.dto.request.RequestEstablishmentDto;
import ru.nsu.fit.pak.budle.dto.request.RequestGetEstablishmentParameters;
import ru.nsu.fit.pak.budle.dto.request.RequestHotelDto;
import ru.nsu.fit.pak.budle.dto.request.RequestWorkingHoursDto;
import ru.nsu.fit.pak.budle.exceptions.ErrorWhileParsingEstablishmentMapException;
import ru.nsu.fit.pak.budle.repository.EstablishmentRepository;
import ru.nsu.fit.pak.budle.repository.UserRepository;
import ru.nsu.fit.pak.budle.repository.WorkingHoursRepository;
import ru.nsu.fit.pak.budle.service.EstablishmentService;
import ru.nsu.fit.pak.budle.utils.ImageWorker;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DatabaseSetup(value = "/establishment/before/establishment.xml")
@DisplayName("Тест на бизнес-логику заведений")
class EstablishmentBusinessLogicTests extends AbstractContextualTest {

    private static final Long USER_ID = 200L;

    private static final Integer ONLY_ONE = 1;
    private static final Integer NOTHING = 0;

    @Autowired
    private ImageWorker imageWorker;
    @Autowired
    private WorkingHoursRepository workingHoursRepository;

    @BeforeEach
    public void makeMock() {
        User user = userRepository.findById(USER_ID).orElseThrow();
        mockUser(user);
    }

    @Autowired
    private EstablishmentService establishmentService;
    @Autowired
    private EstablishmentRepository establishmentRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EstablishmentController establishmentController;

    @Test
    @DisplayName("Проверка количества категорий")
    public void testCategoryNumber() {
        Assertions.assertEquals(establishmentService.getCategories().size(), Category.values().length);
    }

    @Test
    @DisplayName("Проверка количества тэгов")
    public void testTagsNumber() {
        Assertions.assertEquals(establishmentController.tags().size(), Tag.values().length);
    }

    @Test
    @DisplayName("Проверка создания карты заведения")
    public void testCreatingMap() {
        Establishment establishment = establishmentRepository.findAll().get(0);
        String addedMap =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><svg xmlns=\"http://www.w3.org/2000/svg\" fill=\"none\" height=\"555\" viewBox=\"0 0 375 555\" width=\"375\"/>";
        establishmentService.addMap(establishment.getId(), addedMap);
    }

    @Test
    @DisplayName("Проверка удаления заведения")
    @ExpectedDatabase(
        value = "/establishment/after/deleted_establishment.xml",
        assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED
    )
    public void deleteEstablishment() {
        establishmentService.deleteEstablishment(100L);
        Assertions.assertEquals(
            0,
            establishmentRepository.findAll().size()
        );
    }

    @Test
    @DisplayName("Проверка ошибки создания карты")
    public void errorCreatingMap() {
        Establishment establishment = establishmentRepository.findAll().get(0);
        String addedMap = "smkalz";
        Assertions.assertThrows(
            ErrorWhileParsingEstablishmentMapException.class,
            () -> establishmentService.addMap(establishment.getId(), addedMap)
        );
    }

    @Test
    @DisplayName("Проверка создания заведения")
    @ExpectedDatabase(
        value = "/establishment/after/hotel_created.xml",
        assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED
    )
    public void testCreatingHotel() {
        when(imageWorker.saveImage(any())).thenReturn("image path");
        establishmentService.createEstablishment(getEstablishment());
        Assertions.assertEquals(4, workingHoursRepository.findAll().size());
    }

    @MethodSource
    @DisplayName("Тест на поиск заведения по заданным параметрам")
    @ParameterizedTest(name = TUPLE_PARAMETERIZED_DISPLAY_NAME)
    @SuppressWarnings("unused")
    public void search(
        String testName,
        RequestGetEstablishmentParameters parameters,
        Integer expectedResponseCount
    ) {
        EstablishmentListDto establishmentListDto = establishmentService.getEstablishmentByParams(parameters);
        Assertions.assertEquals(expectedResponseCount, establishmentListDto.getCount());

    }

    @EnumSource(Category.class)
    @ParameterizedTest(name = TUPLE_PARAMETERIZED_DISPLAY_NAME)
    @DisplayName("Тест на получение вариантов категорий")
    public void categoryVariants(Category category) {
        Assertions.assertEquals(
            category.variants,
            establishmentService.getCategoryVariants(category.getValue())
        );

    }

    @NonNull
    public static Stream<Arguments> search() {
        return Stream.of(
            Arguments.of(
                "По пустому фильтру",
                RequestGetEstablishmentParameters.builder().build(),
                ONLY_ONE
            ),
            Arguments.of(
                "По названию",
                RequestGetEstablishmentParameters.builder().name("Red Rabbit").build(),
                ONLY_ONE
            ),
            Arguments.of(
                "По флагу наличия карты",
                RequestGetEstablishmentParameters.builder().hasMap(false).build(),
                ONLY_ONE
            ),
            Arguments.of(
                "По флагу наличия безналичной оплаты",
                RequestGetEstablishmentParameters.builder().hasCardPayment(false).build(),
                ONLY_ONE
            ),
            Arguments.of(
                "По количеству рабочих дней",
                RequestGetEstablishmentParameters.builder().workingDayCount(1).build(),
                ONLY_ONE
            ),
            Arguments.of(
                "По верной категории",
                RequestGetEstablishmentParameters.builder().category(Category.game_club.value).build(),
                ONLY_ONE
            ),
            Arguments.of(
                "По неверной категории",
                RequestGetEstablishmentParameters.builder().category(Category.hotel.value).build(),
                NOTHING
            )
        );
    }

    private RequestEstablishmentDto getEstablishment() {
        return RequestHotelDto.builder()
            .name("DoubleTree")
            .address("Aztekov st. 47")
            .description("One of the best places")
            .hasCardPayment(false)
            .hasMap(false)
            .owner(USER_ID)
            .price(400)
            .rating(4.8F)
            .tags(Collections.emptySet())
            .workingHours(Set.of(
                RequestWorkingHoursDto.builder()
                    .startTime(LocalTime.of(12, 0))
                    .endTime(LocalTime.of(13, 0))
                    .days(List.of("Вт", "Ср", "Вс"))
                    .build()
            ))
            .category(Category.hotel.value)
            .photosInput(Collections.emptySet())
            .build();
    }

}
