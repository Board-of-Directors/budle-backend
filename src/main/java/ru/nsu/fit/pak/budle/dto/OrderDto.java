package ru.nsu.fit.pak.budle.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class OrderDto {
    private Integer guestCount;
    private LocalDate date;
    private LocalTime time;
    private Long establishmentId;
    private Long userId;
}
