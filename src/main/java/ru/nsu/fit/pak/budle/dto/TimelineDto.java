package ru.nsu.fit.pak.budle.dto;

import lombok.Data;

import java.time.LocalTime;
import java.util.Set;

@Data
public class TimelineDto {
    private LocalTime start;
    private LocalTime end;
    private Set<BookingTimesDto> times;
}
