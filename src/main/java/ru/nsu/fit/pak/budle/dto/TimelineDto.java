package ru.nsu.fit.pak.budle.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
public class TimelineDto {
    private LocalTime start;
    private LocalTime end;
    private Set<BookingTimesDto> times;
}
