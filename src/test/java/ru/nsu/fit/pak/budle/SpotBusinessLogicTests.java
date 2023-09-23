package ru.nsu.fit.pak.budle;

import com.github.springtestdbunit.annotation.ExpectedDatabase;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.dbunit.DBUnitSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.nsu.fit.pak.budle.dto.SpotDto;
import ru.nsu.fit.pak.budle.service.SpotService;

import java.util.List;

@DisplayName("Тесты на бизнес-функциональность работы с местами для бронирования")
@FlywayTest
@DBUnitSupport(loadFilesForRun = {"CLEAN_INSERT", "/establishment/before/establishment_with_spots.xml"})
public class SpotBusinessLogicTests extends AbstractContextualTest {
    private static final Long ESTABLISHMENT_ID = 100L;
    @Autowired
    private SpotService spotService;

    @Test
    @DisplayName("Проверка получения мест заведения")
    @DBUnitSupport(loadFilesForRun = {"CLEAN_INSERT", "/establishment/before/establishment_with_spots.xml"})
    @FlywayTest
    public void testGetSpots() {
        SpotDto expectedSpot = new SpotDto();
        expectedSpot.setId(2L);
        Assertions.assertEquals(
            spotService.getSpotsByEstablishment(ESTABLISHMENT_ID),
            List.of(expectedSpot)
        );

    }

    @Test
    @DisplayName("Проверка получения мест заведения")
    @DBUnitSupport(loadFilesForRun = {"CLEAN_INSERT", "/establishment/before/establishment_with_spots.xml"})
    @FlywayTest
    public void testGetSpecificSpot() {
        SpotDto expectedSpot = new SpotDto();
        expectedSpot.setId(2L);
        Assertions.assertEquals(
            spotService.getSpotById(2L),
            expectedSpot
        );

    }

    @Test
    @DisplayName("Проверка получения мест заведения")
    @DBUnitSupport(loadFilesForRun = {"CLEAN_INSERT", "/establishment/before/establishment_with_spots.xml"})
    @FlywayTest
    @ExpectedDatabase(value = "/spots/after/establishment_with_two_spots.xml")
    public void testCreateSpot() {
        spotService.createSpot(2L, ESTABLISHMENT_ID);
    }
}
