package ru.nsu.fit.pak.budle;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import ru.nsu.fit.pak.budle.dao.WorkingHours;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.ValidTimeDto;
import ru.nsu.fit.pak.budle.repository.EstablishmentRepository;
import ru.nsu.fit.pak.budle.repository.WorkingHoursRepository;
import ru.nsu.fit.pak.budle.service.WorkingHoursService;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;

@DisplayName("Тесты на бизнес-функциональность рабочих часов заведения")
@DatabaseSetup("/working_hours/before/prepare.xml")
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
    @DisplayName("Тест на удаление рабочих часов из базы данных")
    public void testDeleteWorkingHours() {
        WorkingHours wh = workingHoursRepository.findAll().get(0);
        workingHoursService.deleteHours(Set.of(wh));
        Assertions.assertTrue(workingHoursRepository.findAll().isEmpty());
    }

    @Test
    @DisplayName("Тест генерации рабочих часов заведения на основании времени работы")
    public void testGenerateWorkingHoursDuration() {
        String instantExpected = "2014-12-22T10:15:30Z";
        Clock clock = Clock.fixed(Instant.parse(instantExpected), ZoneId.of("UTC"));
        LocalDate now = LocalDate.now(clock);
        try (MockedStatic<LocalDate> mockedStatic = mockStatic(LocalDate.class)) {
            mockedStatic.when(LocalDate::now).thenReturn(now);
            Establishment establishment = establishmentRepository.findById(ESTABLISHMENT_ID).orElseThrow();
            List<ValidTimeDto> validTimeDtoList = workingHoursService.getValidBookingHoursByEstablishment(establishment);
            Assertions.assertEquals(
                List.of(
                    ValidTimeDto.builder().monthName("дек.").dayName("вт").dayNumber("23").times(TIMES).build(),
                    ValidTimeDto.builder().monthName("дек.").dayName("вт").dayNumber("30").times(TIMES).build()
                ),
                validTimeDtoList
            );
        }

    }

}
