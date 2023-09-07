package ru.nsu.fit.pak.budle;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.nsu.fit.pak.budle.controller.EstablishmentController;
import ru.nsu.fit.pak.budle.dao.Category;
import ru.nsu.fit.pak.budle.dao.Tag;
import ru.nsu.fit.pak.budle.dao.User;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.request.RequestGetEstablishmentParameters;
import ru.nsu.fit.pak.budle.repository.EstablishmentRepository;
import ru.nsu.fit.pak.budle.repository.UserRepository;
import ru.nsu.fit.pak.budle.service.EstablishmentService;

@DatabaseSetup(value = "/establishment/before/establishment.xml")
@DisplayName("Тест на бизнес-логику заведений")
class EstablishmentBusinessLogicTests extends AbstractContextualTest {

    private static final Long USER_ID = 200L;

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
    @DisplayName("Поиск заведения по неверной категории")
    public void testCreatingEstablishment_FindEstablishmentByParams_FindByWrongCategory() {
        Assertions.assertFalse(establishmentController.getEstablishments(
            RequestGetEstablishmentParameters.builder().build()
        ).getEstablishments().isEmpty());
    }

    @Test
    @DisplayName("Поиска заведения по правильной категории")
    public void testCreatingEstablishment_FindEstablishmentByParams_FindByRightCategory() {
        Assertions.assertEquals(establishmentService.getEstablishmentByParams(
            RequestGetEstablishmentParameters.builder().build()
        ).getCount(), 1);
    }

    @Test
    @DisplayName("Поиск заведения по неверному флагу")
    public void testCreatingEstablishment_FindEstablishmentByParams_FindByWrongBooleanFlags() {
        Assertions.assertEquals(establishmentService.getEstablishmentByParams(
            RequestGetEstablishmentParameters.builder().hasMap(false).build()
        ).getCount(), 1);
    }

    @Test
    @DisplayName("Поиск заведения по правильным параметрам")
    public void testCreatingEstablishment_FindEstablishmentByRightParams() {
        Assertions.assertEquals(establishmentService.getEstablishmentByParams(
            RequestGetEstablishmentParameters.builder().build()
        ).getCount(), 1);
    }

    @Test
    @DisplayName("Поиск заведения по неверному названию")
    public void testCreatingEstablishment_FindEstablishmentByWrongName() {
        Assertions.assertEquals(establishmentService.getEstablishmentByParams(
            RequestGetEstablishmentParameters.builder().name("Red Rabbit").build()
        ).getCount(), 1);
    }
}
