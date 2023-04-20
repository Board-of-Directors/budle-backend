package ru.nsu.fit.pak.budle.mapper;

import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.budle.dto.ValidTimeDto;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

@Component
public class WorkingHoursMapper {
    public ValidTimeDto convertFromDateAndTimeToValidTimeDto(LocalDate date) {
        TextStyle style = TextStyle.SHORT;
        Locale locale = new Locale("ru");
        ValidTimeDto validTimeDto = new ValidTimeDto();
        validTimeDto.setMonthName(date.getMonth().getDisplayName(style, locale));
        validTimeDto.setDayName(date.getDayOfWeek().getDisplayName(style, locale));
        validTimeDto.setDayNumber(date.getDayOfMonth() + "");
        return validTimeDto;
    }
}
