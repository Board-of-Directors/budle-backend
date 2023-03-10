package ru.nsu.fit.pak.budle.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class WorkingHoursDto {
    private Integer dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
}
