package ru.nsu.fit.pak.budle;

import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.dbunit.DBUnitSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import ru.nsu.fit.pak.budle.dao.WorkingHours;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.ValidTimeDto;
import ru.nsu.fit.pak.budle.dto.request.RequestWorkingHoursDto;
import ru.nsu.fit.pak.budle.repository.EstablishmentRepository;
import ru.nsu.fit.pak.budle.repository.WorkingHoursRepository;
import ru.nsu.fit.pak.budle.service.WorkingHoursService;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

@DisplayName("Тесты на бизнес-функциональность рабочих часов заведения")
@FlywayTest
public class WorkingHoursBusinessLogicTests extends AbstractContextualTest {

    private final static Long ESTABLISHMENT_ID = 100L;
    private static final List<String> TIMES = List.of("00:00", "00:30");
    @Autowired
    private WorkingHoursService workingHoursService;
    @Autowired
    private WorkingHoursRepository workingHoursRepository;
    @Autowired
    private EstablishmentRepository establishmentRepository;

    @Test
    @FlywayTest
    @DBUnitSupport(loadFilesForRun = {"CLEAN_INSERT", "/working_hours/before/prepare.xml"})
    @DisplayName("Тест на удаление рабочих часов из базы данных")
    public void testDeleteWorkingHours() {
        WorkingHours wh = workingHoursRepository.findAll().get(0);
        workingHoursService.deleteHours(Set.of(wh));
        Assertions.assertTrue(workingHoursRepository.findAll().isEmpty());
    }

    @Test
    @FlywayTest
    @DBUnitSupport(loadFilesForRun = {"CLEAN_INSERT", "/working_hours/before/prepare.xml"})
    @DisplayName("Тест генерации рабочих часов заведения на основании времени работы")
    public void testGenerateWorkingHoursDuration() {
        String instantExpected = "2014-12-22T10:15:30Z";
        Clock clock = Clock.fixed(Instant.parse(instantExpected), ZoneId.of("UTC"));
        LocalDate now = LocalDate.now(clock);
        try (MockedStatic<LocalDate> mockedStatic = mockStatic(LocalDate.class)) {
            mockedStatic.when(LocalDate::now).thenReturn(now);
            Establishment establishment = establishmentRepository.findById(ESTABLISHMENT_ID).orElseThrow();
            List<ValidTimeDto> validTimeDtoList = workingHoursService.generateValidBookingHours(establishment);
            Assertions.assertEquals(
                List.of(
                    ValidTimeDto.builder().monthName("дек.").dayName("вт").dayNumber("23").times(TIMES).build(),
                    ValidTimeDto.builder().monthName("дек.").dayName("вт").dayNumber("30").times(TIMES).build()
                ),
                validTimeDtoList
            );
        }

    }

    @Test
    @FlywayTest
    @DBUnitSupport(loadFilesForRun = {"CLEAN_INSERT", "/working_hours/before/prepare.xml"})
    @DisplayName("Тест генерации рабочих часов заведения на основании времени работы (с учетом отступа по времени)")
    public void testGenerateWorkingHoursDurationWithGap() {
        String instantExpected = "2014-12-23T00:01:00Z";
        Clock clock = Clock.fixed(Instant.parse(instantExpected), ZoneId.of("UTC"));
        LocalDate now = LocalDate.now(clock);
        ZonedDateTime zonedNow = ZonedDateTime.now(clock);
        try (MockedStatic<LocalDate> mockedStatic = mockStatic(LocalDate.class);
             MockedStatic<ZonedDateTime> mockedZonedTime = mockStatic(ZonedDateTime.class)) {
            mockedStatic.when(LocalDate::now).thenReturn(now);
            mockedZonedTime.when(() -> ZonedDateTime.now(any(ZoneId.class))).thenReturn(zonedNow);
            Establishment establishment = establishmentRepository.findById(ESTABLISHMENT_ID).orElseThrow();
            List<ValidTimeDto> validTimeDtoList = workingHoursService.generateValidBookingHours(establishment);
            Assertions.assertEquals(
                List.of(
                    ValidTimeDto.builder().monthName("дек.").dayName("вт").dayNumber("23").times(List.of(TIMES.get(1)))
                        .build(),
                    ValidTimeDto.builder().monthName("дек.").dayName("вт").dayNumber("30").times(TIMES).build()
                ),
                validTimeDtoList
            );
        }

    }

    @Test
    @FlywayTest
    @DBUnitSupport(loadFilesForRun = {"CLEAN_INSERT", "/working_hours/before/prepare.xml"})
    @DisplayName("Тест на сохранение рабочих часов в базу данных")
    @ExpectedDatabase(
        value = "/working_hours/after/not_replace_old_hours.xml",
        assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED
    )
    public void testSaveWorkingHours() {
        workingHoursService.saveWorkingHours(
            Set.of(
                RequestWorkingHoursDto.builder()
                    .days(List.of("Ср"))
                    .startTime(LocalTime.of(12, 0))
                    .endTime(LocalTime.of(13, 0))
                    .build()
            ),
            establishmentRepository.findById(ESTABLISHMENT_ID).orElseThrow()
        );

        Assertions.assertEquals(2, workingHoursRepository.findAll().size());
    }

}
