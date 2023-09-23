package ru.nsu.fit.pak.budle;

import com.github.springtestdbunit.annotation.ExpectedDatabase;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.dbunit.DBUnitSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import ru.nsu.fit.pak.budle.dto.SpotDto;
import ru.nsu.fit.pak.budle.dto.TimelineDto;
import ru.nsu.fit.pak.budle.service.SpotService;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

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

    @Test
    @DisplayName("Проверка таймлайна места: пустой таймлайн")
    @DBUnitSupport(loadFilesForRun = {"CLEAN_INSERT", "/establishment/before/establishment_with_spots.xml"})
    @FlywayTest
    public void testGetEmptyTimeline() {
        String instantExpected = "2023-09-26T00:01:00Z";
        Clock clock = Clock.fixed(Instant.parse(instantExpected), ZoneId.of("UTC"));
        LocalDate now = LocalDate.now(clock);
        ZonedDateTime zonedNow = ZonedDateTime.now(clock);
        try (MockedStatic<LocalDate> mockedStatic = mockStatic(LocalDate.class);
             MockedStatic<ZonedDateTime> mockedZonedTime = mockStatic(ZonedDateTime.class)) {
            mockedStatic.when(LocalDate::now).thenReturn(now);
            mockedZonedTime.when(() -> ZonedDateTime.now(any(ZoneId.class))).thenReturn(zonedNow);
            Assertions.assertEquals(
                new TimelineDto()
                    .setStart(LocalTime.of(0, 0))
                    .setEnd(LocalTime.of(23, 0))
                    .setTimes(Collections.emptySet()),
                spotService.getSpotTimeline(1L, ESTABLISHMENT_ID)
            );
        }
    }
}
